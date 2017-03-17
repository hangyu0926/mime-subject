package cn.nanmi.msts.web.dao;


import cn.nanmi.msts.web.model.BiddingDTO;
import cn.nanmi.msts.web.model.BiddingDetailDTO;
import cn.nanmi.msts.web.model.OrderDTO;
import cn.nanmi.msts.web.model.PreBiddingDTO;
import cn.nanmi.msts.web.response.CSResponse;
import cn.nanmi.msts.web.web.vo.in.BiddingListQueryVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("OrderMapper")
public interface OrderMapper {
    List<BiddingDTO> getBiddingList(@Param("startPage") int startPage,@Param("pageSize") int pageSize,@Param("userId") Long userId);

    PreBiddingDTO getPreBidding(@Param("orderNo") String orderNo);

    BiddingDetailDTO getOrderDetail(@Param("orderNo") String orderNo);

    void  updateStatus();

    void releaseOrder(OrderDTO orderDTO);

    List<OrderDTO> getMyOrder(@Param("startPage")int startPage, @Param("pageSize")int pageSize, @Param("userId") Long userId);

    void backoutOrder(@Param("orderNo")String orderNo);
}
