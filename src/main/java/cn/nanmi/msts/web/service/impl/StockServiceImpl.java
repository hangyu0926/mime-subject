package cn.nanmi.msts.web.service.impl;

import cn.nanmi.msts.web.dao.entities.OrderEntity;
import cn.nanmi.msts.web.model.*;
import cn.nanmi.msts.web.response.CSResponse;
import cn.nanmi.msts.web.dao.OrderMapper;
import cn.nanmi.msts.web.service.IStockService;
import cn.nanmi.msts.web.web.vo.in.BiddingListQueryVO;
import org.springframework.stereotype.Service;
import cn.nanmi.msts.web.dao.OrderMapper;
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

    public void releaseOrder(OrderDTO orderDTO){

         orderMapper.releaseOrder(orderDTO);
    }
    @Override
    public List<BiddingDTO> getBiddingList(int startPage,int pageSize,Long userId) {
        return orderMapper.getBiddingList(startPage,pageSize,userId);
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

    public void backoutOrder(String orderNo){

        orderMapper.backoutOrder(orderNo);
    }

    public SystemRules getSystemRules(){
        return orderMapper.getSystemRules();
    }

    public List<OrderDTO> beConfirmedList(int startPage,int pageSize){
        return orderMapper.beConfirmedList(startPage,pageSize);
    }
}
