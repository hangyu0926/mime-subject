package cn.nanmi.msts.web.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with cn.nanmi.msts.web.model.
 * User: jiangbin
 * Date: 2017/3/16
 * Time: 10:44
 */
public class BiddingListDTO implements Serializable{
    //订单编号
    private String orderNo;
    //订单股权数
    private Double stockAmt;
    //订单上架时间
    private Date saleTime;
    //卖家id
    private Long sellerId;
    //起拍单价
    private Double initialPrice;
    //最高出价
    private Double maxBiddingPrice;
    //最高出价人id
    private Long maxBidder;
    //订单到期时间
    private Date expireTime;
    //订单创建时间
    private Date createTime;
    //订单状态
    private Long orderState;
    //系统规则
    private Long systemRuleId;

    public BiddingListDTO() {
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

    public Date getSaleTime() {
        return saleTime;
    }

    public void setSaleTime(Date saleTime) {
        this.saleTime = saleTime;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Double getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(Double initialPrice) {
        this.initialPrice = initialPrice;
    }

    public Double getMaxBiddingPrice() {
        return maxBiddingPrice;
    }

    public void setMaxBiddingPrice(Double maxBiddingPrice) {
        this.maxBiddingPrice = maxBiddingPrice;
    }

    public Long getMaxBidder() {
        return maxBidder;
    }

    public void setMaxBidder(Long maxBidder) {
        this.maxBidder = maxBidder;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getOrderState() {
        return orderState;
    }

    public void setOrderState(Long orderState) {
        this.orderState = orderState;
    }

    public Long getSystemRuleId() {
        return systemRuleId;
    }

    public void setSystemRuleId(Long systemRuleId) {
        this.systemRuleId = systemRuleId;
    }
}
