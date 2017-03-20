package cn.nanmi.msts.web.service.impl;

import cn.nanmi.msts.web.dao.OrderMapper;
import cn.nanmi.msts.web.dao.UserMapper;
import cn.nanmi.msts.web.model.*;
import cn.nanmi.msts.web.response.CSResponse;
import cn.nanmi.msts.web.service.IStockService;
import cn.nanmi.msts.web.web.vo.in.UpdateConfigVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * 股权相关service
 * User: jiangbin
 * Date: 2017/3/16
 * Time: 9:33
 */
@Service("stockService")
public class StockServiceImpl implements IStockService {
    @Resource
    private OrderMapper orderMapper;

    @Resource
    private UserMapper userMapper;

    public void releaseOrder(OrderDTO orderDTO){

         orderMapper.releaseOrder(orderDTO);
    }

    public void restoreFrozenStocks(Long sellId,Double stockAmt){

        userMapper.restoreFrozenStocks(sellId,stockAmt);
    }

    public void frozenStocks(Long sellId,Double stockAmt){

        userMapper.frozenStocks(sellId,stockAmt);
    }

    @Override
    public List<BiddingDTO> getBiddingList(int startPage,int pageSize,Long userId) {
        return orderMapper.getBiddingList(startPage, pageSize, userId);
    }

    @Override
    public Long getBiddingListCount(Long userId) {
        return orderMapper.getBiddingListCount(userId);
    }

    @Override
    public PreBiddingDTO getPreBidding(String orderNo) {
        return orderMapper.getPreBidding(orderNo);
    }

    @Override
    public BiddingDetailDTO getOrderDetail(String orderNo) {
        return orderMapper.getOrderDetail(orderNo);
    }

    @Override
    public void updateStatus() {
         orderMapper.updateStatus();
    }



    public List<OrderDTO> getMyOrder(int startPage,int pageSize, Long userId){
        return orderMapper.getMyOrder(startPage,pageSize,userId);
    }

    public Long getMyOrderCount(Long userId){
        return orderMapper.getMyOrderCount(userId);
    }

    public void updateOrderState(String orderNo,int orderState){

        orderMapper.updateOrderState(orderNo,orderState);
    }

    public void updateOrderSaleTime(String orderNo){

        orderMapper.updateOrderSaleTime(orderNo);
    }

    public SystemRules getSystemRules(){
        return orderMapper.getSystemRules();
    }

    public List<OrderDTO> beConfirmedList(int startPage,int pageSize){
        return orderMapper.beConfirmedList(startPage,pageSize);
    }

    public List<OrderDTO> releaseAuditList(int startPage,int pageSize){
        return orderMapper.releaseAuditList(startPage, pageSize);
    }

    public List<OrderDTO> backoutAuditList(int startPage,int pageSize){
        return orderMapper.backoutAuditList(startPage,pageSize);
    }

    public Long beConfirmedListCount(){
        return orderMapper.beConfirmedListCount();
    }

    public Long releaseAuditListCount(){
        return orderMapper.releaseAuditListCount();
    }

    public Long backoutAuditListCount(){
        return orderMapper.backoutAuditListCount();
    }

    @Override
    public void updateOrderBidding(Double biddingPrice, Long bidderId, String orderNo) {
         orderMapper.updateOrderBidding(biddingPrice,bidderId,orderNo);
    }

    public JumpReleaseOrderDTO jumpReleaseOrder(Long userId) {
        return orderMapper.jumpReleaseOrder(userId);
    }

    @Override
    public void insertNewConfig(UpdateConfigVO updateConfigVO) {
        orderMapper.insertNewConfig(updateConfigVO);
    }

    public void releaseAudit(OrderCheckDTO orderCheckDTO) {
        orderMapper.releaseAudit(orderCheckDTO);
    }

    @Override
    public void confirmOrder(Integer orderState, Integer sellerConfirm, Integer buyerConfirm,String orderNo) {
        orderMapper.confirmOrder(orderState,sellerConfirm,buyerConfirm,orderNo);
    }

    @Override
    public List<OrderDTO> getPassOrder() {
        return orderMapper.getPassOrder();
    }

    @Override
    public void updateStatus2Pass() {
        orderMapper.updateStatus2Pass();
    }
}
