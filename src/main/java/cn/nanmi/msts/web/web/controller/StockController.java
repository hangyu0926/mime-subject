package cn.nanmi.msts.web.web.controller;

import cn.nanmi.msts.web.business.IStockBusiness;
import cn.nanmi.msts.web.core.ConstantHelper;
import cn.nanmi.msts.web.enums.ErrorCode;
import cn.nanmi.msts.web.model.OrderDTO;
import cn.nanmi.msts.web.model.SystemRules;
import cn.nanmi.msts.web.model.UserDTO;
import cn.nanmi.msts.web.response.CSPageResponse;
import cn.nanmi.msts.web.response.CSResponse;
import cn.nanmi.msts.web.web.vo.in.BidStockVO;
import cn.nanmi.msts.web.web.vo.in.BiddingListQueryVO;
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
@RequestMapping(value = "/stocks",  produces = { "application/json;charset=UTF-8" })
public class StockController {

    @Resource
    private IStockBusiness stockBusiness;

    /**
     *  查询竞拍列表（分页)
     * @param request
     * @param queryVO
     * @return
     */
    @RequestMapping(value = "/biddingList",method = RequestMethod.POST)
    @ResponseBody
    public CSResponse getBiddingList(HttpServletRequest request,@RequestBody BiddingListQueryVO queryVO){
        if(queryVO == null ){
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }
        if(queryVO.getPageNo()<0 || queryVO.getPageSize()<0){
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute(ConstantHelper.USER_SESSION);
        if(user == null){
            return new CSResponse(ErrorCode.SESSION_ERROR);
        }
        return stockBusiness.getBiddingList(queryVO,user);
    }

    /**
     *  我的竞拍列表（分页)
     * @param request
     * @param queryVO
     * @return
     */
    @RequestMapping(value = "/myBidding",method = RequestMethod.POST)
    @ResponseBody
    public CSResponse getMyBidding(HttpServletRequest request,@RequestBody BiddingListQueryVO queryVO){
        if(queryVO == null ){
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }
        if(queryVO.getPageNo()<0 || queryVO.getPageSize()<0){
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }
        return null;
    }

    /**
     * 准备竞拍
     * @param request
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "/preBidding",method = RequestMethod.GET)
    @ResponseBody
    public CSResponse preBidding(HttpServletRequest request,String orderNo){
        if(StringUtils.isBlank(orderNo)){
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }

        return stockBusiness.getPreBidding(orderNo);
    }


    /**
     *  竞拍标的
     * @param request
     * @param bidStockVO
     * @return
     */
    @RequestMapping(value = "/bidding",method = RequestMethod.POST)
    @ResponseBody
    public CSResponse bidStock(HttpServletRequest request,@RequestBody BidStockVO bidStockVO){
        if(bidStockVO == null ){
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }
        if(StringUtils.isBlank(bidStockVO.getOrderNo()) || bidStockVO.getBiddingPrice()<=0){
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }



        return null;
    }

    /**
     *  确认订单
     * @param request
     * @param queryVO
     * @return
     */
    @RequestMapping(value = "confirmOrder")
    @ResponseBody
    public CSResponse confirmOrder(HttpServletRequest request,@RequestBody BiddingListQueryVO queryVO){
        if(queryVO == null ){
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }
        if(queryVO.getPageNo()<0 || queryVO.getPageSize()<0){
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }
        return null;
    }

    /**
     *  发布订单
     * @param request
     * @param orderDTO
     * @return
     */
    @RequestMapping(value = "releaseOrder")
    @ResponseBody
    public void releaseOrder(HttpServletRequest request, OrderDTO orderDTO){
      /*  HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute(ConstantHelper.USER_SESSION);
        if(user == null){
            return new CSResponse(ErrorCode.SESSION_ERROR);
        }*/

        orderDTO = new OrderDTO();
        orderDTO.setOrderNo(UUID.randomUUID().toString().replace("-", ""));
        orderDTO.setStockAmt((double) 50);
        orderDTO.setInitialPrice((double) 100);
        orderDTO.setSellerId((long) 1);

        SystemRules systemRules = stockBusiness.getSystemRules();
        orderDTO.setSystemRuleId(systemRules.getRuleId());
        orderDTO.setBiddingPeriod(systemRules.getBiddingPeriod());
         stockBusiness.releaseOrder(orderDTO);
    }

    /**
     *  我的发布（分页)
     * @param request
     * @param queryVO
     * @return
     */
    @RequestMapping(value = "getMyOrder")
    @ResponseBody
    public CSResponse getMyOrder(HttpServletRequest request,@RequestBody BiddingListQueryVO queryVO){
        if(queryVO == null ){
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }
        if(queryVO.getPageNo()<0 || queryVO.getPageSize()<0){
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }
      /*  HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute(ConstantHelper.USER_SESSION);
        if(user == null){
            return new CSResponse(ErrorCode.SESSION_ERROR);
        }*/

        Long userId = 1L;
                //Long.valueOf(request.getParameter("userId").toString());
        return stockBusiness.getMyOrder(queryVO,userId);
    }

    /**
     * 撤销订单
     * @param request
     */
    @RequestMapping(value = "backoutOrder")
    @ResponseBody
    public void backoutOrder(HttpServletRequest request,@RequestBody Map map){
        stockBusiness.backoutOrder(map.get("orderNo").toString());
    }
}
