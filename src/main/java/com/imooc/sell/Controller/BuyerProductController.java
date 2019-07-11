package com.imooc.sell.Controller;

import com.imooc.sell.Service.CategoryService;
import com.imooc.sell.Service.ProductService;
import com.imooc.sell.Utils.ResultVOUtil;
import com.imooc.sell.VO.ProductInfoVO;
import com.imooc.sell.VO.ProductVO;
import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.dataobject.ProductCategory;
import com.imooc.sell.dataobject.ProductInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 用户商品
 *
 * @author YJB
 * 2019-07-01
 */
@RestController  /*返回的是json格式，所以返回RestController*/
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ResultVO list() {
        //1 查询所有的上架商品
        List<ProductInfo> productInfoList = productService.findUpAll();

        //2 查询类目（一次性查询）
        /*List<Integer> categoryTypeList=new ArrayList<>();*/
        //使用循环将所有商品的类目编号传入到list里面
       /* for (ProductInfo productInfo:productInfoList) {
            categoryTypeList.add(productInfo.getCategoryType());
        }*/
        //java8 提供的新特性(lambda表达式)
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(e -> e.getCategoryType())
                .collect(Collectors.toList());

        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);
        //3 数据拼装
        List<ProductVO> productVOList=new ArrayList<>();
        for (ProductCategory productCategory : productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());


            List<ProductInfoVO> productInfoVOList=new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                ProductInfoVO productInfoVO = new ProductInfoVO();
                if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    BeanUtils.copyProperties(productInfo,productInfoVO);//spring提供的复制数据的功能
                    productInfoVOList.add(productInfoVO);
                }

            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }


        ResultVO resultVO = new ResultVO();
        /*resultVO.setCode(0);
        resultVO.setMsg("操作成功");
        resultVO.setData(productVOList);*/
     //   return resultVO;
        return ResultVOUtil.success(productVOList);
    }
}
