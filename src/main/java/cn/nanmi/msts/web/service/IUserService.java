package cn.nanmi.msts.web.service;


import cn.nanmi.msts.web.model.UserDTO;
import cn.nanmi.msts.web.web.vo.in.AddUserVO;
import cn.nanmi.msts.web.web.vo.in.GetUserPageListVO;
import cn.nanmi.msts.web.web.vo.out.UserModelVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能简述:<br>
 * user
 *
 * @author zhanglei
 * @version [V1.0, 2016年11月10日]
 */
public interface IUserService {
    /**
     * 用户登录
     *
     * @param mail
     * @return
     */
    UserDTO getUserByMail(String mail);

    /**
     * 根据userId拿到user
     *
     * @param userId
     * @return
     */
    UserDTO getUserById(long userId);

    /**
     * 修改密码
     *
     * @param userID
     * @param newPassword
     */
    void modifyPassword(Long userID, String newPassword);



    public ArrayList<UserModelVO> getEmployeeList(GetUserPageListVO getVO);

    public int addUser(AddUserVO addUserVO) ;

    ArrayList<String> getMailList();

    public Long getEmployeeTotalNum();

    public void deleteEmployee(Long userId, Long curUserId);

    public void resetUserPassword(Long userId);

    public Long getEmployeeNumByMobile(String mobile);

    public Long getEmployeeNumByEmail(String email);

    public Long getEmployeeNum(GetUserPageListVO getVO);

    void balanceOrder(Double stockAmt,Long userId);


}
