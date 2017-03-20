package cn.nanmi.msts.web.model;

/**
 * Created by hangyu on 2017/3/17.
 */
public class SystemRules {
    //规则ID
    private long ruleId;

    //竞拍周期
    private int biddingPeriod;

    private Double minStockPrice;

    private Double maxStockPrice;

    public long getRuleId() {
        return ruleId;
    }

    public void setRuleId(long ruleId) {
        this.ruleId = ruleId;
    }

    public int getBiddingPeriod() {
        return biddingPeriod;
    }

    public void setBiddingPeriod(int biddingPeriod) {
        this.biddingPeriod = biddingPeriod;
    }

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
}
