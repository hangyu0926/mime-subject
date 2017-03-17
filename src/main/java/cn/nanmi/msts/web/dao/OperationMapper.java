package cn.nanmi.msts.web.dao;


import cn.nanmi.msts.web.dao.entities.OperationEntity;
import org.springframework.stereotype.Repository;


@Repository("operationMapper")
public interface OperationMapper {
    void addOperation(OperationEntity operationEntity);
}
