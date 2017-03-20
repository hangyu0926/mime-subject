package cn.nanmi.msts.web.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with cn.nanmi.msts.web.model.
 * User: jiangbin
 * Date: 2017/3/17
 * Time: 16:51
 */
public class MyBiddingDTO implements Serializable {
    //订单编号
    private String orderNo;
    //股权数
    private Double stockAmt;
    //起拍单价
    private Double initPrice;
    //上架时间
    private Date saleTime;
    //当前竞价
    private Double nowPrice;
    //最高竞价人id
    private Long bidderId;
    //我的出价
    private Double myPrice;
    //结束时间
    private Date expireTime;
    //订单状态(1-待审核，2-已取消，3-撤销审核中，4-交易中，5-已撤销，6-竞拍结束，等待确认，7-交易结束，8-流拍)
    private Integer orderState;

    public MyBiddingDTO() {
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Double getStockAmt() {
        return stockAmt;
    }

    public void setStockAmt(Double stockAmt) {
        this.stockAmt = stockAmt;
    }

    public Double getInitPrice() {
        return initPrice;
    }

    public void setInitPrice(Double initPrice) {
        this.initPrice = initPrice;
    }

    public Date getSaleTime() {
        return saleTime;
    }

    public void setSaleTime(Date saleTime) {
        this.saleTime = saleTime;
    }

    public Double getNowPrice() {
        return nowPrice;
    }

    public void setNowPrice(Double nowPrice) {
        this.nowPrice = nowPrice;
    }

    public Double getMyPrice() {
        return myPrice;
    }

    public void setMyPrice(Double myPrice) {
        this.myPrice = myPrice;
    }

    public Long getBidderId() {
        return bidderId;
    }

    public void setBidderId(Long bidderId) {
        this.bidderId = bidderId;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }
}
