package cn.nanmi.msts.web.model;

import java.io.Serializable;

/**
 * Created by hangyu on 2017/3/17.
 */
public class JumpReleaseOrderDTO implements Serializable {

    //当前最高竞价
    private Double minStockPrice;
    //最小加价
    private Double maxStockPrice;
    //最大加价
    private Double stocksSaleAmt;

    public Double getMinStockPrice() {
        return minStockPrice;
    }

    public void setMinStockPrice(Double minStockPrice) {
        this.minStockPrice = minStockPrice;
    }

    public Double getMaxStockPrice() {
        return maxStockPrice;
    }

    public void setMaxStockPrice(Double maxStockPrice) {
        this.maxStockPrice = maxStockPrice;
    }

    public Double getStocksSaleAmt() {
        return stocksSaleAmt;
    }

    public void setStocksSaleAmt(Double stocksSaleAmt) {
        this.stocksSaleAmt = stocksSaleAmt;
    }
}
