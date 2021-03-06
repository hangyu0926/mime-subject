package cn.nanmi.msts.web.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hangyu on 2017/3/16.
 */
public class OrderDTO implements Serializable {
    //订单id
    private long orderId;
    //订单编号
    private String orderNo;
    //订单股权数
    private Double stockAmt;
    //起拍单价
    private Double initialPrice;
    //订单状态
    private int orderState;
     //卖家id
    private Long sellerId;
    //卖家姓名
    private String sellerName;
    //最高出价
    private Double maxBiddingPrice;
    //最高出价人id
    private Long maxBidder;
    //创建时间
    private Date createTime;
    //订单上架时间
    private Date saleTime;
    //订单到期时间
    private Date expireTime;
    //系统规则
    private Long systemRuleId;

    //规则竞拍周期
    private int biddingPeriod;

    //该订单的竞价轮数
    private int buyerCounts;

    //是否能撤销（0：可撤销 1：不可撤销）
    private int hasbackout;

    //卖家是否确认订单（0：未确认 1：已确认）
    private int sellerConfirm;

    //当前购买者姓名
    private String buyName;

    //最终买家姓名
    private String userName;
    private String userMobile;//最终买家手机

    public String getBuyName() {
        return buyName;
    }

    public void setBuyName(String buyName) {
        this.buyName = buyName;
    }

    public int getSellerConfirm() {
        return sellerConfirm;
    }

    public void setSellerConfirm(int sellerConfirm) {
        this.sellerConfirm = sellerConfirm;
    }

    public int getHasbackout() {
        return hasbackout;
    }

    public void setHasbackout(int hasbackout) {
        this.hasbackout = hasbackout;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
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

    public int getOrderState() {
        return orderState;
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
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

    public Long getSystemRuleId() {
        return systemRuleId;
    }

    public void setSystemRuleId(Long systemRuleId) {
        this.systemRuleId = systemRuleId;
    }

    public int getBiddingPeriod() {
        return biddingPeriod;
    }

    public void setBiddingPeriod(int biddingPeriod) {
        this.biddingPeriod = biddingPeriod;
    }

    public int getBuyerCounts() {
        return buyerCounts;
    }

    public void setBuyerCounts(int buyerCounts) {
        this.buyerCounts = buyerCounts;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }
}
