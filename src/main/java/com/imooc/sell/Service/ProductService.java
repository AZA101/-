package com.imooc.sell.Service;

import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author YJB
 * 2019-06-30
 * 商品的service层
 */

public interface ProductService {
    /**
     * 根据id来查询
     * @param productId
     * @return
     */
    ProductInfo  findOne(String productId);

    /**
     * 查询所有在架商品
     * @return
     */
    List<ProductInfo> findUpAll();

    /**
     * 分页查询
     * @param pageable
     * @return
     */
    Page<ProductInfo> findAll(Pageable pageable);

    /**
     * 保存商品
     * @param productInfo
     * @return
     */
    ProductInfo save(ProductInfo productInfo);
    /**
     * 加库存
     */
    void increaseStock(List<CartDTO> cartDTOList);

    /**
     * 减少库存
     */
    void decreaseStock(List<CartDTO> cartDTOList);
}
