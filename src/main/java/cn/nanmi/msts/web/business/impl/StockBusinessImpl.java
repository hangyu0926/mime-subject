package cn.nanmi.msts.web.business.impl;

import cn.nanmi.msts.web.business.IStockBusiness;
import cn.nanmi.msts.web.model.UserDTO;
import cn.nanmi.msts.web.response.CSResponse;
import cn.nanmi.msts.web.service.IStockService;
import cn.nanmi.msts.web.web.vo.in.BiddingListQueryVO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 股权相关business
 * User: jiangbin
 * Date: 2017/3/16
 * Time: 9:31
 */
@Component("stockBusiness")
public class StockBusinessImpl implements IStockBusiness {

    @Resource
    private IStockService stockService;

    @Override
    public CSResponse getBiddingList(BiddingListQueryVO queryVO,UserDTO user) {




        return null;
    }
}
