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

    public PreBiddingVO(PreBiddingDTO preBiddingDTO) {
        if(preBiddingDTO != null){
            this.nowPrice = preBiddingDTO.getNowPrice();
            this.minMakeUp = preBiddingDTO.getMinMakeUp();
            this.maxMakeUp = preBiddingDTO.getMaxMakeUp();
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
}
