package cn.nanmi.msts.web.web.vo.out;

import cn.nanmi.msts.web.model.OrderDTO;

import java.util.Date;
import java.util.List;

/**
 * Created by hangyu on 2017/3/17.
 */
public class OrderNoListVO {
   List<String> orders;

    public List<String> getOrders() {
        return orders;
    }

    public void setOrders(List<String> orders) {
        this.orders = orders;
    }
}
