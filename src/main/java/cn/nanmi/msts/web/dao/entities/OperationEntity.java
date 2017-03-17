package cn.nanmi.msts.web.dao.entities;

import java.io.Serializable;

/**
 *  用户操作状态表实体类（用户ID、订单号、操作类型 联合唯一索引）
 * User: jiangbin
 * Date: 2017/3/17
 * Time: 10:58
 */
public class OperationEntity implements Serializable {
    //用户ID
    private Long userId;
    //订单编号
    private String orderNo;
    //操作类型（1-竞拍，2-发布，3-撤销）
    private Integer operationType;
    //操作金额
    private Double operationPrice;

    public OperationEntity() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public Double getOperationPrice() {
        return operationPrice;
    }

    public void setOperationPrice(Double operationPrice) {
        this.operationPrice = operationPrice;
    }
}
