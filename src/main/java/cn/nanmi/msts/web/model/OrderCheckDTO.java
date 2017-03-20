package cn.nanmi.msts.web.model;

import java.io.Serializable;

/**
 * Created by hangyu on 2017/3/20.
 */
public class OrderCheckDTO implements Serializable{
    //审核人
    private long auditor;

    //审批流水号
    private String transId;

    //订单号
    private String orderNo;

    //审核类型（1发布审核 2撤销审核）
    private int checkingType;

    //审核意见
    private String checkingView;

    //审核结果（0通过 1不通过）
    private int checkingResult;

    public long getAuditor() {
        return auditor;
    }

    public void setAuditor(long auditor) {
        this.auditor = auditor;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getCheckingType() {
        return checkingType;
    }

    public void setCheckingType(int checkingType) {
        this.checkingType = checkingType;
    }

    public String getCheckingView() {
        return checkingView;
    }

    public void setCheckingView(String checkingView) {
        this.checkingView = checkingView;
    }

    public int getCheckingResult() {
        return checkingResult;
    }

    public void setCheckingResult(int checkingResult) {
        this.checkingResult = checkingResult;
    }
}
