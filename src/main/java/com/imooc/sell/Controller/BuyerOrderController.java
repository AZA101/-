package com.imooc.sell.Controller;

import com.imooc.sell.Service.Impl.BuyerServiceImpl;
import com.imooc.sell.Service.Impl.OrderServiceImpl;
import com.imooc.sell.Utils.ResultVOUtil;
import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.convert.OrderFormToOrderDTOConverter;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.from.OrderForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 买家端
 *
 * @author YJB
 * 2019-07-02
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private BuyerServiceImpl buyerService;

    OrderDTO orderDTO = new OrderDTO();

    //创建订单
    /*因为返回去的内容中有code、msg、data,data里面有具体的内容,可以当成返回去一个map*/
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("[创建订单]参数不正确,orderForm={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        orderDTO = OrderFormToOrderDTOConverter.convert(orderForm);//调用静态方法
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetails())) {
            log.error("[参数错误]购物车参数内容为空 orderDTO={}", orderDTO.getOrderDetails());
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO creatResult = orderService.create(orderDTO);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", creatResult.getOrderId());
        return ResultVOUtil.success(map);
    }

    //订单列表
    @PostMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size) {
        if (StringUtils.isEmpty(openid)) {
            log.error("[参数错误]openid为空 openid={}", openid);
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest pageRequest = new PageRequest(page, size);
        Page<OrderDTO> orderDTOPage = orderService.findlist(openid, pageRequest);

        return ResultVOUtil.success(orderDTOPage.getContent());
        // return ResultVOUtil.success();
    }

    //订单详情
    @PostMapping("/detail")
    public ResultVO<List<OrderDTO>> detail(@RequestParam("openid") String openid,
                                           @RequestParam("orderId") String orderId) {

        //TODO 这里需要对用户身份先进行判断，否则未判断直接从查出数据影响安全性
        if (StringUtils.isEmpty(orderId)) {
            log.error("[参数问题] 参数为空 orderId={}", orderId);
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
       // OrderDTO orderDTO = orderService.findOne(orderId);
        OrderDTO orderDTO = buyerService.findOne(openid,orderId);
        return ResultVOUtil.success(orderDTO);
    }

    //取消订单
    @PostMapping("/cancel")
    public ResultVO<List<OrderDTO>> cancel(@RequestParam("openid") String openid,
                                           @RequestParam("orderId") String orderId) {
        //TODO 这里需要对用户身份先进行判断，否则未判断直接从查出数据影响安全性
       // OrderDTO orderDTO = orderService.findOne(orderId);
        buyerService.cancel(openid, orderId);
       // orderService.cancel(orderDTO);  取消订单的具体操作放入到service层

        return ResultVOUtil.success();
    }
}
