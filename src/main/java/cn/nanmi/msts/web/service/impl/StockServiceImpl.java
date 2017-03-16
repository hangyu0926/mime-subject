package cn.nanmi.msts.web.service.impl;

import cn.nanmi.msts.web.model.OrderDTO;
import cn.nanmi.msts.web.model.PreBiddingDTO;
import cn.nanmi.msts.web.response.CSResponse;
import cn.nanmi.msts.web.dao.OrderMapper;
import cn.nanmi.msts.web.model.BiddingDTO;
import cn.nanmi.msts.web.service.IStockService;
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

    public CSResponse releaseOrder(OrderDTO orderDTO){

        return orderMapper.releaseOrder(orderDTO);
    }
    @Override
    public List<BiddingDTO> getBiddingList(int startPage,int pageSize,Long userId) {
        return orderMapper.getBiddingList(startPage,pageSize,userId);
    }

    @Override
    public PreBiddingDTO getPreBidding(String orderNo) {
        return orderMapper.getPreBidding(orderNo);
    }
}
