package cn.nanmi.msts.web.dao;


import cn.nanmi.msts.web.dao.entities.TransactionEntity;
import org.springframework.stereotype.Repository;


@Repository("transactionMapper")
public interface TransactionMapper {
    void addTransRecord(TransactionEntity transactionEntity);
}
