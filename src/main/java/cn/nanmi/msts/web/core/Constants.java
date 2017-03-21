package cn.nanmi.msts.web.core;

/**
 * 功能描述:
 * 作者: LDL
 * 创建时间: 2014/7/15 22:04
 */
public class Constants {

    public static final String SESSION_USERNAME = "username";
    public static final String SESSION_TUTORIALS = "tutorials";
    public static final String SESSION_ADMIN = "entity";
    public static final String UN_AUDIT_MESSAGE = "尊敬的%s,您提交的教程%s,因%s不能通过审核，请您按要求修改。";
    public static final String AUDIT_MESSAGE = "尊敬的%s,您提交的教程%s,已通过审核。";
    public static final String SESSION_USER = "user";
    public static final String WEBSOCKET_USERNAME = "websocket_user";
    public static final String INITIAL_PASSWORD= "e10adc3949ba59abbe56e057f20f883e";



    public static final String BUCKETNAME = "msts";
    public static final String COSPICPATH = "/pictures/";
    public static final String TEMP_COSPICPATH = "/temp_pictures/";
    public static final String COSATTACHPATH = "/attachments/";
    public static final String TEMP_COSATTACHPATH = "/temp_attachments/";
    //发布评论最多展示100个汉字
    public static final Integer APPRAISAL_WORD_LIMIT = 100;
    
    //邮件服务器地址
    public static final String HOST = "smtp.exmail.qq.com";
    public static final String SUBJECT = "附件下载";
    //发件人地址
    public static final String FROM = "mimeboard@mi-me.com";
    public static final String USER = "mimeboard@mi-me.com";
    public static final String PWD = "100Days";
    
}
