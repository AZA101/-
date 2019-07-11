package com.imooc.sell.Service;

import com.imooc.sell.dataobject.ProductCategory;

import java.util.List;

/**
 * 类目表的Service
 * @author YJB
 * 2019-06-29
 */
public interface CategoryService {
    /**
     * 通过主键进行查询
     * @param categoryId
     * @return
     */
    ProductCategory findOne(Integer categoryId);

    /**
     * 查询所有记录
     * @return
     */
    List<ProductCategory> findAll();

    /**
     * 根据产品类目编号进行查询
     * @param categoryTypeList
     * @return
     */
    List<ProductCategory> findByCategoryTypeIn (List<Integer> categoryTypeList);

    /**
     * 保存方法
     * @param productCategory
     * @return
     */
    ProductCategory save(ProductCategory productCategory);

}
