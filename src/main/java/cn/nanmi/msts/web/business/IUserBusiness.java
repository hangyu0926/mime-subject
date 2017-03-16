package cn.nanmi.msts.web.business;



import cn.nanmi.msts.web.response.CSResponse;
import cn.nanmi.msts.web.web.vo.in.AddUserVO;
import cn.nanmi.msts.web.web.vo.in.GetUserListVO;
import cn.nanmi.msts.web.web.vo.in.LoginVO;
import cn.nanmi.msts.web.web.vo.in.ModifyPasswordVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * test<br>
 * test
 *
 * @author
 * @version [V1.0, 2016年2月1日]
 */
public interface IUserBusiness {
    /**
     *   用户登录
     * @param loginVO
     * @return
     */
    public CSResponse login(HttpServletRequest httpServletRequest, LoginVO loginVO);

    /**
     *     修改密码
     * @param httpServletRequest
     * @param modifyPasswordVO
     */
    public  CSResponse modifyPassword(HttpServletRequest httpServletRequest, ModifyPasswordVO modifyPasswordVO);

    /**
     *   用户登出
     */
    public CSResponse logout(HttpServletRequest httpServletRequest);

    public CSResponse addUser(HttpServletRequest request, AddUserVO addVO);

    public CSResponse getUserList(GetUserListVO getVO);


    public CSResponse deleteUser(HttpServletRequest request, Long userId);


    public CSResponse resetUserPassword(HttpServletRequest request, Long userId);
}
