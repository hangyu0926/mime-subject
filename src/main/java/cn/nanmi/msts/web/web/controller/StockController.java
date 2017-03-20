package cn.nanmi.msts.web.web.controller;

import cn.nanmi.msts.web.business.IStockBusiness;
import cn.nanmi.msts.web.core.ConstantHelper;
import cn.nanmi.msts.web.enums.ErrorCode;
import cn.nanmi.msts.web.model.OrderCheckDTO;
import cn.nanmi.msts.web.model.OrderDTO;
import cn.nanmi.msts.web.model.SystemRules;
import cn.nanmi.msts.web.model.UserDTO;
import cn.nanmi.msts.web.response.CSPageResponse;
import cn.nanmi.msts.web.response.CSResponse;
import cn.nanmi.msts.web.utils.MathUtil;
import cn.nanmi.msts.web.web.vo.in.BidStockVO;
import cn.nanmi.msts.web.web.vo.in.ConfirmVO;
import cn.nanmi.msts.web.web.vo.in.PagedQueryVO;
import cn.nanmi.msts.web.web.vo.in.UpdateConfigVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.UUID;

/**
 * 股权相关Controller
 * User: jiangbin
 * Date: 2017/1/17
 * Time: 14:38
 */
@Controller
@RequestMapping(value = "/stocks", produces = {"application/json;charset=UTF-8"})
public class StockController {

    @Resource
    private IStockBusiness stockBusiness;

    /**
     * 查询竞拍列表（分页)
     *
     * @param request
     * @param queryVO
     * @return
     */
    @RequestMapping(value = "/biddingList", method = RequestMethod.POST)
    @ResponseBody
    public CSResponse getBiddingList(HttpServletRequest request, @RequestBody PagedQueryVO queryVO) {
        if (queryVO == null) {
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }
        if (queryVO.getPageNo() < 0 || queryVO.getPageSize() < 0) {
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute(ConstantHelper.USER_SESSION);
//        UserDTO user = new UserDTO();
//        user.setUserId(2L);
        if (user == null) {
            return new CSResponse(ErrorCode.SESSION_ERROR);
        }
        return stockBusiness.getBiddingList(queryVO, user.getUserId());
    }

    /**
     * 我的竞拍列表（分页)
     *
     * @param request
     * @param queryVO
     * @return
     */
    @RequestMapping(value = "/myBidding", method = RequestMethod.POST)
    @ResponseBody
    public CSResponse getMyBidding(HttpServletRequest request, @RequestBody PagedQueryVO queryVO) {
        if (queryVO == null) {
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }
        if (queryVO.getPageNo() < 0 || queryVO.getPageSize() < 0) {
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute(ConstantHelper.USER_SESSION);
//        UserDTO user = new UserDTO();
//        user.setUserId(2L);
        if (user == null) {
            return new CSResponse(ErrorCode.SESSION_ERROR);
        }
        return stockBusiness.getMyBiddingRecord(queryVO, user.getUserId());
    }

    /**
     * 准备竞拍
     *
     * @param request
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "/preBidding", method = RequestMethod.GET)
    @ResponseBody
    public CSResponse preBidding(HttpServletRequest request, String orderNo) {
        if (StringUtils.isBlank(orderNo)) {
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }

        return stockBusiness.getPreBidding(orderNo);
    }


    /**
     * 竞拍标的
     *
     * @param request
     * @param bidStockVO
     * @return
     */
    @RequestMapping(value = "/bidding", method = RequestMethod.POST)
    @ResponseBody
    public CSResponse bidStock(HttpServletRequest request, @RequestBody BidStockVO bidStockVO) {
        if (bidStockVO == null) {
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }
        if (StringUtils.isBlank(bidStockVO.getOrderNo()) || bidStockVO.getBiddingPrice() <= 0) {
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute(ConstantHelper.USER_SESSION);
//        UserDTO user = new UserDTO();
//        user.setUserId(2L);
        if (user == null) {
            return new CSResponse(ErrorCode.SESSION_ERROR);
        }
        return stockBusiness.bidStock(bidStockVO, user);
    }

    /**
     * 确认订单
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "confirmOrder")
    @ResponseBody
    public CSResponse confirmOrder(HttpServletRequest request,@RequestBody ConfirmVO confirmVO){
        if(confirmVO == null){
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }
        if(StringUtils.isBlank(confirmVO.getOrderNo())){
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute(ConstantHelper.USER_SESSION);
        if(user == null){
            return new CSResponse(ErrorCode.SESSION_ERROR);
        }
        if(confirmVO.getConfirmUser() == 3 && user.getPermissionId() !=1){
            return new CSResponse(ErrorCode.PC_PERMISSION_ERROR);
        }
        return stockBusiness.confirmOrder(confirmVO,user.getUserId());
    }

    /**
     * 发布订单
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "releaseOrder")
    @ResponseBody
    public CSResponse releaseOrder(HttpServletRequest request, @RequestBody Map map) {
      /*  HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute(ConstantHelper.USER_SESSION);
        if(user == null){
            return new CSResponse(ErrorCode.SESSION_ERROR);
        }*/

        if (map == null) {
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }
        if (null == map.get("stockAmt") || null == map.get("initialPrice")
                || "".equals(map.get("stockAmt").toString())  || "".equals(map.get("initialPrice").toString())) {
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }

       /* Double bidMakeup =  MathUtil.sub(Double.valueOf(map.get("stockAmt").toString()),Double.valueOf(user.getAvailableStock()));
        if(bidMakeup > 0 ){
            //您的发布股权数大于可售股权数
            return new CSResponse(ErrorCode.YOUR_RELEASE_BEYOND);
        }*/

        SystemRules systemRules = stockBusiness.getSystemRules();

        Double bidMakeup1 =  MathUtil.sub(Double.valueOf(map.get("stockAmt").toString()),systemRules.getMinStockPrice());
        if(bidMakeup1 < 0 ){
            //您的发布股权数小于起拍单价
            return new CSResponse(ErrorCode.YOUR_RELEASE_LOWER);
        }

        Double bidMakeup2 =  MathUtil.sub(Double.valueOf(map.get("stockAmt").toString()),systemRules.getMaxStockPrice());
        if(bidMakeup2 > 0 ){
            //您的发布股权数大于最大单价
            return new CSResponse(ErrorCode.YOUR_RELEASE_HIGHER);
        }

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderNo(UUID.randomUUID().toString().replace("-", ""));
        orderDTO.setStockAmt(Double.valueOf(map.get("stockAmt").toString()));
        orderDTO.setInitialPrice(Double.valueOf(map.get("initialPrice").toString()));
        //orderDTO.setSellerId(user.getUserId());
        orderDTO.setSellerId(1L);


        orderDTO.setSystemRuleId(systemRules.getRuleId());
        orderDTO.setBiddingPeriod(systemRules.getBiddingPeriod());
        stockBusiness.releaseOrder(orderDTO);

        return null;
    }

    /**
     * 我的发布（分页)
     *
     * @param request
     * @param queryVO
     * @return
     */
    @RequestMapping(value = "getMyOrder")
    @ResponseBody
    public CSResponse getMyOrder(HttpServletRequest request, @RequestBody PagedQueryVO queryVO) {
        if (queryVO == null) {
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }
        if (queryVO.getPageNo() < 0 || queryVO.getPageSize() < 0) {
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }

     /*   HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute(ConstantHelper.USER_SESSION);

        if(user == null){
            return new CSResponse(ErrorCode.SESSION_ERROR);
        }*/
        return stockBusiness.getMyOrder(queryVO, 1L);
    }

    /**
     * 撤销订单
     *
     * @param request
     */
    @RequestMapping(value = "backoutOrder")
    @ResponseBody
    public CSResponse backoutOrder(HttpServletRequest request, @RequestBody Map map) {
        if (map == null) {
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }
        if (null == map.get("orderNo")) {
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }

        stockBusiness.backoutOrder(map.get("orderNo").toString());

        return null;
    }

    /**
     * 待确认订单（分页)
     *
     * @param request
     * @param queryVO
     * @return
     */
    @RequestMapping(value = "beConfirmedList")
    @ResponseBody
    public CSResponse beConfirmedList(HttpServletRequest request, @RequestBody PagedQueryVO queryVO) {
        if (queryVO == null) {
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }
        if (queryVO.getPageNo() < 0 || queryVO.getPageSize() < 0) {
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }

        return stockBusiness.beConfirmedList(queryVO);
    }

    /**
     * 修改配置
     *
     * @param request
     * @param updateConfigVO
     * @return
     */
    @RequestMapping(value = "updateConfig")
    @ResponseBody
    public CSResponse updateConfig(HttpServletRequest request, @RequestBody UpdateConfigVO updateConfigVO) {
        if (updateConfigVO == null) {
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }
        if (updateConfigVO.getBackTime() > updateConfigVO.getStockTime()||updateConfigVO.getStartAmt()>updateConfigVO.getMaxAmt()||updateConfigVO.getMinAdd()>updateConfigVO.getMaxAdd()) {
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }
        return stockBusiness.updateConfig(request,updateConfigVO);
    }

    /**
     * 待审核订单-发布审核（分页）
     *
     * @param request
     * @param queryVO
     * @return
     */
    @RequestMapping(value = "releaseAuditList")
    @ResponseBody
    public CSResponse releaseAuditList(HttpServletRequest request, @RequestBody PagedQueryVO queryVO) {
        if (queryVO == null) {
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }
        if (queryVO.getPageNo() < 0 || queryVO.getPageSize() < 0) {
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }

        return stockBusiness.releaseAuditList(queryVO);
    }

    /**
     * 待审核订单-撤销审核（分页）
     *
     * @param request
     * @param queryVO
     * @return
     */
    @RequestMapping(value = "backoutAuditList")
    @ResponseBody
    public CSResponse backoutAuditList(HttpServletRequest request, @RequestBody PagedQueryVO queryVO) {
        if (queryVO == null) {
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }
        if (queryVO.getPageNo() < 0 || queryVO.getPageSize() < 0) {
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }

        return stockBusiness.backoutAuditList(queryVO);
    }

    /**
     * 发布审核通过/不通过
     *
     * @param request
     */
    @RequestMapping(value = "releaseAudit")
    @ResponseBody
    public CSResponse releaseAudit(HttpServletRequest request, @RequestBody Map map) {
        /*   HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute(ConstantHelper.USER_SESSION);
        if(user == null){
            return new CSResponse(ErrorCode.SESSION_ERROR);
        }*/

        if (map == null) {
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }
        if (null == map.get("orderNo") || null == map.get("checkingResult")) {
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }

        OrderCheckDTO orderCheckDTO = new OrderCheckDTO();
        /*orderCheckDTO.setAuditor(user.getUserName());*/
        orderCheckDTO.setAuditor(2);
        orderCheckDTO.setTransId(UUID.randomUUID().toString().replace("-", ""));
        orderCheckDTO.setOrderNo(map.get("orderNo").toString());
        orderCheckDTO.setCheckingType(1);
        orderCheckDTO.setCheckingView(null == map.get("checkingView") ? "" : map.get("checkingView").toString());
        orderCheckDTO.setCheckingResult(Integer.valueOf(map.get("checkingResult").toString()));

        stockBusiness.releaseAudit(orderCheckDTO);

        return new CSResponse();
    }

    /**
     * 撤销审核通过/不通过
     *
     * @param request
     */
    @RequestMapping(value = "backoutAudit")
    @ResponseBody
    public CSResponse backoutAudit(HttpServletRequest request, @RequestBody Map map) {
        /*   HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute(ConstantHelper.USER_SESSION);
        if(user == null){
            return new CSResponse(ErrorCode.SESSION_ERROR);
        }*/

        if (map == null) {
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }
        if (null == map.get("orderNo") || null == map.get("checkingResult")) {
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }

        OrderCheckDTO orderCheckDTO = new OrderCheckDTO();
        /*orderCheckDTO.setAuditor(user.getUserName());*/
        orderCheckDTO.setAuditor(2);
        orderCheckDTO.setTransId(UUID.randomUUID().toString().replace("-", ""));
        orderCheckDTO.setOrderNo(map.get("orderNo").toString());
        orderCheckDTO.setCheckingType(2);
        orderCheckDTO.setCheckingView(null == map.get("checkingView") ? "" : map.get("checkingView").toString());
        orderCheckDTO.setCheckingResult(Integer.valueOf(map.get("checkingResult").toString()));

        stockBusiness.backoutAudit(orderCheckDTO);

        return new CSResponse();
    }

    /**
     * 跳转我的发布页
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "jumpReleaseOrder")
    @ResponseBody
    public CSResponse jumpReleaseOrder(HttpServletRequest request) {
       /* HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute(ConstantHelper.USER_SESSION);

        if(user == null){
            return new CSResponse(ErrorCode.SESSION_ERROR);
        }*/

        return stockBusiness.jumpReleaseOrder(1L);
    }
}
