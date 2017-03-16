package cn.nanmi.msts.web.web.controller;


import cn.nanmi.msts.web.business.IUserBusiness;
import cn.nanmi.msts.web.enums.ErrorCode;
import cn.nanmi.msts.web.response.CSResponse;
import cn.nanmi.msts.web.web.vo.in.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户登录注销相关接口
 * Created by zhanglei on 2016/11/16.
 */
@Controller
@RequestMapping(value = "/user", produces = {"application/json;charset=UTF-8"})
public class UserController {
    @Autowired
    private IUserBusiness userBusiness;

    /**
     * 用户登录接口
     *
     * @param request
     * @param loginBean 用户登录数据，包括：邮箱、密码
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CSResponse login(HttpServletRequest request, @RequestBody LoginVO loginBean) {
        if(null == loginBean){
            return  new CSResponse(ErrorCode.UNKNOWN_WRROR);
        }
        return userBusiness.login(request,loginBean);
    }

    /**
     * 用户修改密码接口
     *
     * @param request
     * @param modifyPasswordVO
     * @return
     */
    @RequestMapping(value = "/modifyPassword", method = RequestMethod.POST)
    @ResponseBody
    public CSResponse modifyPassword(HttpServletRequest request, @RequestBody ModifyPasswordVO modifyPasswordVO) {
        if(null == modifyPasswordVO){
            return  new CSResponse(ErrorCode.UNKNOWN_WRROR);
        }
       return userBusiness.modifyPassword(request,modifyPasswordVO);
    }
    /**
     * 用户注销接口
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public CSResponse logout(HttpServletRequest request) {
        return userBusiness.logout(request);
    }


    @RequestMapping(value = "addUser")
    @ResponseBody
    public CSResponse addUser(HttpServletRequest request, @RequestBody AddUserVO addVO) {
        //验证输入参数
        if (addVO == null) {
            return new CSResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }else {
            return userBusiness.addUser(request, addVO);
        }
    }

    @RequestMapping(value = "getUserList")
    @ResponseBody
    public CSResponse getUserList(@RequestBody GetUserListVO getVO) {
        //判断入参
        if (getVO.getPageNo() == 0
                || getVO.getPageSize() == 0) {
            return new CSResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }
        return userBusiness.getUserList(getVO);
    }


    @RequestMapping("deleteUser")
    @ResponseBody
    public CSResponse deleteUser(HttpServletRequest request, @RequestBody DeleteEmployeeVO deleteEmployeeVO){
        long userId = deleteEmployeeVO.getUserId();
        if (userId <= 0){
            return new CSResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }
        return userBusiness.deleteUser(request,userId);
    }

    @RequestMapping("resetUserPassword")
    @ResponseBody
    public CSResponse resetUserPassword(HttpServletRequest request, @RequestBody ResetPasswordVO resetPasswordVO){
        long userId = resetPasswordVO.getUserId();
        if (userId <= 0){
            return new CSResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }
        return userBusiness.resetUserPassword(request,userId);
    }

}
