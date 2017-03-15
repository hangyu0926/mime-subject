package cn.nanmi.msts.web.utils;


import java.math.BigDecimal;

/**
 * Created by tizhou on 2016/12/16.
 */
public class NumUtil {

    public static double formatNumHaveTwoDecimal(double number) {
        int scale = 2;//设置位数
        int roundingMode = 4;//表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
        BigDecimal bd = new BigDecimal(number);
        bd = bd.setScale(scale, roundingMode);
        return bd.doubleValue();
    }
}
