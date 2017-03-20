package cn.nanmi.msts.web.web.vo.in;

import java.io.Serializable;

/**
 * Created with cn.nanmi.msts.web.web.vo.in.
 * User: jiangbin
 * Date: 2017/3/20
 * Time: 14:00
 */
public class ConfirmVO implements Serializable {
    //订单编号
    private String orderNo;
    //确认者（0：买家，1：卖家，3：管理员）
    private Integer confirmUser;

    public ConfirmVO() {
    }

    public Integer getConfirmUser() {
        return confirmUser;
    }

    public void setConfirmUser(Integer confirmUser) {
        this.confirmUser = confirmUser;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
