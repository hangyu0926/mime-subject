package cn.nanmi.msts.web.dao;


import cn.nanmi.msts.web.model.BiddingDTO;
import cn.nanmi.msts.web.model.OrderDTO;
import cn.nanmi.msts.web.model.PreBiddingDTO;
import cn.nanmi.msts.web.response.CSResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("OrderMapper")
public interface OrderMapper {
    List<BiddingDTO> getBiddingList(@Param("startPage") int startPage,@Param("pageSize") int pageSize,@Param("userId") Long userId);

    CSResponse releaseOrder(OrderDTO orderDTO);

    PreBiddingDTO getPreBidding(@Param("orderNo") String orderNo);
}
