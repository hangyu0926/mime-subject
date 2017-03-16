package cn.nanmi.msts.web.dao;


import cn.nanmi.msts.web.model.BiddingListDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("OrderMapper")
public interface OrderMapper {
    List<BiddingListDTO> getBiddingList(@Param("startPage") int startPage,@Param("pageSize") int pageSize);
}
