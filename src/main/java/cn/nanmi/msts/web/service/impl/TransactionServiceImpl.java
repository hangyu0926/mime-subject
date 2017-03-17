package cn.nanmi.msts.web.service.impl;

import cn.nanmi.msts.web.dao.TransactionMapper;
import cn.nanmi.msts.web.dao.entities.TransactionEntity;
import cn.nanmi.msts.web.service.ITransactionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 股权相关service
 * User: jiangbin
 * Date: 2017/3/16
 * Time: 9:33
 */
@Service("transactionService")
public class TransactionServiceImpl implements ITransactionService {
    @Resource
    private TransactionMapper transactionMapper;

    @Override
    public void addTransRecord(TransactionEntity transactionEntity) {
        transactionMapper.addTransRecord(transactionEntity);
    }
}
