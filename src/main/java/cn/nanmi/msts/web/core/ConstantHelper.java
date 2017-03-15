package cn.nanmi.msts.web.core;

/**
 * 功能简述:<br>
 * 定义常量
 *
 * @author Vic Ding
 * @version [V1.0, 2016年11月10日]
 */
public class ConstantHelper {

    /**
     * 业务配置文件
     */
    public final static String BUSINESS_CONF_PROPS = "properties/business_conf.properties";

    /**
     * 默认redis属性配置文件
     */
    public final static String REDIS_PROP = "properties/redis.properties";

    /**
     * 分页默认页数
     */
    public final static Integer DEFAULT_PAGE_NUM = 1;

    /**
     * 禁用的用户
     */
    public final static Integer DISABLE_USER = 1;

    /**
     * 分页默认每页条数
     */
    public final static Integer DEFAULT_PAGE_ROW = 5;

    /**
     * 工作量表分页默认每页条数
     */
    public final static Integer DEFAULT_PAGE_ROW_WORKLOAD_DATA = 15;

    /**
     * 年月日常量
     */
    public static final String DATEFORMATE_YYYYMMDD = "yyyyMMdd";

    public static final String DATEFORMATE_YYYYMM = "yyyyMM";

    public static final String DATEFORMATE_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static final String DATEFORMATE_YYYYMMDDHH = "yyyyMMddHH";

    public static final String DATEFORMATE_YYYY_MM_DD_HHMMSS = "yyyy-MM-dd HH:mm:ss";

    public static final String DATEFORMATE_YYYY_MM_DD = "yyyy-MM-dd";

    public static final String DATEFORMATE_HHMMSS = "HHmmss";

    public static final String DATEFORMATE_CN_TIME = "yyyy年MM月dd日 HH:mm:ss";

    public static final String DATEFORMATE_CN_DATE = "yyyy年MM月dd日";

    public static final String DATEFORMATE_YYYYMMDD_SLASH = "yyyy/MM/dd";

    public static final String DATEFORMATE_FLAG_HHMMSS = "HH:mm:ss";

    public static final String DATEFORMATE_YYYY_MM_DD_HHMMSS_POINT = "yyyy.MM.dd HH:mm:ss";

    public static final String DATEFORMATE_YYYY_MM_DD_HHMMSSSSS = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String WORK_DATA_USER_NAME = "用户姓名";

    public static final String WORK_DATA_USER_WORK_ID = "用户工号";

    public static final String WORK_DATA_ACCEPT_SESSION_NUM = "接入会话量";

    public static final String WORK_DATA_NO_REPLY_SESSION_NUM = "未回复会话量";

    public static final String WORK_DATA_SWITCH_SESSION_NUM = "转接会话量";

    public static final String WORK_DATA_ACTIVE_SESSION_NUM = "主动会话量";

    public static final String WORK_DATA_AVG_RESPONSE_NUM = "平均响应时间";

    public static final String WORK_DATA_LOGIN_TOTAL_TIME = "登陆总时长";

    public static final String WORK_DATA_ONLINE_TOTAL_TIME = "在线总时长";

    public static final String WORK_DATA_JOB_SATURATION = "工作饱和度";

    public static final String WORK_DATA_DATE = "数据日期";

    public static final String GENERATE_EXCEL_EXCLUDE_STR = "serialVersionUID";
    
    public static final String PERMISSION_ISDEFAULT = "1";
    
    public static final String PERMISSION_ISNOTDEFAULT = "2";
    

    public static final String GENERATE_LOGIN_TOTAL_TIME_STR = "loginTotalTime";

    public static final String GENERATE_ONLINE_TOTAL_TIME_STR = "onlineTotalTime";

    public static final String GENERATE_JOB_SATURATION_STR = "jobSaturation";
    /**
     * 用户session
     */
    public static final String USER_SESSION = "USER_SESSION";

    /**
     * 用户登录页面
     */
    public static final String USER_LOGIN = "/views/login.html";
    /**
     * 微信登录页面
     */
    public static final String WX_USER_LOGIN = "/weixin/views/bind.html";

    /**
     * 微信项目列表页面
     * type:"follow":1;"total":2
     */
    public static final String WX_PJ_LIST = "/weixin/views/list.html?type=";
    /**
     * 微信内部页面
     */
    public static final String WX_PJ_INFO = "/weixin/views/info.html?projectId=";

    /**
     * 主页
     */
    public static final String USER_INDEX = "/views/index.html";


    public static final String SECOND_SYMBOL_STR = "s";

    public static final String PERCENT_SYMBOL_STR = "%";

    public static final int PER_INSERT_WORKLOAN_DATA_NUM = 83;

    public static final int ONE_DAY_TOTAL_SECOND = 24 * 60 * 60;

    public static final int ONE_DAY_WORK_TOTAL_SECOND = 8 * 60 * 60;

    public static final int ONE_DAY_TOTAL_M_SECOND = 1000 * 24 * 60 * 60;

    public static final String URL_PERMISSION_FILTER_TURN_ON = "1";

    public static final String URL_PERMISSION_FILTER_TURN_OFF = "0";
    
    /**
     * 会话的上线moduleId
     */
    public static final String SESSION_MODULE_ID = "p00001";
    
    /**
     * 1、显示 2、不显示
     */
    public static final int MODULE_ISSHOW = 1;
    
    /**
     * 模块扩展属性
     */
    public static final String MODULE_ATTR_ISSHOW = "isShow";
    
    public static final String MODULE_ATTR_SESSIONLIMIT = "sessionLimit";
    
    public static final String MODULE_ATTR_LOGOURL = "logoUrl";
    
    public static final String MODULE_ATTR_URL = "url";
}
