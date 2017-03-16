package cn.nanmi.msts.web.model;


import java.util.Date;

/**
 * 角色DTO
 */
public class UserDTO {
    private Long userId;//用户id
    private String userName;//用户名
    private String userMobile;//工号
    private String loginPass;//密码
    private String userWechat;
    private String initPass;
    private String initPassMd5;
    private int permissionId;//权限id
    private String permissionName;//权限名称
    private String mailAddress;
    private int deleteFlag;//是否被删除
    private Long deleteMan;//删除执行人
    private Date deleteTime;//删除时间
    private Long createMan;//创建人
    private Date createTime;//创建日期
    private String wxUserId;//微信openid



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

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getWxUserId() {
        return wxUserId;
    }

    public void setWxUserId(String wxUserId) {
        this.wxUserId = wxUserId;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userMobile='" + userMobile + '\'' +
                ", loginPass='" + loginPass + '\'' +
                ", userWechat='" + userWechat + '\'' +
                ", initPass='" + initPass + '\'' +
                ", initPassMd5='" + initPassMd5 + '\'' +
                ", permissionId=" + permissionId +
                ", permissionName='" + permissionName + '\'' +
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
