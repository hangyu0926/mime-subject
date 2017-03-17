package cn.nanmi.msts.web.service.impl;

import cn.nanmi.msts.web.dao.OperationMapper;
import cn.nanmi.msts.web.dao.entities.OperationEntity;
import cn.nanmi.msts.web.service.IOperationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 股权相关service
 * User: jiangbin
 * Date: 2017/3/16
 * Time: 9:33
 */
@Service("operationService")
public class OperationServiceImpl implements IOperationService {
    @Resource
    private OperationMapper operationMapper;

    @Override
    public void addOperation(OperationEntity operationEntity) {
        operationMapper.addOperation(operationEntity);
    }
}
