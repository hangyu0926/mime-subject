package cn.nanmi.msts.web.dao;


import cn.nanmi.msts.web.model.UserDTO;
import cn.nanmi.msts.web.web.vo.in.AddUserVO;
import cn.nanmi.msts.web.web.vo.in.GetUserPageListVO;
import cn.nanmi.msts.web.web.vo.out.UserModelVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("userMapper")
public interface UserMapper {
    /**
     *  根据用户邮箱查找用户
     * @param mail
     * @return
     */
	 UserDTO getUserByMail(@Param("mail") String mail);
    /**
     *  根据id查找用户
     * @param userId
     * @return
     */
    UserDTO getUserById(@Param("userId") Long userId);

    /**
     *  根据userId修改密码
     * @param userid
     * @param newPassword
     */
    void modifyPwdByUserID(@Param("userID") Long userid, @Param("password") String newPassword);



    int addUser(AddUserVO user);

    ArrayList<UserModelVO> getEmployeeListByVO(GetUserPageListVO getVO);

    Long getEmployeeTotalNum();

    void deleteEmployee(@Param("userId") Long userId, @Param("curUserId") Long curUserId);

    void resetUserPassword(@Param("userId") Long userId);

    Long getEmployeeNumByMobile(@Param("mobile") String mobile);

    Long getEmployeeNumByEmail(@Param("email") String email);

    Long getEmployeeNum(GetUserPageListVO getVO);

    void frozenStocks(@Param("sellId")Long sellId,@Param("stockAmt")Double stockAmt);

    void restoreFrozenStocks(@Param("sellId")Long sellId,@Param("stockAmt")Double stockAmt);
    void balanceOrder(@Param("stockAmt")Double stockAmt,@Param("userId")Long userId);
    ArrayList<String> getMailList();

    UserDTO getUserById(@Param("userId")Long userId);
}
