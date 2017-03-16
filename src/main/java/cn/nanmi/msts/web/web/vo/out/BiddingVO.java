package cn.nanmi.msts.web.web.vo.out;

import cn.nanmi.msts.web.model.BiddingDTO;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with cn.nanmi.msts.web.web.vo.out.
 * User: jiangbin
 * Date: 2017/3/16
 * Time: 15:10
 */
public class BiddingVO implements Serializable {
    //订单编号
    private String orderNo;
    //订单股权数
    private Double stockAmt;
    //起拍单价
    private Double initPrice;
    //当前竞价
    private Double nowPrice;
    //卖家姓名
    private String sellerName;
    //上架时间
    private Date saleTime;
    //结束时间
    private Date expireTime;
    //竞价状态(1-可以竞价，2-当前最高竞价人，不可竞价)
    private Integer biddingState;

    public BiddingVO(BiddingDTO biddingDTO) {
        if(biddingDTO !=null){
           this.orderNo = biddingDTO.getOrderNo();
           this.stockAmt = biddingDTO.getStockAmt();
           this.initPrice = biddingDTO.getInitialPrice();
           this.nowPrice = biddingDTO.getMaxBiddingPrice();
           this.sellerName = biddingDTO.getSellerName();
           this.saleTime = biddingDTO.getSaleTime();
           this.expireTime = biddingDTO.getExpireTime();
        }
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

    public Double getNowPrice() {
        return nowPrice;
    }

    public void setNowPrice(Double nowPrice) {
        this.nowPrice = nowPrice;
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

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public Integer getBiddingState() {
        return biddingState;
    }

    public void setBiddingState(Integer biddingState) {
        this.biddingState = biddingState;
    }
}
