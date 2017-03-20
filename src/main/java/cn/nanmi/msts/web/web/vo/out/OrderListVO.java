package cn.nanmi.msts.web.web.vo.out;

import cn.nanmi.msts.web.model.OrderDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hangyu on 2017/3/17.
 */
public class OrderListVO implements Serializable {
   /* List<OrderVO> orderVOList;

    public OrderListVO(List<OrderVO> orderVOList) {
        this.orderVOList = orderVOList;
    }

    public List<OrderVO> getOrderVOList() {
        return orderVOList;
    }

    public void setOrderVOList(List<OrderVO> orderVOList) {
        this.orderVOList = orderVOList;
    }*/

    List<OrderDTO> orderDTOList;

    private Long totalCount;

    public List<OrderDTO> getOrderDTOList() {
        return orderDTOList;
    }

    public void setOrderDTOList(List<OrderDTO> orderDTOList) {
        this.orderDTOList = orderDTOList;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
}
