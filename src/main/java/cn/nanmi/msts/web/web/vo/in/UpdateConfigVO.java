package cn.nanmi.msts.web.web.vo.in;

import java.io.Serializable;

/**
 * User: zhanglei
 * Date: 2017/1/16
 * Time: 17:29
 */
public class UpdateConfigVO implements Serializable {
    private int startAmt;
    private int maxAmt;
    private int stockTime;
    private int backTime;
    private int minAdd;
    private int maxAdd;

    public int getStartAmt() {
        return startAmt;
    }

    public void setStartAmt(int startAmt) {
        this.startAmt = startAmt;
    }

    public int getMaxAmt() {
        return maxAmt;
    }

    public void setMaxAmt(int maxAmt) {
        this.maxAmt = maxAmt;
    }

    public int getStockTime() {
        return stockTime;
    }

    public void setStockTime(int stockTime) {
        this.stockTime = stockTime;
    }

    public int getBackTime() {
        return backTime;
    }

    public void setBackTime(int backTime) {
        this.backTime = backTime;
    }

    public int getMinAdd() {
        return minAdd;
    }

    public void setMinAdd(int minAdd) {
        this.minAdd = minAdd;
    }

    public int getMaxAdd() {
        return maxAdd;
    }

    public void setMaxAdd(int maxAdd) {
        this.maxAdd = maxAdd;
    }
}
