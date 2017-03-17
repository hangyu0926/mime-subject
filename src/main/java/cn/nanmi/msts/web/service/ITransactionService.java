package cn.nanmi.msts.web.service;

import cn.nanmi.msts.web.dao.entities.TransactionEntity;

/**
 * Created with cn.nanmi.msts.web.service.
 * User: jiangbin
 * Date: 2017/3/16
 * Time: 9:33
 */
public interface ITransactionService {
    void addTransRecord(TransactionEntity transactionEntity);
}
