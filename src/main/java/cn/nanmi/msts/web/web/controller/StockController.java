package cn.nanmi.msts.web.web.controller;

import cn.nanmi.msts.web.business.IStockBusiness;
import cn.nanmi.msts.web.core.ConstantHelper;
import cn.nanmi.msts.web.enums.ErrorCode;
import cn.nanmi.msts.web.model.BiddingDTO;
import cn.nanmi.msts.web.model.OrderDTO;
import cn.nanmi.msts.web.model.UserDTO;
import cn.nanmi.msts.web.response.CSPageResponse;
import cn.nanmi.msts.web.response.CSResponse;
import cn.nanmi.msts.web.web.vo.in.BiddingListQueryVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 股权相关Controller
 * User: jiangbin
 * Date: 2017/1/17
 * Time: 14:38
 */
@Controller
@RequestMapping(value = "/stocks", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
public class StockController {

    @Resource
    private IStockBusiness stockBusiness;

    /**
     *  查询竞拍列表（分页)
     * @param request
     * @param queryVO
     * @return
     */
    @RequestMapping(value = "/biddingList")
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
    @RequestMapping(value = "myBidding")
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
     *  竞拍标的
     * @param request
     * @param queryVO
     * @return
     */
    @RequestMapping(value = "bidding")
    @ResponseBody
    public CSResponse bidStock(HttpServletRequest request,@RequestBody BiddingListQueryVO queryVO){
        if(queryVO == null ){
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }
        if(queryVO.getPageNo()<0 || queryVO.getPageSize()<0){
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
    public CSResponse releaseOrder(HttpServletRequest request, OrderDTO orderDTO){
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute(ConstantHelper.USER_SESSION);
        if(user == null){
            return new CSResponse(ErrorCode.SESSION_ERROR);
        }

        return null;
    }

}
