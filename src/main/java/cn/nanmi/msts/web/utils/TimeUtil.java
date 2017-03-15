package cn.nanmi.msts.web.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by memedai on 2016/11/21.
 */
public class TimeUtil {
    private static SimpleDateFormat formatter   =  new   SimpleDateFormat( "yyyy-MM-dd ");

    public static String timeStamp2Date(Date date){
        return  formatter.format(date);
    }
}
