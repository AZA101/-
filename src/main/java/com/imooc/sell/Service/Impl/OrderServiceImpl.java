package com.imooc.sell.Service.Impl;

import com.imooc.sell.Service.OrderService;
import com.imooc.sell.Service.ProductService;
import com.imooc.sell.Utils.KeyUtil;
import com.imooc.sell.convert.OrderMasterToOrderDTOConverter;
import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.dataobject.OrderMaster;
import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.dto.CartDTO;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.OrderMasterEnum;
import com.imooc.sell.enums.PayStatusEnums;
import com.imooc.sell.enums.ProductStatusEnum;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.OrderDetailRepository;
import com.imooc.sell.repository.OrderMasterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * orderservice的实现层
 *
 * @author YJB
 * 2019-07-3
 */
@Service
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderDetailRepository repository;
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        //调用创建订单的方法时候就创建订单号
        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(0);
        // List<CartDTO> cartDTOList =new ArrayList<>();
        //1 查询商品（数量、价格）
        //目前只查询一条数据,前端返回来的订单详细中只有商品id和数量
        for (OrderDetail orderDetail : orderDTO.getOrderDetails()) {
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //2 计算总价
            // 类似于 orderAmout+=orderAmout
            orderAmount = productInfo.getProductPrice().multiply
                    (new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            //订单详情入库
            orderDetail.setDetailId(KeyUtil.genUniqueKey()); //调用了静态方法
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo, orderDetail);//第一个参数内容拷贝到后面的参数中
            repository.save(orderDetail);

            /*订单详情中的订单id和订单的库存*/
            /*CartDTO cartDTO=new CartDTO(orderDetail.getProductId(),orderDetail.getProductQuantity());
            cartDTOList.add(cartDTO);*/
        }

        //3 写入订单数据库（ordermaster、orderdetail）
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmout(orderAmount);
        orderMaster.setOrderStatus(OrderMasterEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnums.WAIT.getCode());
        orderMasterRepository.save(orderMaster);


        //4 扣除库存
        List<CartDTO> cartDTOList = new ArrayList<>();
        cartDTOList = orderDTO.getOrderDetails().stream().map(e ->
                new CartDTO(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        //查询订单主表
        OrderMaster orderMaster = orderMasterRepository.findById(orderId).get();
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        //调用订单详细的dao层进行订单编号的查询
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (orderDetailList.size() < 0) {
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        //将查询到的数据存入到java代码变量中
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetails(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findlist(String buyerOpenid, Pageable pageable) {
        /*OrderMaster是对应订单主表的信息，OrderDTO包含了订单主表和订单详情表字段*/
        //System.out.println("查看自定义的列表查询方法内容："+orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable));
        Page<OrderMaster> orderMasterList = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        System.out.println("查看方法的输出：" + orderMasterList.getContent());
        List<OrderDTO> orderDTOList = OrderMasterToOrderDTOConverter.convert(orderMasterList.getContent());
        Page<OrderDTO> orderDTOPage = new PageImpl<>(orderDTOList, pageable, orderMasterList.getTotalElements());

        return orderDTOPage;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();

        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderMasterEnum.NEW.getCode())) {
            log.error("[取消订单]订单状态不正确,orderId={},orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderMasterEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //返回库存
        /*判断订单是否有商品*/
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetails())) {
            log.error("[取消订单]订单无商品详情，orderId={}", orderDTO.getOrderId());
            throw new SellException(ResultEnum.OEDER_DETAIL_EMPTY);
        }
        /*调用加库存的方法*/
        List<CartDTO> cartDTOList = orderDTO.getOrderDetails().stream().map(e -> new CartDTO
                (e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
        //调用服务层的增加库存方法
        productService.increaseStock(cartDTOList);

        //已支付订单，退回金额
        if (orderDTO.getOrderStatus().equals(PayStatusEnums.SUCCESS)) {
            //TODO
        }
        return orderDTO;
    }

    @Override
    @Transactional
    /**
     * 按正常流程走的情况下，将订单从非完结状态改为完结状态
     */
    public OrderDTO finish(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        //判断订单状态,只有新下单的订单才需要修改为完结订单
        if (!orderDTO.getOrderStatus().equals(OrderMasterEnum.NEW.getCode())) {
            log.error("[完结订单] 订单状态不是新下单 orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        orderDTO.setOrderStatus(OrderMasterEnum.FINISHED.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("[完结订单] 订单状态未修改为完结状态 orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //修改订单状态
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        /**
         * 只有新下单的订单才需要进行支付操作，其他订单不需要进行订单支付
         */
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderMasterEnum.NEW.getCode())) {
            log.error("[未支付的新下订单]不是新下单的订单 orderMaster={}", orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnums.WAIT.getCode())) {
            log.error("[判断订单支付状态]该订单不是未支付的订单 orderMaster={}", orderDTO.getPayStatus());
            throw new SellException(ResultEnum.OEDER_PAY_STATUS_ERROR);
        }
        //修改支付状态
        OrderMaster ordermaster = new OrderMaster();
        orderDTO.setPayStatus(PayStatusEnums.SUCCESS.getCode());
        BeanUtils.copyProperties(orderDTO, ordermaster);
        OrderMaster result = orderMasterRepository.save(ordermaster);
        if (result == null) {
            log.error("[支付订单状态修改]支付订单状态修改失败 orderMaster={}", orderDTO.getPayStatus());
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }
}
