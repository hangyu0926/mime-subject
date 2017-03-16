package cn.nanmi.msts.web.business.impl;

import cn.nanmi.msts.web.business.IStockBusiness;
import cn.nanmi.msts.web.model.BiddingDTO;
import cn.nanmi.msts.web.model.OrderDTO;
import cn.nanmi.msts.web.model.UserDTO;
import cn.nanmi.msts.web.response.CSResponse;
import cn.nanmi.msts.web.service.IStockService;
import cn.nanmi.msts.web.web.vo.in.BiddingListQueryVO;
import cn.nanmi.msts.web.web.vo.out.BiddingListVO;
import cn.nanmi.msts.web.web.vo.out.BiddingVO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
        int page = queryVO.getPageNo();
        int pageSize = queryVO.getPageSize();
        page = page - 1;
        int startPage = page * pageSize ;
        Long bidderId = user.getUserId();

        List<BiddingVO> biddingVOList = new ArrayList<>();

        List<BiddingDTO> biddingList = stockService.getBiddingList(startPage,pageSize,bidderId);

        if(biddingList != null && biddingList.size()>0){
            for(BiddingDTO biddingDTO :biddingList){
                BiddingVO biddingVO = new BiddingVO(biddingDTO);
                if(biddingDTO.getMaxBidder().equals(bidderId)){
                    biddingVO.setBiddingState(2);
                }else{
                    biddingVO.setBiddingState(1);
                }
                biddingVOList.add(biddingVO);
            }
        }
        BiddingListVO biddingListVO = new BiddingListVO(biddingVOList);
        return new CSResponse(biddingListVO);
    }

    public CSResponse releaseOrder(OrderDTO orderDTO){

        return stockService.releaseOrder(orderDTO);
    }
}
