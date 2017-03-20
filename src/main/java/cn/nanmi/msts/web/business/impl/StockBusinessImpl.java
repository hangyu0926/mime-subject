package cn.nanmi.msts.web.business.impl;

import cn.nanmi.msts.web.business.IStockBusiness;
import cn.nanmi.msts.web.core.ConstantHelper;
import cn.nanmi.msts.web.dao.entities.OperationEntity;
import cn.nanmi.msts.web.dao.entities.TransactionEntity;
import cn.nanmi.msts.web.enums.ErrorCode;
import cn.nanmi.msts.web.model.*;
import cn.nanmi.msts.web.response.CSResponse;
import cn.nanmi.msts.web.service.IOperationService;
import cn.nanmi.msts.web.service.IStockService;
import cn.nanmi.msts.web.service.ITransactionService;
import cn.nanmi.msts.web.utils.MathUtil;
import cn.nanmi.msts.web.web.vo.in.BidStockVO;
import cn.nanmi.msts.web.web.vo.in.PagedQueryVO;
import cn.nanmi.msts.web.web.vo.in.UpdateConfigVO;
import cn.nanmi.msts.web.web.vo.out.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
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
    @Resource
    private IOperationService operationService;
    @Resource
    private ITransactionService transactionService;

    @Override
    public CSResponse getBiddingList(PagedQueryVO queryVO,Long bidderId) {
        int page = queryVO.getPageNo();
        int pageSize = queryVO.getPageSize();
        page = page - 1;
        int startPage = page * pageSize ;

        List<BiddingVO> biddingVOList = new ArrayList<>();
        //分页查询
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
        //总页数
        Long count = stockService.getBiddingListCount(bidderId);
        BiddingListVO biddingListVO = new BiddingListVO();
        biddingListVO.setBiddingVOList(biddingVOList);
        biddingListVO.setTotalCount(count);
        return new CSResponse(biddingListVO);
    }

    public void releaseOrder(OrderDTO orderDTO){
        stockService.releaseOrder(orderDTO);

        //减去可售股权数，冻结该笔订单股权
        stockService.frozenStocks(orderDTO.getSellerId(),orderDTO.getStockAmt());

        //新增用户流水
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setUserId(orderDTO.getSellerId());
        transactionEntity.setOrderNo(orderDTO.getOrderNo());
        transactionEntity.setTransAmt(orderDTO.getInitialPrice());
        transactionEntity.setTransType(2);
        transactionService.addTransRecord(transactionEntity);
    }

    public CSResponse getMyOrder(PagedQueryVO queryVO,Long userId){
        int page = queryVO.getPageNo();
        int pageSize = queryVO.getPageSize();
        int startPage = (page - 1) * pageSize ;
        List<OrderDTO> orderDTOList = stockService.getMyOrder(startPage,pageSize,userId);

        //总页数
        Long count = stockService.getMyOrderCount(userId);
        OrderListVO   orderListVO = new OrderListVO();
        orderListVO.setOrderDTOList(orderDTOList);
        orderListVO.setTotalCount(count);
        return new CSResponse(orderListVO);
    }

    public void backoutOrder(String orderNo){
        stockService.updateOrderState(orderNo,3);

        BiddingDetailDTO biddingDetailDTO = stockService.getOrderDetail(orderNo);

        //新增用户流水
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setUserId(biddingDetailDTO.getSellerId());
        transactionEntity.setOrderNo(biddingDetailDTO.getOrderNo());
        transactionEntity.setTransAmt(biddingDetailDTO.getMaxBiddingPrice());
        transactionEntity.setTransType(3);
        transactionService.addTransRecord(transactionEntity);
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
        Date now = new Date();
        int spanTime = biddingDetailDTO.getExpireTime().compareTo(now);
        if(spanTime<0){
            //该订单已结束
            return new CSResponse(ErrorCode.ORDER_IS_OVER);
        }
        Double bidMakeup =  MathUtil.sub(myPrice,biddingDetailDTO.getMaxBiddingPrice());
        if(bidMakeup <= 0 ){
            //您的出价低于当前竞价
            return new CSResponse(ErrorCode.YOUR_PRICE_LOWER);
        }
        if(bidMakeup<biddingDetailDTO.getMinMakeUp()){
            //您的加价低于最低限制
            return new CSResponse(ErrorCode.LOWER_MIN_MAKEUP);
        }
        if(bidMakeup>biddingDetailDTO.getMaxMakeUp()){
            //您的加价高于最高限制
            return new CSResponse(ErrorCode.GREATER_MAX_MAKEUP);
        }
        synchronized(orderNo){
            takeBidding(bidStockVO,user.getUserId());
        }
        return new CSResponse();
    }

    /**
     * 竞拍订单操作
     * @param bidStockVO
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void takeBidding(BidStockVO bidStockVO,Long userId){
        //更新订单表,最高出价、最高出价人
        stockService.updateOrderBidding(bidStockVO.getBiddingPrice(),userId,bidStockVO.getOrderNo());

        //用户状态表，新增或更新用户ID、订单号、金额、操作类型
        OperationEntity operationEntity = new OperationEntity();
        operationEntity.setUserId(userId);
        operationEntity.setOrderNo(bidStockVO.getOrderNo());
        operationEntity.setOperationPrice(bidStockVO.getBiddingPrice());
        operationEntity.setOperationType(1);
        operationService.addOperation(operationEntity);

        //新增用户流水
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setUserId(userId);
        transactionEntity.setOrderNo(bidStockVO.getOrderNo());
        transactionEntity.setTransAmt(bidStockVO.getBiddingPrice());
        transactionEntity.setTransType(1);
        transactionService.addTransRecord(transactionEntity);
    }

    public SystemRules getSystemRules(){
        return stockService.getSystemRules();
    }

    public CSResponse beConfirmedList(PagedQueryVO queryVO){
        int page = queryVO.getPageNo();
        int pageSize = queryVO.getPageSize();
        int startPage = (page - 1) * pageSize ;
        List<OrderDTO> orderDTOList = stockService.beConfirmedList(startPage,pageSize);

        //总页数
        Long count = stockService.beConfirmedListCount();
        OrderListVO   orderListVO = new OrderListVO();
        orderListVO.setOrderDTOList(orderDTOList);
        orderListVO.setTotalCount(count);
        return new CSResponse(orderListVO);
    }

    public CSResponse releaseAuditList(PagedQueryVO queryVO){
        int page = queryVO.getPageNo();
        int pageSize = queryVO.getPageSize();
        int startPage = (page - 1) * pageSize ;
        List<OrderDTO> orderDTOList = stockService.releaseAuditList(startPage,pageSize);

        //总页数
        Long count = stockService.releaseAuditListCount();
        OrderListVO   orderListVO = new OrderListVO();
        orderListVO.setOrderDTOList(orderDTOList);
        orderListVO.setTotalCount(count);
        return new CSResponse(orderListVO);
    }

    public CSResponse backoutAuditList(PagedQueryVO queryVO){
        int page = queryVO.getPageNo();
        int pageSize = queryVO.getPageSize();
        int startPage = (page - 1) * pageSize ;
        List<OrderDTO> orderDTOList = stockService.backoutAuditList(startPage,pageSize);

        //总页数
        Long count = stockService.backoutAuditListCount();
        OrderListVO   orderListVO = new OrderListVO();
        orderListVO.setOrderDTOList(orderDTOList);
        orderListVO.setTotalCount(count);
        return new CSResponse(orderListVO);
    }

    public CSResponse jumpReleaseOrder(Long userId) {

        JumpReleaseOrderDTO jumpReleaseOrderDTO = stockService.jumpReleaseOrder(userId);

        return new CSResponse(jumpReleaseOrderDTO);

    }

    @Override
    public CSResponse getMyBiddingRecord(PagedQueryVO pagedQueryVO, Long userId) {
        int page = pagedQueryVO.getPageNo();
        int pageSize = pagedQueryVO.getPageSize();
        page = page - 1;
        int startPage = page * pageSize ;

        List<MyBiddingDTO> myBiddingDTOList = operationService.getMyBiddingRecord(startPage,pageSize,userId);
        Long count = operationService.getMyBiddingRecordCount(userId);

        List<MyBiddingVO> myBiddingVOList = new ArrayList<>();
        if(myBiddingDTOList !=null && myBiddingDTOList.size()>0){
            for(MyBiddingDTO myBiddingDTO:myBiddingDTOList){
                MyBiddingVO myBiddingVO = new MyBiddingVO(myBiddingDTO);
                if(myBiddingDTO.getOrderState() ==3 ){
                    //撤销审核中状态前端显示交易中
                    myBiddingVO.setOrderState(4);
                }else{
                    myBiddingVO.setOrderState(myBiddingDTO.getOrderState());
                }
                if(myBiddingDTO.getBidderId() != userId){
                    //竞拍失败
                    myBiddingVO.setBiddingState(1);
                }else{
                    //最高出价
                    myBiddingVO.setBiddingState(2);
                }
                myBiddingVOList.add(myBiddingVO);
            }
        }
        MyBiddingListVO myBiddingListVO = new MyBiddingListVO();
        myBiddingListVO.setTotalCount(count);
        myBiddingListVO.setMyBiddingVOList(myBiddingVOList);
        return new CSResponse(myBiddingListVO);
    }

    @Override
    public CSResponse updateConfig(HttpServletRequest request, UpdateConfigVO updateConfigVO) {
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute(ConstantHelper.USER_SESSION);
       if(user.getPermissionId()!=1){
           return new CSResponse(ErrorCode.PC_PERMISSION_ERROR);
       }
        stockService.insertNewConfig(updateConfigVO);
        return new CSResponse();
    }

    public void releaseAudit(OrderCheckDTO orderCheckDTO){
        //审核新增记录
        stockService.releaseAudit(orderCheckDTO);

        //修改订单状态
        if (0 == orderCheckDTO.getCheckingResult()){
            stockService.updateOrderState(orderCheckDTO.getOrderNo(),4);
            //添加订单上架时间
            stockService.updateOrderSaleTime(orderCheckDTO.getOrderNo());
        }else{
            stockService.updateOrderState(orderCheckDTO.getOrderNo(),2);

            //发布审核不通过。还原冻结资金
            BiddingDetailDTO biddingDetailDTO = stockService.getOrderDetail(orderCheckDTO.getOrderNo());
            stockService.restoreFrozenStocks(biddingDetailDTO.getSellerId(),biddingDetailDTO.getStockAmt());
        }

    }

    public void backoutAudit(OrderCheckDTO orderCheckDTO){
        //审核新增记录
        stockService.releaseAudit(orderCheckDTO);

        //修改订单状态
        if (0 == orderCheckDTO.getCheckingResult()){
            stockService.updateOrderState(orderCheckDTO.getOrderNo(),5);

            //发布审核不通过。还原冻结资金
            BiddingDetailDTO biddingDetailDTO = stockService.getOrderDetail(orderCheckDTO.getOrderNo());
            stockService.restoreFrozenStocks(biddingDetailDTO.getSellerId(),biddingDetailDTO.getStockAmt());
        }else{
            stockService.updateOrderState(orderCheckDTO.getOrderNo(),4);
        }

    }
}
