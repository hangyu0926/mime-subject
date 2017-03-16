package cn.nanmi.msts.web.web.vo.in;

import java.io.Serializable;

/**
 * Created by memedai on 2017/1/17.
 */
public class AddUserVO implements Serializable{
    private String userMobile;
    private String userMailAdd;
    private String userName;
    private int totalStock;
    private int availableStock;

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserMailAdd() {
        return userMailAdd;
    }

    public void setUserMailAdd(String userMailAdd) {
        this.userMailAdd = userMailAdd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public int getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(int totalStock) {
        this.totalStock = totalStock;
    }

    public int getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(int availableStock) {
        this.availableStock = availableStock;
    }

    @Override
    public String toString() {
        return "AddUserVO{" +
                "userMobile='" + userMobile + '\'' +
                ", userMailAdd='" + userMailAdd + '\'' +
                ", userName='" + userName + '\'' +
                ", totalStock=" + totalStock +
                ", availableStock=" + availableStock +
                '}';
    }
}
