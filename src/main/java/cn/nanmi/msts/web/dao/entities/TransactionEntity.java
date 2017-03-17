package cn.nanmi.msts.web.dao.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with cn.nanmi.msts.web.dao.entities.
 * User: jiangbin
 * Date: 2017/3/17
 * Time: 15:20
 */
public class TransactionEntity implements Serializable {
    //流水类型（1-竞拍，2-发布，3-撤销）
    private Integer transType;
    //订单编号
    private String orderNo;
    //用户ID
    private Long userId;
    //流水金额
    private Double transAmt;
    //流水时间
    private Date transTime;

    public TransactionEntity() {
    }

    public Integer getTransType() {
        return transType;
    }

    public void setTransType(Integer transType) {
        this.transType = transType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(Double transAmt) {
        this.transAmt = transAmt;
    }

    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }
}
