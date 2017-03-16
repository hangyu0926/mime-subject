package cn.nanmi.msts.web.web.vo.out;

import java.io.Serializable;

/**
 * Created by zhanglei on 2016/11/16.
 */
public class UserModelVO implements Serializable {
    private static final long serialVersionUID = -2431880683200080280L;
    
    //用户id
    private Long userId;
    //用户名
    private String userMailAdd;
    private String userMobile;
    private String userName;
    //用户权限
    private int permissionId;
    private int totalStock;
    private int availableStock;

    public UserModelVO() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public String getUserMailAdd() {
        return userMailAdd;
    }

    public void setUserMailAdd(String userMailAdd) {
        this.userMailAdd = userMailAdd;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public int getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
