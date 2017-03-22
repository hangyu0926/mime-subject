package cn.nanmi.msts.web.web.vo.out;

import cn.nanmi.msts.web.model.MyBiddingDTO;

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
    //订单状态（4-交易中，5-已撤销，6-竞拍结束，等待确认，7-交易结束）
    private Integer orderState;
    //上架时间
    private Date saleTime;
    //结束时间
    private Date expireTime;
    //卖家确认
    private Integer buyerConfirm;


    public MyBiddingVO(MyBiddingDTO myBiddingDTO) {
        this.orderNo = myBiddingDTO.getOrderNo();
        this.stocksAmt = myBiddingDTO.getStockAmt();
        this.initPrice = myBiddingDTO.getInitPrice();
        this.nowPrice = myBiddingDTO.getNowPrice();
        this.myPrice = myBiddingDTO.getMyPrice();
        this.saleTime = myBiddingDTO.getSaleTime();
        this.expireTime = myBiddingDTO.getExpireTime();
    }

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

    public Integer getBuyerConfirm() {
        return buyerConfirm;
    }

    public void setBuyerConfirm(Integer buyerConfirm) {
        this.buyerConfirm = buyerConfirm;
    }
}
