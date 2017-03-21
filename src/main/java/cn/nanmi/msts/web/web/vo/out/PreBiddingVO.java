package cn.nanmi.msts.web.web.vo.out;


import cn.nanmi.msts.web.model.PreBiddingDTO;

import java.io.Serializable;

/**
 * Created with cn.nanmi.msts.web.web.vo.out.
 * User: jiangbin
 * Date: 2017/3/16
 * Time: 15:10
 */
public class PreBiddingVO implements Serializable {
    //当前最高竞价
    private Double nowPrice;
    //最小加价
    private Double minMakeUp;
    //最大加价
    private Double maxMakeUp;
    //系统最低单价限制
    private Double minPrice;
    //系统最高单价限制
    private Double maxPrice;

    public PreBiddingVO(PreBiddingDTO preBiddingDTO) {
        if(preBiddingDTO != null){
            this.nowPrice = preBiddingDTO.getNowPrice();
            this.minMakeUp = preBiddingDTO.getMinMakeUp();
            this.maxMakeUp = preBiddingDTO.getMaxMakeUp();
            this.minPrice = preBiddingDTO.getMinPrice();
            this.maxPrice = preBiddingDTO.getMaxPrice();
        }
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
}
