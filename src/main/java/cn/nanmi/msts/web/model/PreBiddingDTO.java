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
    //最小加价
    private Double minMakeUp;
    //最大加价
    private Double maxMakeUp;

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
}
