/*
 * Copyright (C), 2013-2016, 南米
 * FileName: DateUtil.java
 * Author:   yanyun
 * Date:     2016-9-30 上午9:57:35
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package cn.nanmi.msts.web.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.nanmi.msts.web.core.ConstantHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 〈日期工具〉<br>
 * 〈功能详细描述〉
 *
 * @author yanyun
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class DateUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

    private static final String ERROR_MSG = "日期转化异常";

    private static final long SIXTY = 60;

    private static final long THOUSAND = 1000;

    private static final long TWENTY_FOUR = 24;

    /**
     * 把日期转换成 yyyyMMdd格式的字符串
     *
     * @param date
     * @return
     */
    public static String getShortStrDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(ConstantHelper.DATEFORMATE_YYYYMMDD);
        return dateFormat.format(date);
    }

    /**
     * 把yyyyMMddHHmmss格式字符串转换成 Date
     *
     * @param dateStr
     * @return
     */
    public static Date getSqlDateTimeByShortStr(String dateStr) {
        Date da = null;
        DateFormat dateFormat = new SimpleDateFormat(ConstantHelper.DATEFORMATE_YYYYMMDDHHMMSS);
        try {
            da = dateFormat.parse(dateStr);
        } catch (Exception e) {
            LOGGER.error(ERROR_MSG, e);
        }
        return da;
    }

    /**
     * 获取指定日期样式yyyy一天开始时间
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param dateStr
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static Date getIndexDateBeginTime(String dateStr) {
        if (dateStr == null || dateStr.equals("")) {
            return null;
        }
        //yyyy-MM-dd HH:mm:ss
        String timestr = dateStr.trim() + " " + "00:00:00";
        return DateUtil.getDateOfTimeStr(timestr, ConstantHelper.DATEFORMATE_YYYY_MM_DD_HHMMSS);
    }

    /**
     * 获取指定日期样式一天最大时间
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param dateStr
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static Date getIndexDateEndTime(String dateStr) {
        if (dateStr == null || dateStr.equals("")) {
            return null;
        }
        String timestr = dateStr.trim() + " " + "23:59:59";
        return DateUtil.getDateOfTimeStr(timestr, ConstantHelper.DATEFORMATE_YYYY_MM_DD_HHMMSS);
    }

    /**
     * 获取指定日期样式一天最大时间
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param dateStr
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static Date getIndexDateAndTypeEndTime(String dateStr) {
        String timestr = dateStr + "235959";
        return DateUtil.getDateOfTimeStr(timestr, ConstantHelper.DATEFORMATE_YYYYMMDDHHMMSS);
    }

    /**
     * 功能描述:根据指定格式，格式化日期 <br>
     * 〈功能详细描述〉
     *
     * @param date
     * @param format
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String formatDate(Date date, String format) {
        SimpleDateFormat dateformat = new SimpleDateFormat(format);
        if (null == date) {
            return null;
        }
        return dateformat.format(date);
    }

    /**
     * 获取yyyyMMddHHmmss当前时间
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String getYYYYMMDDHHMMSSNowTime() {
        return DateUtil.formatDate(new Date(), ConstantHelper.DATEFORMATE_YYYYMMDDHHMMSS);
    }

    /**
     * 功能描述: 格式成带时间的日期，格式：yyyy-MM-dd HH:mm:ss<br>
     * 〈功能详细描述〉
     *
     * @param date
     * @return
     * @author GONGMING
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String formatDateWithTime(Date date) {
        return formatDate(date, ConstantHelper.DATEFORMATE_YYYY_MM_DD_HHMMSS);
    }

    /**
     * 把yyyy-MM-dd HH:mm:ss.S格式的字符串转换成Date
     *
     * @param timeStr
     * @return Timestamp
     */
    public static Date getDateOfTimeStr(String timeStr) {
        DateFormat df = new SimpleDateFormat(ConstantHelper.DATEFORMATE_YYYY_MM_DD_HHMMSS);
        Date da = null;
        try {
            da = df.parse(timeStr);
        } catch (Exception e) {
            LOGGER.error(ERROR_MSG, e);
        }
        return da;
    }

    public static Date getDateOfTimeStr(String timeStr, String formateDateStr) {
        DateFormat df = new SimpleDateFormat(formateDateStr);
        Date da = null;
        try {
            da = df.parse(timeStr);
        } catch (Exception e) {
            LOGGER.error(ERROR_MSG, e);
            da = new Date();
        }
        return da;
    }

    public static Date getDateOfTimeStrZERO(String timeStr, String formateDateStr) {
        DateFormat df = new SimpleDateFormat(formateDateStr);
        Date da = null;
        try {
            da = df.parse(timeStr);
        } catch (Exception e) {
            LOGGER.error(ERROR_MSG, e);
            da = new Date();
        }
        return da;
    }

    /**
     * 把日期转换成 yyyy-MM-dd格式的字符串
     *
     * @param date
     * @return
     */
    public static String getStrDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(ConstantHelper.DATEFORMATE_YYYY_MM_DD);
        return dateFormat.format(date);
    }

    /**
     * 将yyyy-mm-dd格式化成yyyymmdd
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param str
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String getYYYYMMDDStr(String str) {
        DateFormat df = new SimpleDateFormat(ConstantHelper.DATEFORMATE_YYYY_MM_DD);
        Date d;
        try {
            d = df.parse(str);
            DateFormat dateFormat = new SimpleDateFormat(ConstantHelper.DATEFORMATE_YYYYMMDD);
            return dateFormat.format(d);
        } catch (ParseException e) {
            LOGGER.error(ERROR_MSG, e);
            return "";
        }
    }

    /**
     * 把yyyy-MM-dd HH:mm:ss格式字符串转换成Date
     *
     * @param dateStr
     * @return
     */
    public static Date getSqlDateTimeByStr(String dateStr) {
        Date da = null;
        DateFormat dateFormat = new SimpleDateFormat(ConstantHelper.DATEFORMATE_YYYY_MM_DD_HHMMSS);
        try {
            da = dateFormat.parse(dateStr);
        } catch (Exception e) {
            LOGGER.error(ERROR_MSG, e);
        }

        return da;
    }

    /**
     * 获取距离今天 delay 天的日期 yyyy-MM-dd
     *
     * @param delay
     * @return
     */
    public static String getDateStringDelay(int delay) {
        return formateDateStr(DateUtil.addDate(new Date(), delay));
    }

    /**
     * 获取距离今天 之前 delay 天的日期 yyyy-MM-dd
     *
     * @param delay
     * @return
     */
    public static String getDateStringSub(int delay) {
        return formateDateStr(DateUtil.subDate(new Date(), delay));
    }

    /**
     * 日期相加
     *
     * @param date 日期
     * @param day  天数
     * @return 返回相加后的日期
     */
    public static Date addDate(Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) + ((long) day) * TWENTY_FOUR * SIXTY * SIXTY * THOUSAND);
        return c.getTime();
    }

    /**
     * 日期相减
     *
     * @param date 日期
     * @param day  天数
     * @return 返回相减后的日期
     */
    public static Date subDate(Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) - ((long) day) * TWENTY_FOUR * SIXTY * SIXTY * THOUSAND);
        return c.getTime();
    }

    /**
     * 返回指定天数的毫秒
     *
     * @param day int类型的天数
     */
    public static long getMilliSeconds(int day) {
        return (long) day * TWENTY_FOUR * SIXTY * SIXTY * THOUSAND;
    }

    /**
     * 返回毫秒
     *
     * @param date 日期
     * @return 返回毫秒
     */
    public static long getMillis(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }

    /**
     * 日期格式 yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String formateDateStr(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(ConstantHelper.DATEFORMATE_YYYY_MM_DD);
        return formateDate(date, dateFormat);
    }

    /**
     * 格式化日期格式返回字符串
     *
     * @param date
     * @param dateFormat
     * @return
     */
    public static String formateDate(Date date, DateFormat dateFormat) {
        return dateFormat.format(date);
    }

    /**
     * 日期格式 yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String formateDateString(Date date) {
        if (null == date) {
            return "";
        }
        DateFormat dateFormat = new SimpleDateFormat(ConstantHelper.DATEFORMATE_YYYY_MM_DD);
        return formateDate(date, dateFormat);

    }

    /**
     * 把yyyy-MM-dd格式的字符串转换成Date
     *
     * @param dateStr
     * @return
     */
    public static Date getDateOfStr(String dateStr) {
        DateFormat df = new SimpleDateFormat(ConstantHelper.DATEFORMATE_YYYY_MM_DD);
        Date da = null;
        try {
            da = df.parse(dateStr);
        } catch (Exception e) {
            LOGGER.error(ERROR_MSG, e);
        }
        return da;

    }

    /**
     * 把日期转换成 yyyyMMddHHmmss格式的字符串
     *
     * @param date
     * @return
     */
    public static String getShortStrDateTime(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(ConstantHelper.DATEFORMATE_YYYYMMDDHHMMSS);
        return dateFormat.format(date);
    }

    public static String getShortStrByyyyyMMddHH(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(ConstantHelper.DATEFORMATE_YYYYMMDDHH);
        return dateFormat.format(date);
    }

    /**
     * get current date
     *
     * @return Date
     */
    public static Date getCurDate() {
        return getDate(getCurTime());
    }

    /**
     * 根据Timestamp获得日期
     *
     * @param time
     * @return
     */
    public static Date getDate(Timestamp time) {
        return (new Date(time.getTime()));
    }

    /**
     * get current time
     *
     * @return Timestamp
     */
    public static Timestamp getCurTime() {
        return new Timestamp(Calendar.getInstance().getTime().getTime());
    }

    public static String getTime() {
        Timestamp time = new Timestamp(Calendar.getInstance().getTime().getTime());
        return time.toString();
    }

    /**
     * 日期相加
     *
     * @param date 日期
     * @param day  天数
     * @return 返回相加后的日期
     */
    public static Date minusDate(Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) - ((long) day) * TWENTY_FOUR * SIXTY * SIXTY * THOUSAND);
        return c.getTime();
    }

    /**
     * 日期相加
     *
     * @param date 日期
     * @param day  天数
     * @return 返回相加后的日期
     */
    public static Date addDate(Date date, double day) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis((long) (getMillis(date) + day * TWENTY_FOUR * SIXTY * SIXTY * THOUSAND));
        return c.getTime();
    }

    /**
     * 分钟相加
     *
     * @param date
     * @param minite
     * @return
     */
    public static Date addMinute(Date date, double minute) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis((long) (getMillis(date) + minute * SIXTY * THOUSAND));
        return c.getTime();
    }

    /**
     * 功能描述: <br>
     * 某日期增加n小时
     *
     * @param date
     * @param hours
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static Date addHours(Date date, Integer hours) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis((getMillis(date) + (long) hours * SIXTY * SIXTY * THOUSAND));
        return c.getTime();
    }

    /**
     * 功能描述: <br>
     * 某日期减少n小时
     *
     * @param date
     * @param hours
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static Date subHours(Date date, Integer hours) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis((getMillis(date) - (long) hours * SIXTY * SIXTY * THOUSAND));
        return c.getTime();
    }

    /**
     * 功能描述: <br>
     * 某日期加n秒
     *
     * @param date
     * @param hours
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static Date addSeconds(Date date, Integer seconds) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis((getMillis(date) + seconds * THOUSAND));
        return c.getTime();
    }

    /**
     * 日期相减
     *
     * @param date  日期
     * @param date1 日期
     * @return 返回相减后的日期
     */
    public static int diffDate(Date date, Date date1) {
        return (int) ((getMillis(date) - getMillis(date1)) / (TWENTY_FOUR * SIXTY * SIXTY * THOUSAND));
    }

    public static int diffDateToHour(Date date, Date date1) {
        return (int) ((getMillis(date) - getMillis(date1)) / (THOUSAND * SIXTY * SIXTY));
    }

    public static int diffDateToMIn(Date date, Date date1) {
        long seconds = date.getTime() - date1.getTime();
        return (int) (seconds / SIXTY);
    }

    /**
     * 功能描述: <br>
     * 两个日期间隔多少秒
     *
     * @param firstDate
     * @param secondDate
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static int diffSeconds(Date firstDate, Date secondDate) {
        return (int) ((getMillis(firstDate) - getMillis(secondDate)) / THOUSAND);
    }

    /**
     * 把yyyyMMdd格式字符串转换成 Date
     *
     * @param dateStr
     * @return
     */
    public static Date getUtilDateByShortStr(String datestr) {
        DateFormat dateFormat = new SimpleDateFormat(ConstantHelper.DATEFORMATE_YYYYMMDD);
        try {
            return dateFormat.parse(datestr);
        } catch (ParseException e) {
            LOGGER.error(ERROR_MSG, e);
        }
        return null;
    }

    public static String formatDateToStr(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(ConstantHelper.DATEFORMATE_YYYY_MM_DD_HHMMSS);
        return dateFormat.format(date);
    }

    public static Date getForwardTime(Date currentDate, Long forwardValue) {
        Long currentValue = Long.valueOf(currentDate.getTime());
        Long startValue = Long.valueOf(currentValue.longValue() - forwardValue.longValue());
        Date forwardDate = new Date(startValue.longValue());
        return forwardDate;
    }

    /**
     * 功能描述: <br>
     * 根据开始时间和结束时间参数判断当前时间是否在该时间范围内
     *
     * @param startTime
     * @param endTime
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static boolean inActiveTime(String startTime, String endTime) {
        Date startDate = DateUtil.getSqlDateTimeByStr(startTime);
        Date deadDate = DateUtil.getSqlDateTimeByStr(endTime);
        return betweenDates(startDate, deadDate);
    }

    public static boolean betweenDates(Date startDate, Date deadDate) {
        Date currDate = new Date();
        if (deadDate == null) {
            return currDate.after(startDate);
        }
        return currDate.after(startDate) && currDate.before(deadDate);
    }

    /**
     * 把yyyyMMdd格式的字符串转换成Date
     *
     * @param dateStr
     * @return
     */
    public static Date getDateOfShortStr(String dateStr) {
        Date da = null;
        DateFormat dateFormat = new SimpleDateFormat(ConstantHelper.DATEFORMATE_YYYYMMDD);
        try {
            da = new Date(dateFormat.parse(dateStr).getTime());
        } catch (Exception e) {
            LOGGER.error(ERROR_MSG, e);
        }
        return da;
    }

    public static Date parseDate(String datestr, String dateFormate) {
        if (datestr == null || "".equals(datestr)) {
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat(dateFormate);
        try {
            return df.parse(datestr);
        } catch (Exception e) {
            LOGGER.error(ERROR_MSG, e);
        }
        return null;
    }

    public static String timeStampToString(Timestamp timestamp) {
        if (null == timestamp) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat(ConstantHelper.DATEFORMATE_YYYY_MM_DD_HHMMSS);
        try {
            return df.format(timestamp);
        } catch (Exception e) {
            LOGGER.error(ERROR_MSG, e);
        }
        return null;
    }

    /**
     * 传入日期加几个月
     *
     * @param dateStr
     * @return
     */
    public static Date addMonth(Date date, int addMonth) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.add(Calendar.MONTH, addMonth);
        date = cl.getTime();
        return date;
    }

    /**
     * string类型的毫米转换成格式String
     * str:需要转换的字符串
     * formatType:转换的格式
     */
    public static String msStrToFormatStr(String str, String formatType) {
        if (StringUtils.isBlank(str) || StringUtils.isBlank(formatType)) {
            return null;
        }
//    	SimpleDateFormat sdf = new SimpleDateFormat(formatType);
        try {
            Long millionSeconds = Long.parseLong(str);
            String returnStr = longToString(millionSeconds, formatType);
            return returnStr;
        } catch (ParseException e) {
            LOGGER.error("日期转换异常,异常如下", e);
            return null;
        }
    }

    /**
     * string类型转换为date类型
     * strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
     * HH时mm分ss秒，
     * strTime的时间格式必须要与formatType的时间格式相同
     *
     * @param strTime
     * @param formatType
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    /**
     * long转换为Date类型
     * currentTime要转换的long类型的时间
     * formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
     *
     * @param currentTime
     * @param formatType
     * @return
     * @throws ParseException
     */
    public static String longToString(long currentTime, String formatType)
            throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        return sDateTime;
    }

    /**
     * date类型转换为String类型
     * formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
     * data Date类型的时间
     *
     * @param data
     * @param formatType
     * @return
     */
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    /**
     * 比较输入的两个日期大小
     *
     * @param startDate
     * @param endDate
     * @param formatType
     * @return 0 为endDate < startDate 非法输入
     * 1 为 startDate < endDate
     * 2 为 两者为同一日期
     */
    public static int compareStringTime(String startDate, String endDate, String formatType) {
        int result = 0;

        if (StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate) || StringUtils.isBlank(formatType)) {
            LOGGER.error("compareStringTime input startDate is {} , endDate is {}, formatType is {}", startDate, endDate, formatType);
            return result;
        }

        Date start = null;
        Date end = null;
        try {
            start = stringToDate(startDate, formatType);
            end = stringToDate(endDate, formatType);
            int compareInt = start.compareTo(end);
            if (compareInt == -1) {
                result = 1;
            } else if (compareInt == 0) {
                result = 2;
            } else {
                result = 0;
            }
        } catch (Exception e) {
            LOGGER.error("compareStringTime Exception is {}", e);
        } finally {
            return result;
        }
    }

    /**
     * 获取DATE那天的最后一秒
     *
     * @param date
     * @return
     */
    public static long getOneDayLastSec(Date date) {
        long resultMis = 0L;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String str = format.format(date);
        Date d2;
        try {
            d2 = format.parse(str);
        } catch (ParseException e) {
            return resultMis;
        }

        //返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。
        long curMillisecond = d2.getTime();//当天的毫秒
        resultMis = curMillisecond + (ConstantHelper.ONE_DAY_TOTAL_M_SECOND - 1); //当天最后一秒
        return resultMis;
    }

    /**
     * 获取DATE那天的第一秒
     *
     * @param date
     * @return
     */
    public static long getOneDayFirstSec(Date date) {
        long resultMis = 0L;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String str = format.format(date);
        Date d2;
        try {
            d2 = format.parse(str);
        } catch (ParseException e) {
            return resultMis;
        }

        //返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。
        long curMillisecond = d2.getTime();//当天的毫秒
        resultMis = curMillisecond; //当天第一秒
        return resultMis;

    }
}
