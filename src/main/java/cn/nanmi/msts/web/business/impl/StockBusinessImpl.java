package cn.nanmi.msts.web.business.impl;

import cn.nanmi.msts.web.business.IStockBusiness;
import cn.nanmi.msts.web.business.IUserBusiness;
import cn.nanmi.msts.web.core.ConstantHelper;
import cn.nanmi.msts.web.core.Constants;
import cn.nanmi.msts.web.dao.entities.OperationEntity;
import cn.nanmi.msts.web.dao.entities.TransactionEntity;
import cn.nanmi.msts.web.enums.ErrorCode;
import cn.nanmi.msts.web.model.*;
import cn.nanmi.msts.web.response.CSResponse;
import cn.nanmi.msts.web.service.IOperationService;
import cn.nanmi.msts.web.service.IStockService;
import cn.nanmi.msts.web.service.ITransactionService;
import cn.nanmi.msts.web.service.IUserService;
import cn.nanmi.msts.web.utils.BusinessConfUtil;
import cn.nanmi.msts.web.utils.DateUtil;
import cn.nanmi.msts.web.utils.MathUtil;
import cn.nanmi.msts.web.utils.SendMail;
import cn.nanmi.msts.web.web.vo.in.BidStockVO;
import cn.nanmi.msts.web.web.vo.in.ConfirmVO;
import cn.nanmi.msts.web.web.vo.in.PagedQueryVO;
import cn.nanmi.msts.web.web.vo.in.UpdateConfigVO;
import cn.nanmi.msts.web.web.vo.out.*;
import org.apache.commons.lang3.StringUtils;
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
public class StockBusinessImpl extends BaseBussinessImpl implements IStockBusiness {

    @Resource
    private IStockService stockService;
    @Resource
    private IOperationService operationService;
    @Resource
    private ITransactionService transactionService;
    @Resource
    private IUserService userService;
//    private String fileServerOutPath;

    @Override
    public CSResponse getBiddingList(PagedQueryVO queryVO, Long bidderId) {
        int page = queryVO.getPageNo();
        int pageSize = queryVO.getPageSize();
        page = page - 1;
        int startPage = page * pageSize;

        List<BiddingVO> biddingVOList = new ArrayList<>();
        //分页查询
        List<BiddingDTO> biddingList = stockService.getBiddingList(startPage, pageSize, bidderId);
        if (biddingList != null && biddingList.size() > 0) {
            for (BiddingDTO biddingDTO : biddingList) {
                BiddingVO biddingVO = new BiddingVO(biddingDTO);
                if (biddingDTO.getMaxBidder() != null && biddingDTO.getMaxBidder().equals(bidderId)) {
                    biddingVO.setBiddingState(2);
                } else {
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

    public CSResponse releaseOrder(OrderDTO orderDTO) {
       /* String  curDate = DateUtil.getShortStrDate(new Date());

        long num = stockService.queryTodayOrder(curDate);

        String nums = String.valueOf(num+1);

        if (nums.length() >= 1000){
            return new CSResponse(ErrorCode.TODAY_RELEASE_HIGHER);
        }

        if (nums.length() == 1){
            nums = "00" + nums;
        }
        if (nums.length() == 2){
            nums = "0" + nums;
        }

        orderDTO.setOrderNo(curDate+nums);*/

        orderDTO.setOrderNo(DateUtil.getMillisecond(new Date()));

        stockService.releaseOrder(orderDTO);

        //减去可售股权数，冻结该笔订单股权
        stockService.frozenStocks(orderDTO.getSellerId(), orderDTO.getStockAmt());

        //新增用户流水
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setUserId(orderDTO.getSellerId());
        transactionEntity.setOrderNo(orderDTO.getOrderNo());
        transactionEntity.setTransAmt(orderDTO.getInitialPrice());
        transactionEntity.setTransType(2);
        transactionService.addTransRecord(transactionEntity);

        //发送邮件
        try{
            Boolean result = sendEmail(orderDTO.getOrderNo(),1);
            if(!result){
                LOGGER.error("发布股权邮件发送失败，订单号:{}，用户ID:{}", orderDTO.getOrderNo(), orderDTO.getSellerId());
            }
        }catch (Exception e){
            LOGGER.error("发布股权邮件发送异常，订单号:{}，用户ID:{}",orderDTO.getOrderNo(),orderDTO.getSellerId());
        }

        return new CSResponse();
    }

    public CSResponse getMyOrder(PagedQueryVO queryVO, Long userId) {
        int page = queryVO.getPageNo();
        int pageSize = queryVO.getPageSize();
        int startPage = (page - 1) * pageSize;
        List<OrderDTO> orderDTOList = stockService.getMyOrder(startPage, pageSize, userId);

        if (orderDTOList != null && orderDTOList.size() > 0) {
            for (OrderDTO orderDTO : orderDTOList) {
                //6-竞拍结束，等待确认，7-交易结束 我的发布可以看到买方信息，姓名电话。
                if (6 == orderDTO.getOrderState() || 7 == orderDTO.getOrderState()){
                    UserDTO userDTO =  userService.getUserById(orderDTO.getMaxBidder());
                    orderDTO.setUserName(userDTO.getUserName());
                    orderDTO.setUserMobile(userDTO.getUserMobile());
                }else{
                    orderDTO.setUserName("--");
                    orderDTO.setUserMobile("--");
                }
            }
        }

        //总页数
        Long count = stockService.getMyOrderCount(userId);
        OrderListVO orderListVO = new OrderListVO();
        orderListVO.setOrderDTOList(orderDTOList);
        orderListVO.setTotalCount(count);
        return new CSResponse(orderListVO);
    }

    public void backoutOrder(String orderNo) {
        stockService.updateOrderState(orderNo, 3);

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
    public CSResponse bidStock(BidStockVO bidStockVO, UserDTO user) {
        String orderNo = bidStockVO.getOrderNo();
        Double myPrice = bidStockVO.getBiddingPrice();
        BiddingDetailDTO biddingDetailDTO = stockService.getOrderDetail(orderNo);
        if (biddingDetailDTO == null) {
            //没有找到竞拍订单
            return new CSResponse(ErrorCode.NOT_FIND_BIDDING);
        }
        if (biddingDetailDTO.getOrderStatus() != 3 && biddingDetailDTO.getOrderStatus() != 4) {
            //订单状态无效
            return new CSResponse(ErrorCode.INVALID_ORDER);
        }
        if (biddingDetailDTO.getSellerId() == user.getUserId()) {
            //不能竞拍自己的标的
            return new CSResponse(ErrorCode.FORBID_BIDDING_YOURSELF);
        }
        Date now = new Date();
        int spanTime = biddingDetailDTO.getExpireTime().compareTo(now);
        if (spanTime < 0) {
            //该订单已结束
            return new CSResponse(ErrorCode.ORDER_IS_OVER);
        }
        if(myPrice < biddingDetailDTO.getMinPrice() ){
            //您的每股单价小于起拍单价
            return new CSResponse(ErrorCode.YOUR_RELEASE_LOWER);
        }
        if(myPrice > biddingDetailDTO.getMaxPrice() ){
            //您的每股单价大于最大单价
            return new CSResponse(ErrorCode.YOUR_RELEASE_HIGHER);
        }
        Double bidMakeup = MathUtil.sub(myPrice, biddingDetailDTO.getMaxBiddingPrice());
        if (bidMakeup <= 0) {
            //您的出价低于当前竞价
            return new CSResponse(ErrorCode.YOUR_PRICE_LOWER);
        }
        if (bidMakeup < biddingDetailDTO.getMinMakeUp()) {
            //您的加价低于最低限制
            return new CSResponse(ErrorCode.LOWER_MIN_MAKEUP);
        }
        if (bidMakeup > biddingDetailDTO.getMaxMakeUp()) {
            //您的加价高于最高限制
            return new CSResponse(ErrorCode.GREATER_MAX_MAKEUP);
        }
        synchronized (orderNo) {
            takeBidding(bidStockVO, user.getUserId());
        }
        try{
            Boolean result = sendEmail(orderNo,2);
            if(!result){
                LOGGER.error("竞拍邮件发送失败，订单号:{}，用户ID:{}", orderNo, user.getUserId());
            }
        }catch (Exception e){
            LOGGER.error("竞拍邮件发送异常，订单号:{}，用户ID:{}",orderNo,user.getUserId());
        }
        return new CSResponse();
    }

    /**
     * 竞拍订单操作
     *
     * @param bidStockVO
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void takeBidding(BidStockVO bidStockVO, Long userId) {
        //更新订单表,最高出价、最高出价人
        stockService.updateOrderBidding(bidStockVO.getBiddingPrice(), userId, bidStockVO.getOrderNo());

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

    public SystemRules getSystemRules() {
        return stockService.getSystemRules();
    }

    public CSResponse beConfirmedList(PagedQueryVO queryVO) {
        int page = queryVO.getPageNo();
        int pageSize = queryVO.getPageSize();
        int startPage = (page - 1) * pageSize;
        List<OrderDTO> orderDTOList = stockService.beConfirmedList(startPage, pageSize);

        //总页数
        Long count = stockService.beConfirmedListCount();
        OrderListVO orderListVO = new OrderListVO();
        orderListVO.setOrderDTOList(orderDTOList);
        orderListVO.setTotalCount(count);
        return new CSResponse(orderListVO);
    }

    public CSResponse releaseAuditList(PagedQueryVO queryVO) {
        int page = queryVO.getPageNo();
        int pageSize = queryVO.getPageSize();
        int startPage = (page - 1) * pageSize;
        List<OrderDTO> orderDTOList = stockService.releaseAuditList(startPage, pageSize);

        //总页数
        Long count = stockService.releaseAuditListCount();
        OrderListVO orderListVO = new OrderListVO();
        orderListVO.setOrderDTOList(orderDTOList);
        orderListVO.setTotalCount(count);
        return new CSResponse(orderListVO);
    }

    public CSResponse backoutAuditList(PagedQueryVO queryVO) {
        int page = queryVO.getPageNo();
        int pageSize = queryVO.getPageSize();
        int startPage = (page - 1) * pageSize;
        List<OrderDTO> orderDTOList = stockService.backoutAuditList(startPage, pageSize);

        //总页数
        Long count = stockService.backoutAuditListCount();
        OrderListVO orderListVO = new OrderListVO();
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
        int startPage = page * pageSize;

        List<MyBiddingDTO> myBiddingDTOList = operationService.getMyBiddingRecord(startPage, pageSize, userId);
        Long count = operationService.getMyBiddingRecordCount(userId);

        List<MyBiddingVO> myBiddingVOList = new ArrayList<>();
        if (myBiddingDTOList != null && myBiddingDTOList.size() > 0) {
            for (MyBiddingDTO myBiddingDTO : myBiddingDTOList) {
                MyBiddingVO myBiddingVO = new MyBiddingVO(myBiddingDTO);
                if (myBiddingDTO.getOrderState() == 3) {
                    //撤销审核中状态前端显示交易中
                    myBiddingVO.setOrderState(4);
                } else {
                    myBiddingVO.setOrderState(myBiddingDTO.getOrderState());
                }
                if (myBiddingDTO.getBidderId()!=null && myBiddingDTO.getBidderId() != userId) {
                    //竞拍失败
                    myBiddingVO.setBiddingState(1);
                } else {
                    //最高出价
                    myBiddingVO.setBiddingState(2);
                }
                if(myBiddingDTO.getBuyerConfirm() == null){
                    //买家未确认
                    myBiddingVO.setBuyerConfirm(1);
                }else{
                    //买家已确认
                    myBiddingVO.setBuyerConfirm(2);
                }
                Date now = new Date();
                int spanTime = myBiddingDTO.getExpireTime().compareTo(now);
                if(myBiddingVO.getOrderState()==4 && spanTime<0){
                    //竞拍结束，等待结算
                    myBiddingVO.setOrderState(9);
                }
                if(myBiddingVO.getOrderState()==6 || myBiddingVO.getOrderState()==7){
                    myBiddingVO.setSellerName(myBiddingDTO.getSellerName());
                    myBiddingVO.setSellerMobile(myBiddingDTO.getSellerMobile());
                }else{
                    myBiddingVO.setSellerName("--");
                    myBiddingVO.setSellerMobile("--");
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
        if (user.getPermissionId() != 1) {
            return new CSResponse(ErrorCode.PC_PERMISSION_ERROR);
        }
        stockService.insertNewConfig(updateConfigVO);
        return new CSResponse();
    }

    public CSResponse releaseAudit(OrderCheckDTO orderCheckDTO) {
        //审核新增记录
        stockService.releaseAudit(orderCheckDTO);

        BiddingDetailDTO biddingDetailDTO = stockService.getOrderDetail(orderCheckDTO.getOrderNo());
        //修改订单状态
        if (0 == orderCheckDTO.getCheckingResult()) {
            stockService.updateOrderState(orderCheckDTO.getOrderNo(), 4);


            //添加订单上架时间和失效时间
            SystemRules systemRules =  stockService.getSystemRulesById(biddingDetailDTO.getSysRuleId());

            stockService.updateOrderSaleTime(orderCheckDTO.getOrderNo(),systemRules.getBiddingPeriod());
        } else {
            stockService.updateOrderState(orderCheckDTO.getOrderNo(), 2);

            //发布审核不通过。还原冻结资金
            stockService.restoreFrozenStocks(biddingDetailDTO.getSellerId(), biddingDetailDTO.getStockAmt());
        }

        return new CSResponse();
    }

    public CSResponse backoutAudit(OrderCheckDTO orderCheckDTO) {
        //审核新增记录
        stockService.releaseAudit(orderCheckDTO);

        //修改订单状态
        if (0 == orderCheckDTO.getCheckingResult()) {
            BiddingDetailDTO biddingDetailDTO = stockService.getOrderDetail(orderCheckDTO.getOrderNo());
            Date now = new Date();
            int spanTime = biddingDetailDTO.getExpireTime().compareTo(now);
            if (spanTime < 0) {
                //该订单已结束
                return new CSResponse(ErrorCode.ORDER_IS_OVER);
            }

            stockService.updateOrderState(orderCheckDTO.getOrderNo(), 5);

            //撤销审核通过。还原冻结资金
            stockService.restoreFrozenStocks(biddingDetailDTO.getSellerId(), biddingDetailDTO.getStockAmt());
        } else {

            BiddingDetailDTO biddingDetailDTO = stockService.getOrderDetail(orderCheckDTO.getOrderNo());
            Date now = new Date();
            int spanTime = biddingDetailDTO.getExpireTime().compareTo(now);
            if (spanTime < 0) {
                //该订单已结束
                return new CSResponse(ErrorCode.ORDER_IS_OVER);
            }

            stockService.updateOrderState(orderCheckDTO.getOrderNo(), 4);
        }

        return new CSResponse();
    }

    @Override
    public CSResponse confirmOrder(ConfirmVO confirmVO, Long userId) {
        String orderNo = confirmVO.getOrderNo();
        //确认者（1：买家，2：卖家，3：管理员）
        Integer confirmUser = confirmVO.getConfirmUser();
        BiddingDetailDTO biddingDetailDTO = stockService.getOrderDetail(orderNo);
        if (biddingDetailDTO == null) {
            //没有找到竞拍订单
            return new CSResponse(ErrorCode.NOT_FIND_BIDDING);
        }
        if (biddingDetailDTO.getOrderStatus() != 6) {
            //订单状态无效
            return new CSResponse(ErrorCode.INVALID_ORDER);
        }
        if (confirmUser == 1) {
            //买家确认
            if (biddingDetailDTO.getMaxBidder() != userId) {
                //非本人订单
                return new CSResponse(ErrorCode.INVALID_USER);
            }
            if (biddingDetailDTO.getBuyerConfirm() != null && biddingDetailDTO.getBuyerConfirm() == 1) {
                //该订单已被确认
                return new CSResponse(ErrorCode.HAS_CONFIRM_ORDER);
            }
            if (biddingDetailDTO.getSellerConfirm() != null && biddingDetailDTO.getSellerConfirm() == 1) {
                //双方已确认，订单结算
                stockService.confirmOrder(7, null, 1, orderNo);
                userService.balanceOrder(biddingDetailDTO.getStockAmt(), biddingDetailDTO.getSellerId());

            } else {
                stockService.confirmOrder(null, null, 1, orderNo);
            }
        }
        if (confirmUser == 2) {
            //卖家确认
            if (biddingDetailDTO.getSellerId() != userId) {
                //非本人订单
                return new CSResponse(ErrorCode.INVALID_USER);
            }
            if (biddingDetailDTO.getSellerConfirm() != null && biddingDetailDTO.getSellerConfirm() == 1) {
                //该订单已被确认
                return new CSResponse(ErrorCode.HAS_CONFIRM_ORDER);
            }
            if (biddingDetailDTO.getBuyerConfirm() != null && biddingDetailDTO.getBuyerConfirm() == 1) {
                //双方已确认，订单结算
                stockService.confirmOrder(7, 1, null, orderNo);
                userService.balanceOrder(biddingDetailDTO.getStockAmt(), biddingDetailDTO.getSellerId());
            } else {
                stockService.confirmOrder(null, 1, null, orderNo);
            }
        }
        if (confirmUser == 3) {
            stockService.updateOrderState(orderNo, 7);
            //结算订单
            userService.balanceOrder(biddingDetailDTO.getStockAmt(), biddingDetailDTO.getSellerId());
        }
        return new CSResponse();
    }

    @Override
    public void updateOrderState() {
        //给已经完成订单状态的买卖方发邮件
        List<String> orders = stockService.getCompleteOrder();
        for(String orderNo:orders){
            sendEmail(orderNo,3);
        }
        //更新已结束的订单状态
        stockService.updateStatus();
        //结算流拍订单
        List<OrderDTO> orderList = stockService.getPassOrder();
        for (OrderDTO orderDTO : orderList) {
            stockService.restoreFrozenStocks(orderDTO.getSellerId(), orderDTO.getStockAmt());
        }
        //更新流拍的订单状态
        stockService.updateStatus2Pass();
    }

    /**
     * 发送邮件
     *
     * @param OrderNo 订单号
     * @param type    1发布  2竞价更新 3 竞拍成功
     */
    public boolean sendEmail(String OrderNo, int type) {
        if (StringUtils.isBlank(OrderNo)){
            return false;
        }
        //根据orderNo拿到order实体
        BiddingDetailDTO orderDetail = stockService.getOrderDetail(OrderNo);
        ArrayList<String> mails;
        String mailAdds = "";
        UserDTO buyer = null;
        UserDTO seller = null;
        try {
            SendMail sm = new SendMail();
            String subject = "";
            switch (type){
                case 1:
                    subject = "新增标的通知，订单号：" + orderDetail.getOrderNo();
                    //拿到所有用户的邮箱
                    mails = userService.getMailList();
                    mailAdds = StringUtils.join(mails.toArray(), ",");
                case 2:
                    subject = "标的竞价更新通知，订单号：" + orderDetail.getOrderNo();
                    //拿到所有用户的邮箱
                    mails = userService.getMailList();
                    mailAdds = StringUtils.join(mails.toArray(), ",");
                case 3:
                    subject = "标的竞拍成功通知，订单号：" + orderDetail.getOrderNo();
                    buyer = userService.getUserById(orderDetail.getMaxBidder());
                    seller = userService.getUserById(orderDetail.getSellerId());
                    mailAdds = buyer.getMailAddress()+","+seller.getMailAddress();
            }

            sm.setAddress(Constants.FROM, mailAdds, subject);
            StringBuffer txt = new StringBuffer();
            txt.append("您好：");
            txt.append("<br/>");
            switch (type){
                case 1:
                    txt.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;新增标的，订单号：" + orderDetail.getOrderNo()
                            + ",起拍价:" + orderDetail.getInitialPrice()
                            + ",请注意查看。");

                case 2:
                    txt.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;标的竞价更新，订单号：" + orderDetail.getOrderNo()
                            + ",起拍价:" + orderDetail.getInitialPrice()
                            + ",当前最高出价:" + orderDetail.getMaxBiddingPrice()
                            + ",请注意查看。");

                case 3:
                    txt.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;标的竞拍成功，<br/>订单号：" + orderDetail.getOrderNo()
                            + ",<br/>最终每股单价:" + orderDetail.getMaxBiddingPrice()
                            + ",<br/>股权数:" + orderDetail.getStockAmt()
                            +", <br/>共计:" + orderDetail.getMaxBiddingPrice()*orderDetail.getStockAmt()
                            +"元,<br/>卖家信息:" + seller.getUserName()+","+seller.getUserMobile()+","+seller.getMailAddress()
                            +", <br/>买家信息:" + buyer.getUserName()+","+buyer.getUserMobile()+","+buyer.getMailAddress()
                            + ",<br/>请收到邮件后尽快联系对方完成线下交易，交易完成后及时在系统确认订单，完成股权变更。");


            }
            txt.append("<br/>");
            txt.append("<br/>");
            txt.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;米么股权系统");
            sm.send(Constants.HOST, Constants.USER, Constants.PWD, txt.toString());
            return true;
        } catch (Exception e) {
            LOGGER.error("发送失败：" + e.toString());
            return false;
        }
    }


}
