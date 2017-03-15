package cn.nanmi.msts.web.web.vo.in;

import java.io.Serializable;

/**
 * User: wanghailing
 * Date: 2016/11/2
 * Project: cs-platform
 * Description:
 */
 public class LoginVO implements Serializable {

    private static final long serialVersionUID = 1582247013154121081L;
    private String userMobile;
    private String password;

    public LoginVO(){}
    public LoginVO(String userMobile, String password){
        this.userMobile = userMobile;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    @Override
    public String toString() {
        return "LoginVO{" +
                "userMobile='" + userMobile + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
