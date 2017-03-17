package cn.nanmi.msts.web.service;

import cn.nanmi.msts.web.dao.entities.OrderEntity;
import cn.nanmi.msts.web.model.BiddingDTO;
import cn.nanmi.msts.web.model.BiddingDetailDTO;
import cn.nanmi.msts.web.model.OrderDTO;
import cn.nanmi.msts.web.model.PreBiddingDTO;
import cn.nanmi.msts.web.response.CSResponse;
import org.apache.ibatis.annotations.Param;

import javax.swing.text.html.parser.Entity;
import java.util.List;

/**
 * Created with cn.nanmi.msts.web.service.
 * User: jiangbin
 * Date: 2017/3/16
 * Time: 9:33
 */
public interface IStockService {

    /**
     *  发布订单
     * @param orderDTO
     * @return
     */
    CSResponse releaseOrder(OrderDTO orderDTO);
    /**
     * 查询竞拍列表（分页)
     * @param startPage
     * @param pageSize
     * @return
     */
    List<BiddingDTO> getBiddingList(int startPage,int pageSize,Long userId);

    /**
     * 查询准备竞拍参数
     * @param orderNo
     * @return
     */
    PreBiddingDTO getPreBidding(String orderNo);

    /**
     * 查询订单详情
     * @param orderNo
     * @return
     */
    BiddingDetailDTO getOrderDetail(String orderNo);

    /**
     * 更新订单状态
     * @return
     */
    void updateStatus();


}
