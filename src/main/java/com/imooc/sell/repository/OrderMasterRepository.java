package com.imooc.sell.repository;

        import com.imooc.sell.dataobject.OrderMaster;
        import org.springframework.data.domain.Page;
        import org.springframework.data.domain.Pageable;
        import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 订单表的dao层
 * @author YJB
 * 2019-07-02
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {
    Page<OrderMaster> findByBuyerOpenid (String buyerOpenid,Pageable pageable);
}
