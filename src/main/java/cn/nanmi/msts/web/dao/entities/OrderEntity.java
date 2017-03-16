package cn.nanmi.msts.web.dao.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单表实体类
 * User: jiangbin
 * Date: 2017/3/16
 * Time: 10:24
 */
public class OrderEntity implements Serializable{
    //订单编号
    private String orderNo;
    //订单股权数
    private Double stockAmt;
    //起拍单价
    private Double initialPrice;
    //订单状态(1-待审核，2-已取消，3-撤销审核中，4-交易中，5-已撤销，6-竞拍结束，等待确认，7-交易结束，8-流拍)
    private Integer orderStatus;
    //卖家id
    private Long sellerId;
    //最高出价
    private Double maxBiddingPrice;
    //最高出价人id
    private Long maxBidder;
    //卖家确认
    private Integer sellerConfirm;
    //买家确认
    private Integer buyerConfirm;
    //订单创建时间
    private Date createTime;
    //订单上架时间
    private Date saleTime;
    //订单到期时间
    private Date expireTime;
    //系统规则id
    private Long sysRuleId;
    //备注
    private String remarks;

    public OrderEntity() {
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

    public Double getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(Double initialPrice) {
        this.initialPrice = initialPrice;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
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

    public Integer getSellerConfirm() {
        return sellerConfirm;
    }

    public void setSellerConfirm(Integer sellerConfirm) {
        this.sellerConfirm = sellerConfirm;
    }

    public Integer getBuyerConfirm() {
        return buyerConfirm;
    }

    public void setBuyerConfirm(Integer buyerConfirm) {
        this.buyerConfirm = buyerConfirm;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Long getSysRuleId() {
        return sysRuleId;
    }

    public void setSysRuleId(Long sysRuleId) {
        this.sysRuleId = sysRuleId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "orderNo='" + orderNo + '\'' +
                ", stockAmt=" + stockAmt +
                ", initialPrice=" + initialPrice +
                ", orderStatus=" + orderStatus +
                ", sellerId=" + sellerId +
                ", maxBiddingPrice=" + maxBiddingPrice +
                ", maxBidder=" + maxBidder +
                ", sellerConfirm=" + sellerConfirm +
                ", buyerConfirm=" + buyerConfirm +
                ", createTime=" + createTime +
                ", saleTime=" + saleTime +
                ", expireTime=" + expireTime +
                ", sysRuleId=" + sysRuleId +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
