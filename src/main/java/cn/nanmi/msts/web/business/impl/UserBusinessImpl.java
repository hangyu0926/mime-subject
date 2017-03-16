package cn.nanmi.msts.web.business.impl;


import cn.nanmi.msts.web.business.IUserBusiness;
import cn.nanmi.msts.web.core.ConstantHelper;
import cn.nanmi.msts.web.core.Constants;
import cn.nanmi.msts.web.enums.ErrorCode;
import cn.nanmi.msts.web.model.UserDTO;
import cn.nanmi.msts.web.response.CSPageResponse;
import cn.nanmi.msts.web.response.CSResponse;
import cn.nanmi.msts.web.service.IUserService;
import cn.nanmi.msts.web.utils.JsonUtil;
import cn.nanmi.msts.web.web.Converter.UserModelConverter;
import cn.nanmi.msts.web.web.vo.in.*;
import cn.nanmi.msts.web.web.vo.out.UserListOutVO;
import cn.nanmi.msts.web.web.vo.out.UserModelVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * test<br>
 * test
 *
 * @author Vic
 * @version [V1.0, 2016年2月1日]
 */
@Component("userBusiness")
public class UserBusinessImpl extends BaseBussinessImpl implements IUserBusiness {

    @Resource
    private IUserService userService;
    private String fileServerOutPath;

    @Override
    public CSResponse login(HttpServletRequest request, LoginVO loginVO) {
        LOGGER.info("用户登录：" + loginVO.toString());
        HttpSession session = request.getSession();
        String userMobile = loginVO.getUserMail();
        String password = loginVO.getPassword();
        boolean isFirst = false;
        if (StringUtils.isBlank(userMobile) || StringUtils.isBlank(password)) {
            //用户名或密码为空
            LOGGER.error("用户登录用户名或密码为空：" + loginVO.toString());
            return new CSResponse(ErrorCode.CONFIRM_PASSWORD_WRONG);
        }
        //SQL拿user实体
        UserDTO user = userService.getUserByMail(loginVO.getUserMail());
        if (null == user) {
            return new CSResponse(ErrorCode.USER_NOT_EXIST);
        } else {
                if(password.equals(Constants.INITIAL_PASSWORD)){
                    isFirst = true;
                }else {
                if (!password.equals(user.getLoginPass())) {
                    return new CSResponse(ErrorCode.CONFIRM_PASSWORD_WRONG);
                }
            }

            //保存到session
            session.setAttribute(ConstantHelper.USER_SESSION, user);
            //如果redis保存了用户的userid，sessionid的map
            //返回格式转换
            UserModelVO userModelVO = UserModelConverter.initUserModelIVOValues(user);
            CSResponse loginResponse;

            if(isFirst){
                 loginResponse = new CSResponse(ErrorCode.USER_IS_INIT);
            }else{
                 loginResponse = new CSResponse();
            }
            loginResponse.setDetailInfo(userModelVO);
            return loginResponse;
        }
    }

    @Override
    public CSResponse modifyPassword(HttpServletRequest request, ModifyPasswordVO modifyPasswordVO) {
        LOGGER.info(JsonUtil.formatLog("用户修改密码:" + modifyPasswordVO.toString()));
        String oldPassword = modifyPasswordVO.getOldPassword();
        String newPassword = modifyPasswordVO.getNewPassword();
        if ( StringUtils.isBlank(newPassword)) {
            //旧密码或新密码为空
            LOGGER.error(JsonUtil.formatLog("用户修改密码,新密码为空:" + modifyPasswordVO.toString()));
            return new CSResponse(ErrorCode.USERNAME_PASSWORD_NULL);
        }
        //SQL拿user实体
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute(ConstantHelper.USER_SESSION);
        if (null == user) {
            return new CSResponse(ErrorCode.CONFIRM_PASSWORD_WRONG);
        } else {
            if(StringUtils.isBlank(oldPassword)){
                if(!user.getLoginPass().equals(Constants.INITIAL_PASSWORD)){
                    LOGGER.error(JsonUtil.formatLog("用户修改密码,密码错误1:" + modifyPasswordVO.toString()));
                    return new CSResponse(ErrorCode.PASSWORD_WRONG);
                }
            }else{
                if(!oldPassword.equals(user.getLoginPass())){
                    LOGGER.error(JsonUtil.formatLog("用户修改密码,密码错误2:" + modifyPasswordVO.toString()));
                    return new CSResponse(ErrorCode.PASSWORD_WRONG);
                }
            }
            CSResponse modifyResponse = new CSResponse();
            //修改数据库中的密码
            userService.modifyPassword(user.getUserId(), newPassword);
            return modifyResponse;
        }
    }

    @Override
    public CSResponse logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute(ConstantHelper.USER_SESSION);
        if (null != user) {
            LOGGER.info("用户注销:==userId:{}==username:{}", user.getUserId(), user.getUserName());
            //删除map,会启用lisetener的destory方法
            session.invalidate();
        } else {
            LOGGER.info("logout session 超时");
        }
        return new CSResponse();
    }


    @Override
    public CSResponse addUser(HttpServletRequest request, AddUserVO addVO) {
        HttpSession session = request.getSession();
        UserDTO curUser = (UserDTO) session.getAttribute(ConstantHelper.USER_SESSION);
        LOGGER.info("session:" + curUser.toString());
        if (null == curUser) {
            LOGGER.info("employee session 超时");
            return new CSResponse(ErrorCode.SESSION_ERROR);
        }
        if(curUser.getPermissionId()!=1){
            return new CSResponse(ErrorCode.PC_PERMISSION_ERROR);
        }
            if(StringUtils.isBlank(addVO.getUserMailAdd())|| StringUtils.isBlank(addVO.getUserMobile())|| StringUtils.isBlank(addVO.getUserName())){
                return new CSResponse(ErrorCode.ADD_NOT_ALL_FAILED);
            }
            if(!Pattern.matches("^([\\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.com|\\.cn)$", addVO.getUserMailAdd())||addVO.getUserMailAdd().length()>50){
                return new CSResponse(ErrorCode.ADD_FORMAT_ERROR_FAILED);
            }
            if(addVO.getUserName().length()>22||addVO.getUserMobile().length()!=11){
                return new CSResponse(ErrorCode.ADD_FORMAT_ERROR_FAILED);
            }
            if (userService.getEmployeeNumByMobile(addVO.getUserMobile()) > 0) {
                return new CSResponse(ErrorCode.ADD_MOBILE_EXIST_FAILED);
            }
            if (userService.getEmployeeNumByEmail(addVO.getUserMailAdd()) > 0) {
                return new CSResponse(ErrorCode.ADD_MAIL_EXIST_FAILED);
            }

            userService.addUser(addVO);

        return new CSResponse();
    }

    @Override
    public CSResponse getUserList(GetUserListVO getVO) {
        LOGGER.info("请求参数：" + getVO);
        if(getVO == null ){
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }
        if(getVO.getPageNo()<0 || getVO.getPageSize()<0){
            return new CSPageResponse(ErrorCode.FAIL_INVALID_PARAMS);
        }

        //计算要查询的页数
        int page = getVO.getPageNo();
        int pageSize = getVO.getPageSize();
        page = page - 1;
        int startPage = page * pageSize ;
        GetUserPageListVO getUserPageListVO = new GetUserPageListVO(getVO);
        getUserPageListVO.setStartPage(startPage);
        Long num = userService.getEmployeeTotalNum();
        Long totalNum = userService.getEmployeeNum(getUserPageListVO);
        ArrayList<UserModelVO> list = userService.getEmployeeList(getUserPageListVO);
        UserListOutVO listOutVO = new UserListOutVO(list,totalNum,num);
        return new CSResponse(listOutVO);
    }


    @Override
    public CSResponse deleteUser(HttpServletRequest request, Long userId) {
        //添加删除人
        HttpSession session = request.getSession();
        UserDTO curUser = (UserDTO) session.getAttribute(ConstantHelper.USER_SESSION);
        LOGGER.info("deleteUser session curUser:" + curUser.toString());
        if (null == curUser) {
            LOGGER.info("del employee session 超时");
            return new CSResponse(ErrorCode.SESSION_ERROR);
        }
        if(curUser.getPermissionId()!=1){
            return new CSResponse(ErrorCode.PC_PERMISSION_ERROR);
        }
        if(curUser.getUserId()==userId){//不能删除自己
            return new CSResponse(ErrorCode.PC_PERMISSION_ERROR);
        }
        userService.deleteEmployee(userId, curUser.getUserId());

        return new CSResponse();
    }


    @Override
    public CSResponse resetUserPassword(HttpServletRequest request, Long userId) {
        HttpSession session = request.getSession();
        UserDTO curUser = (UserDTO) session.getAttribute(ConstantHelper.USER_SESSION);
        LOGGER.info("resetUserPassword session curUser:" + curUser.toString());
        if (null == curUser) {
            LOGGER.info("resetUserPassword employee session 超时");
            return new CSResponse(ErrorCode.SESSION_ERROR);
        }
        if(curUser.getPermissionId()!=1&&curUser.getPermissionId()!=4){
            return new CSResponse(ErrorCode.PC_PERMISSION_ERROR);
        }
        userService.resetUserPassword(userId);
        return new CSResponse();
    }



}
