package cn.nanmi.msts.web.service;

import cn.nanmi.msts.web.dao.entities.OperationEntity;
import cn.nanmi.msts.web.model.BiddingDTO;
import cn.nanmi.msts.web.model.BiddingDetailDTO;
import cn.nanmi.msts.web.model.OrderDTO;
import cn.nanmi.msts.web.model.PreBiddingDTO;
import cn.nanmi.msts.web.response.CSResponse;

import java.util.List;

/**
 * Created with cn.nanmi.msts.web.service.
 * User: jiangbin
 * Date: 2017/3/16
 * Time: 9:33
 */
public interface IOperationService {

    void addOperation(OperationEntity operationEntity);
}
