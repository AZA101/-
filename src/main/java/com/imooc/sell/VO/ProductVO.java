package com.imooc.sell.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 商品包含类目
 * @author YJB
 * 2019-07-01
 */
@Data
public class ProductVO {
    @JsonProperty("name")  /*进行序列化操作，返回到前端时候使用name*/
    private  String categoryName;
    @JsonProperty("type")  /*商品类目也进行序列化*/
    private  Integer categoryType;
    @JsonProperty("foods")  /*商品的具体信息*/
    private List<ProductInfoVO> productInfoVOList;
}
