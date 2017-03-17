package cn.nanmi.msts.web.business.impl;

import cn.nanmi.msts.web.business.IStockBusiness;
import cn.nanmi.msts.web.enums.ErrorCode;
import cn.nanmi.msts.web.model.*;
import cn.nanmi.msts.web.response.CSResponse;
import cn.nanmi.msts.web.service.IStockService;
import cn.nanmi.msts.web.utils.MathUtil;
import cn.nanmi.msts.web.web.vo.in.BidStockVO;
import cn.nanmi.msts.web.web.vo.in.BiddingListQueryVO;
import cn.nanmi.msts.web.web.vo.out.BiddingListVO;
import cn.nanmi.msts.web.web.vo.out.BiddingVO;
import cn.nanmi.msts.web.web.vo.out.OrderListVO;
import cn.nanmi.msts.web.web.vo.out.PreBiddingVO;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    public void releaseOrder(OrderDTO orderDTO){

         stockService.releaseOrder(orderDTO);
    }

    public CSResponse getMyOrder(BiddingListQueryVO queryVO,Long userId){
        int page = queryVO.getPageNo();
        int pageSize = queryVO.getPageSize();
        int startPage = (page - 1) * pageSize ;
        List<OrderDTO> orderDTOList = stockService.getMyOrder(startPage,pageSize,userId);

        OrderListVO   orderListVO = new OrderListVO(orderDTOList);
        return new CSResponse(orderListVO);
    }

    public void backoutOrder(String orderNo){

        stockService.backoutOrder(orderNo);
    }

    @Override
    public CSResponse getPreBidding(String orderNo) {

        PreBiddingDTO preBiddingDTO = stockService.getPreBidding(orderNo);

        PreBiddingVO preBiddingVO = new PreBiddingVO(preBiddingDTO);

        return new CSResponse(preBiddingVO);
    }

    @Override
    public CSResponse bidStock(BidStockVO bidStockVO,UserDTO user) {
        String orderNo = bidStockVO.getOrderNo();
        Double myPrice = bidStockVO.getBiddingPrice();
        BiddingDetailDTO biddingDetailDTO = stockService.getOrderDetail(orderNo);
        if(biddingDetailDTO == null){
            //没有找到竞拍订单
            return new CSResponse(ErrorCode.NOT_FIND_BIDDING);
        }
        if(biddingDetailDTO.getOrderStatus() !=3 && biddingDetailDTO.getOrderStatus() !=4){
            //订单状态无效
            return new CSResponse(ErrorCode.INVALID_ORDER);
        }
        if(biddingDetailDTO.getSellerId() == user.getUserId()){
            //不能竞拍自己的标的
            return new CSResponse(ErrorCode.FORBID_BIDDING_YOURSELF);
        }
        Double bidMakeup =  MathUtil.sub(biddingDetailDTO.getMaxBiddingPrice(),myPrice);
        if(bidMakeup <= 0 ){
            //您的出价低于当前竞价
            return new CSResponse(ErrorCode.YOUR_PRICE_LOWER);
        }
        if(bidMakeup<=biddingDetailDTO.getMinMakeUp()){
            //您的加价低于最低限制
            return new CSResponse(ErrorCode.LOWER_MIN_MAKEUP);
        }
        if(bidMakeup>=biddingDetailDTO.getMaxMakeUp()){
            //您的加价高于最高限制
            return new CSResponse(ErrorCode.GREATER_MAX_MAKEUP);
        }

        takeBidding(bidStockVO);

        return new CSResponse();
    }

    /**
     * 竞拍订单操作
     * @param bidStockVO
     */
    private void takeBidding(BidStockVO bidStockVO){
        //todo 更新订单表
        //todo 新增用户状态
        //todo 新增用户流水
    }
}
