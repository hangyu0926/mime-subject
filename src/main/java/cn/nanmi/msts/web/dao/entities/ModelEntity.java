package cn.nanmi.msts.web.dao.entities;


import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhanglei on 2016/11/2.
 */
public class ModelEntity implements Serializable {
    private static final long serialVersionUID = 5673707054997529150L;
    //用户id
    private Long userId;
    //用户名
    private String userName;
    //手机号
    private String userMobile;
    //密码密文
    private String loginPass;
    //微信号
    private String userWechat;
    //初始密码
    private String initPass;
    //初始密码密文
    private String initPassMd5;
    //权限id
    private int permissionId;
    //邮箱
    private String mailAddress;
    //是否被删除
    private int deleteFlag;
    //删除执行人
    private Long deleteMan;
    //删除时间
    private Date deleteTime;
    //创建人
    private Long createMan;
    //创建日期
    private Long createTime;

    private String wxUserId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getLoginPass() {
        return loginPass;
    }

    public void setLoginPass(String loginPass) {
        this.loginPass = loginPass;
    }

    public String getUserWechat() {
        return userWechat;
    }

    public void setUserWechat(String userWechat) {
        this.userWechat = userWechat;
    }

    public String getInitPass() {
        return initPass;
    }

    public void setInitPass(String initPass) {
        this.initPass = initPass;
    }

    public String getInitPassMd5() {
        return initPassMd5;
    }

    public void setInitPassMd5(String initPassMd5) {
        this.initPassMd5 = initPassMd5;
    }

    public int getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Long getDeleteMan() {
        return deleteMan;
    }

    public void setDeleteMan(Long deleteMan) {
        this.deleteMan = deleteMan;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public Long getCreateMan() {
        return createMan;
    }

    public void setCreateMan(Long createMan) {
        this.createMan = createMan;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getWxUserId() {
        return wxUserId;
    }

    public void setWxUserId(String wxUserId) {
        this.wxUserId = wxUserId;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userMobile='" + userMobile + '\'' +
                ", loginPass='" + loginPass + '\'' +
                ", userWechat='" + userWechat + '\'' +
                ", initPass='" + initPass + '\'' +
                ", initPassMd5='" + initPassMd5 + '\'' +
                ", permissionId=" + permissionId +
                ", mailAddress='" + mailAddress + '\'' +
                ", deleteFlag=" + deleteFlag +
                ", deleteMan=" + deleteMan +
                ", deleteTime=" + deleteTime +
                ", createMan=" + createMan +
                ", createTime=" + createTime +
                ", wxUserId='" + wxUserId + '\'' +
                '}';
    }
}
