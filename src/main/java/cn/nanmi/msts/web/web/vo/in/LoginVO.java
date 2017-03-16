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
    private String userMail;
    private String password;

    public LoginVO(){}
    public LoginVO(String userMail,String password){
        this.userMail = userMail;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

}
