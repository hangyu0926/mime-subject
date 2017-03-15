package cn.nanmi.msts.web.dao;


import cn.nanmi.msts.web.dao.entities.ModelEntity;
import cn.nanmi.msts.web.model.ModelDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userMapper")
public interface ModelMapper {
    /**
     *  根据用户邮箱查找用户
     * @param mobile
     * @return
     */
	 ModelDTO getUserByMobile(@Param("mobile") String mobile);


    /**
     *  根据userId修改密码
     * @param userid
     * @param newPassword
     */
    void modifyPwdByUserID(@Param("userID") Long userid, @Param("password") String newPassword);

    /**
     *  修改在线状态
     * @param userid
     * @param sessionId
     * @param status
     */
    void changeEventStatus(@Param("userID") Long userid, @Param("sessionId") String sessionId, @Param("status") int status);


    /**
     *  根据id获取名称
     * @param uid
     * @return
     */
    void addLoginHistory(@Param("userID") Long uid, @Param("status") int status, @Param("sessionId") String sessionId);
    /**
     *查看是否产生了记录
     */
    int getTodayReport(@Param("userID") Long uid);

    /**
     *  根据id获取名称
     *  1：在线；2：离开；3：就餐；4：会议
     * @param uid
     * @return
     */
    void addStatusHistory(@Param("userID") Long uid, @Param("sessionId") String sessionId, @Param("status") int status);

    /**
     *  根据mailAddress修改密码
     * @param mailAddress
     * @param newPassword
     */
    void modifyPwdByMailAddress(@Param("mailAddress") String mailAddress, @Param("password") String newPassword);

    /**
     *  根据userId查找用户
     * @param userId
     * @return
     */
    ModelEntity getUserInfoByUserId(@Param("userId") Long userId);

    /**
     * 重置默认密码123456
     * @param workId
     */
    void resetPwdByWorkId(@Param("workId") String workId, @Param("password") String newPassword);

    /**
     * 获取指定角色的所有用户
     * @param roleList
     * @return
     */
    List<ModelEntity> getAllUserInfo(@Param("roleList") List<String> roleList);


    /**
     * 获取指定角色的所有用户数
     * @param roleList
     * @return
     */
    Long getAllUserCount(@Param("roleList") List<String> roleList);

    /**
     * 分页获得用户信息
     * @param shardRows
     * @param startRows
     * @return
     */
    List<ModelEntity> getAllUserInfoByShard(@Param("shardRows") Long shardRows, @Param("startRows") Long startRows);

    /**
     * 获得用户信息
     * @return
     */
    List<ModelEntity> getAllUserId();

    /**
     * 获得主管下面所有组员userID
     * @param deptId
     * @return
     */
    List<String> getUserIdsWithSupervisor(@Param("deptId") String deptId);

    /**
     * 获得小组下面所有组员userID
     * @param deptId
     * @return
     */
    List<String> getUserIdsWithGroup(@Param("deptId") String deptId);
    
    
    List<ModelDTO> getUserByPermissionId(@Param("permissionId") Integer permissionId);


    /**
     *  绑定微信号
     * @param userid
     * @param wxUserId
     */
    void bindWXCount(@Param("userID") Long userid, @Param("wxUserId") String wxUserId);

    /**
     *  检查微信号重复
     * @param wxUserId
     */
    int checkWXId(@Param("wxUserId") String wxUserId);

    /**
     *  根据wx查找用户
     * @param wxUserId
     * @return
     */
    ModelDTO getUserByWXId(@Param("wxUserId") String wxUserId);

    List<String> getUsersByUserGroups(@Param("userGroups") List<Integer> userGroups);

}
