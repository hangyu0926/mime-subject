package cn.nanmi.msts.web.dao;


import cn.nanmi.msts.web.dao.entities.OperationEntity;
import cn.nanmi.msts.web.model.MyBiddingDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("operationMapper")
public interface OperationMapper {
    void addOperation(OperationEntity operationEntity);

    List<MyBiddingDTO> getMyBiddingRecord(@Param("startPage") int startPage,@Param("pageSize") int pageSize,@Param("userId") Long userId);

    Long getMyBiddingRecordCount(@Param("userId") Long userId);
}
