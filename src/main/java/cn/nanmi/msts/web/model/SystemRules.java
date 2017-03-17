package cn.nanmi.msts.web.model;

/**
 * Created by hangyu on 2017/3/17.
 */
public class SystemRules {
    //规则ID
    private long ruleId;

    //竞拍周期
    private int biddingPeriod;

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
}
