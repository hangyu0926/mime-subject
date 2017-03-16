package cn.nanmi.msts.web.web.vo.in;

import java.io.Serializable;

/**
 * Created by wanghailing on 2016/11/4.
 */
public class ModifyPasswordVO implements Serializable{
    private static final long serialVersionUID = 2414733749751729941L;
    private String oldPassword;
    private String newPassword;


    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "ModifyPasswordVO{" +
                ", oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
