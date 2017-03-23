package cn.nanmi.msts.web.dao;


import cn.nanmi.msts.web.model.*;
import cn.nanmi.msts.web.web.vo.in.UpdateConfigVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("orderMapper")
public interface OrderMapper {
    List<BiddingDTO> getBiddingList(@Param("startPage") int startPage,@Param("pageSize") int pageSize,@Param("userId") Long userId);

    Long getBiddingListCount(@Param("userId") Long userId);

    PreBiddingDTO getPreBidding(@Param("orderNo") String orderNo);

    BiddingDetailDTO getOrderDetail(@Param("orderNo") String orderNo);

    void  updateStatus();

    void releaseOrder(OrderDTO orderDTO);

    Long queryTodayOrder(@Param("curDate")String curDate);

    List<OrderDTO> getMyOrder(@Param("startPage")int startPage, @Param("pageSize")int pageSize, @Param("userId") Long userId);

    Long getMyOrderCount(@Param("userId") Long userId);

    void updateOrderState(@Param("orderNo")String orderNo,@Param("orderState")int orderState);

    void updateOrderSaleTime(@Param("orderNo")String orderNo,@Param("biddingPeriod") int biddingPeriod);

    SystemRules getSystemRules();

    List<OrderDTO> beConfirmedList(@Param("startPage")int startPage, @Param("pageSize")int pageSize);

    List<OrderDTO> releaseAuditList(@Param("startPage")int startPage, @Param("pageSize")int pageSize);

    List<OrderDTO> backoutAuditList(@Param("startPage")int startPage, @Param("pageSize")int pageSize);

    Long beConfirmedListCount();

    Long releaseAuditListCount();

    Long backoutAuditListCount();

    void updateOrderBidding(@Param("biddingPrice")Double biddingPrice,@Param("bidderId")Long bidderId,@Param("orderNo")String orderNo);

    JumpReleaseOrderDTO jumpReleaseOrder(@Param("userId") Long userId);

    void insertNewConfig(UpdateConfigVO updateConfigVO);

    void releaseAudit(OrderCheckDTO orderCheckDTO);

    void confirmOrder(@Param("orderState")Integer orderState,@Param("sellerConfirm")Integer sellerConfirm,@Param("buyerConfirm")Integer buyerConfirm,@Param("orderNo")String orderNo);

    List<OrderDTO> getPassOrder();

    void updateStatus2Pass();

    SystemRules getSystemRulesById(@Param("ruleId")Long ruleId);
}
