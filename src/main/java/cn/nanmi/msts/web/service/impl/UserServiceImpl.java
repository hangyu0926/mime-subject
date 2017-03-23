package cn.nanmi.msts.web.service.impl;

import cn.nanmi.msts.web.dao.UserMapper;
import cn.nanmi.msts.web.model.UserDTO;
import cn.nanmi.msts.web.service.IUserService;
import cn.nanmi.msts.web.web.vo.in.AddUserVO;
import cn.nanmi.msts.web.web.vo.in.GetUserPageListVO;
import cn.nanmi.msts.web.web.vo.out.UserModelVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能简述:<br>
 * test
 *
 * @author zhanglei
 * @version [V1.0, 2016年11月10日]
 */
@Service(value ="userService")
@Transactional
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Transactional(propagation = Propagation.REQUIRED)
    public UserDTO getUserByMail(String mail) {
        UserDTO userDto = userMapper.getUserByMail(mail);
        return userDto;
    }

    @Override
    public UserDTO getUserById(long userId) {
        UserDTO userDto = userMapper.getUserById(userId);
        return userDto;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void modifyPassword(Long userID, String newPassword) {
        logger.info("用户修改密码,userID:{}", userID);
        userMapper.modifyPwdByUserID(userID, newPassword);
    }
    @Override
    public int addUser(AddUserVO addUserVO) {
        return userMapper.addUser(addUserVO);
    }

    @Override
    public ArrayList<UserModelVO> getEmployeeList(GetUserPageListVO getVO) {
        getVO.setPageNo((getVO.getPageNo() - 1) * getVO.getPageSize());
        return userMapper.getEmployeeListByVO(getVO);
    }

    @Override
    public ArrayList<String> getMailList() {
        return userMapper.getMailList();
    }

    @Override
    public Long getEmployeeTotalNum() {
        return userMapper.getEmployeeTotalNum();
    }

    @Override
    public void deleteEmployee(Long userId, Long curUserId) {
        userMapper.deleteEmployee(userId, curUserId);
    }

    @Override
    public void resetUserPassword(Long userId) {
        userMapper.resetUserPassword(userId);
    }


    @Override
    public Long getEmployeeNumByMobile(String mobile) {
        return userMapper.getEmployeeNumByMobile(mobile);
    }

    @Override
    public Long getEmployeeNumByEmail(String email) {
        return userMapper.getEmployeeNumByEmail(email);
    }

    @Override
    public Long getEmployeeNum(GetUserPageListVO getVO) {
        return userMapper.getEmployeeNum(getVO);
    }

    @Override
    public void balanceOrder(Double stockAmt, Long userId) {
        userMapper.balanceOrder(stockAmt,userId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UserDTO getUserById(Long userId) {
        UserDTO userDto = userMapper.getUserById(userId);
        return userDto;
    }
}
