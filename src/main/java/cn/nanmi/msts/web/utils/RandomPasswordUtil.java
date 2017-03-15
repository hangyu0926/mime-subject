package cn.nanmi.msts.web.utils;


import java.math.BigDecimal;

/**
 * Created by zhanglei 2016/12/16.
 */
public class RandomPasswordUtil {

    public static String Random() {
       int ps = (int)((Math.random()*9+1)*100000);
        return String.valueOf(ps);
    }
}
