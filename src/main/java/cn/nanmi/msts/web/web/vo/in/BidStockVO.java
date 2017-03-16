package cn.nanmi.msts.web.web.vo.in;

import java.io.Serializable;

/**
 * User: jiangbin
 * Date: 2017/1/16
 * Time: 17:29
 */
public class BidStockVO implements Serializable {
    //订单号
    private String orderNo;
    //竞拍单价
    private Double biddingPrice;

    public BidStockVO() {
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Double getBiddingPrice() {
        return biddingPrice;
    }

    public void setBiddingPrice(Double biddingPrice) {
        this.biddingPrice = biddingPrice;
    }
}
