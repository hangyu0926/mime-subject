package cn.nanmi.msts.web.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with cn.nanmi.msts.web.model.
 * User: jiangbin
 * Date: 2017/3/16
 * Time: 19:02
 */
public class BiddingDetailDTO implements Serializable {
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
    //系统最低单价限制
    private Double minPrice;
    //系统最高单价限制
    private Double maxPrice;
    //最小加价限制
    private Double minMakeUp;
    //最大加价限制
    private Double maxMakeUp;

    public BiddingDetailDTO() {
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

    public Double getMinMakeUp() {
        return minMakeUp;
    }

    public void setMinMakeUp(Double minMakeUp) {
        this.minMakeUp = minMakeUp;
    }

    public Double getMaxMakeUp() {
        return maxMakeUp;
    }

    public void setMaxMakeUp(Double maxMakeUp) {
        this.maxMakeUp = maxMakeUp;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }
}
