package cn.nanmi.msts.web.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with cn.nanmi.msts.web.model.
 * User: jiangbin
 * Date: 2017/3/16
 * Time: 10:44
 */
public class PreBiddingDTO implements Serializable{
    //当前最高竞价
    private Double nowPrice;
    //系统最低单价限制
    private Double minPrice;
    //系统最高单价限制
    private Double maxPrice;
    //最小加价
    private Double minMakeUp;
    //最大加价
    private Double maxMakeUp;
    //当前最高竞价人
    private Long maxBidder;

    public PreBiddingDTO() {
    }

    public Double getNowPrice() {
        return nowPrice;
    }

    public void setNowPrice(Double nowPrice) {
        this.nowPrice = nowPrice;
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

    public Long getMaxBidder() {
        return maxBidder;
    }

    public void setMaxBidder(Long maxBidder) {
        this.maxBidder = maxBidder;
    }
}
