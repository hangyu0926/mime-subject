package cn.nanmi.msts.web.business;

import cn.nanmi.msts.web.model.OrderDTO;
import cn.nanmi.msts.web.model.SystemRules;
import cn.nanmi.msts.web.model.UserDTO;
import cn.nanmi.msts.web.response.CSResponse;
import cn.nanmi.msts.web.web.vo.in.BidStockVO;
import cn.nanmi.msts.web.web.vo.in.BiddingListQueryVO;

public interface IStockBusiness {

    /**
     *  查询竞拍列表（分页)
     * @param queryVO
     * @return
     */
    CSResponse getBiddingList(BiddingListQueryVO queryVO,UserDTO user);

    /**
     *  发布订单
     * @param orderDTO
     * @return
     */
    void releaseOrder(OrderDTO orderDTO);

    /**
     * 我的发布
     * @param queryVO
     * @param userId
     * @return
     */
    CSResponse getMyOrder(BiddingListQueryVO queryVO,Long userId);

    /**
     * 撤销订单
     * @param orderNo
     */
    void backoutOrder(String orderNo);

    /**
     * 查询准备竞拍参数
     * @param orderNo
     * @return
     */
    CSResponse getPreBidding(String orderNo);

    /**
     * 竞拍标的
     * @param bidStockVO
     * @return
     */
    CSResponse bidStock(BidStockVO bidStockVO,UserDTO user);

    /**
     * 获取当前规则
     * @return
     */
    SystemRules getSystemRules();

    /**
     * 待确认订单（分页)
     * @param queryVO
     * @return
     */
    CSResponse beConfirmedList(BiddingListQueryVO queryVO);
}
