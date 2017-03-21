package cn.nanmi.msts.web.business;

import cn.nanmi.msts.web.model.OrderCheckDTO;
import cn.nanmi.msts.web.model.OrderDTO;
import cn.nanmi.msts.web.model.SystemRules;
import cn.nanmi.msts.web.model.UserDTO;
import cn.nanmi.msts.web.response.CSResponse;
import cn.nanmi.msts.web.web.vo.in.BidStockVO;
import cn.nanmi.msts.web.web.vo.in.ConfirmVO;
import cn.nanmi.msts.web.web.vo.in.PagedQueryVO;
import cn.nanmi.msts.web.web.vo.in.UpdateConfigVO;

import javax.servlet.http.HttpServletRequest;

public interface IStockBusiness {

    /**
     *  查询竞拍列表（分页)
     * @param queryVO
     * @return
     */
    CSResponse getBiddingList(PagedQueryVO queryVO,Long bidderId);

    /**
     *  发布订单
     * @param orderDTO
     * @return
     */
    CSResponse releaseOrder(OrderDTO orderDTO);

    /**
     * 我的发布
     * @param queryVO
     * @param userId
     * @return
     */
    CSResponse getMyOrder(PagedQueryVO queryVO,Long userId);

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
    CSResponse beConfirmedList(PagedQueryVO queryVO);

    /**
     * 撤销订单
     * @param updateConfigVO
     */
    CSResponse updateConfig(HttpServletRequest request, UpdateConfigVO updateConfigVO);

    /**
     * 待审核订单-发布审核（分页）
     * @param queryVO
     * @return
     */
    CSResponse releaseAuditList(PagedQueryVO queryVO);


    /**
     * 待审核订单-撤销审核（分页）
     * @param queryVO
     * @return
     */
    CSResponse backoutAuditList(PagedQueryVO queryVO);

    /**
     * 跳转我的发布页
     */
    CSResponse jumpReleaseOrder(Long userId);


    /**
     *  查询我的竞拍列表（分页）
     * @param pagedQueryVO
     * @param userId
     * @return
     */
    CSResponse getMyBiddingRecord(PagedQueryVO pagedQueryVO,Long userId);

    /**
     *  发布审核通过/不通过
     * @param orderCheckDTO
     * @return
     */
    CSResponse releaseAudit(OrderCheckDTO orderCheckDTO);

    /**
     *  撤销审核通过/不通过
     * @param orderCheckDTO
     * @return
     */
    CSResponse backoutAudit(OrderCheckDTO orderCheckDTO);

    /**
     * 确认订单
     * @param confirmVO
     */
    CSResponse confirmOrder(ConfirmVO confirmVO,Long userId);

    /**
     * 每日跑批更新订单状态
     */
    void updateOrderState();
}
