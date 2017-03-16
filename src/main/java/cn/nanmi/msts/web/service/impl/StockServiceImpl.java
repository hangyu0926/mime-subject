package cn.nanmi.msts.web.service.impl;

import cn.nanmi.msts.web.dao.OrderMapper;
import cn.nanmi.msts.web.model.BiddingListDTO;
import cn.nanmi.msts.web.service.IStockService;
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

    @Override
    public List<BiddingListDTO> getBiddingList(int startPage,int pageSize) {
        return orderMapper.getBiddingList(startPage,pageSize);
    }
}
