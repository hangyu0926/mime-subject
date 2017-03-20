package cn.nanmi.msts.web.service;

import cn.nanmi.msts.web.model.*;
import cn.nanmi.msts.web.response.CSResponse;
import cn.nanmi.msts.web.web.vo.in.UpdateConfigVO;

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
    void releaseOrder(OrderDTO orderDTO);
    /**
     * 查询竞拍列表（分页)
     * @param startPage
     * @param pageSize
     * @return
     */
    List<BiddingDTO> getBiddingList(int startPage,int pageSize,Long userId);

    /**
     * 竞拍列表总页数
     * @param userId
     * @return
     */
    Long getBiddingListCount(Long userId);

    /**
     * 我的发布
     *
     * @param userId
     * @return
     */
    List<OrderDTO> getMyOrder(int startPage,int pageSize, Long userId);

    /**
     * 我的发布
     *
     * @param userId
     * @return
     */
    Long getMyOrderCount(Long userId);

    /**
     * 修改订单状态
     * @param orderNo
     */
    void updateOrderState(String orderNo,int orderState);

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

    /**
     * 获取当前规则
     * @return
     */
    SystemRules getSystemRules();

    /**
     * 待确认订单（分页)
     *
     * @return
     */
    List<OrderDTO> beConfirmedList(int startPage,int pageSize);

    /**
     * 待审核订单-发布审核（分页）
     * @return
     */
    List<OrderDTO> releaseAuditList(int startPage,int pageSize);

    /**
     * 待审核订单-撤销审核（分页）
     * @return
     */
    List<OrderDTO> backoutAuditList(int startPage,int pageSize);

    /**
     * 待确认订单（分页)
     *
     * @return
     */
    Long beConfirmedListCount();

    /**
     * 待审核订单-发布审核（分页）
     * @return
     */
    Long releaseAuditListCount();

    /**
     * 待审核订单-撤销审核（分页）
     * @return
     */
    Long backoutAuditListCount();

    /**
     * 修改订单竞拍出价
     * @param biddingPrice
     * @param bidderId
     * @param orderNo
     */
    void updateOrderBidding(Double biddingPrice,Long bidderId,String orderNo);

    /**
     * 跳转我的发布页
     */
    JumpReleaseOrderDTO jumpReleaseOrder(Long userId);
    /**
     * 修改配置
     */
    void insertNewConfig(UpdateConfigVO updateConfigVO);
    /**
     * 审核新增记录
     */
    void releaseAudit(OrderCheckDTO orderCheckDTO);
}
