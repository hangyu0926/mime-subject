package cn.nanmi.msts.web.web.vo.out;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with cn.nanmi.msts.web.web.vo.out.
 * User: jiangbin
 * Date: 2017/3/17
 * Time: 17:52
 */
public class MyBiddingVO implements Serializable{
    //订单编号
    private String orderNo;
    //股权数
    private Double stocksAmt;
    //起拍单价
    private Double initPrice;
    //当前竞价
    private Double nowPrice;
    //我的出价
    private Double myPrice;
    //竞拍状态(1：失败，2：最高出价)
    private Integer biddingState;
    //订单状态（1：交易中，2：已结束）
    private Integer orderState;
    //上架时间
    private Date saleTime;
    //结束时间
    private Date expireTime;

    public MyBiddingVO() {
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Double getStocksAmt() {
        return stocksAmt;
    }

    public void setStocksAmt(Double stocksAmt) {
        this.stocksAmt = stocksAmt;
    }

    public Double getInitPrice() {
        return initPrice;
    }

    public void setInitPrice(Double initPrice) {
        this.initPrice = initPrice;
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

    public Integer getBiddingState() {
        return biddingState;
    }

    public void setBiddingState(Integer biddingState) {
        this.biddingState = biddingState;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public Date getSaleTime() {
        return saleTime;
    }

    public void setSaleTime(Date saleTime) {
        this.saleTime = saleTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}
