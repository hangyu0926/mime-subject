package cn.nanmi.msts.web.service;

import cn.nanmi.msts.web.dao.entities.OperationEntity;
import cn.nanmi.msts.web.model.*;
import cn.nanmi.msts.web.response.CSResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with cn.nanmi.msts.web.service.
 * User: jiangbin
 * Date: 2017/3/16
 * Time: 9:33
 */
public interface IOperationService {

    void addOperation(OperationEntity operationEntity);

    List<MyBiddingDTO> getMyBiddingRecord(int startPage,int pageSize,Long userId);

    Long getMyBiddingRecordCount(Long userId);
}
