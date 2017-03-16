package cn.nanmi.msts.web.web.Converter;


import cn.nanmi.msts.web.model.UserDTO;
import cn.nanmi.msts.web.web.vo.out.UserModelVO;
import org.apache.commons.lang.StringUtils;

/**
 * Created by zhanglei on 2016/11/16.
 */
public class UserModelConverter {
    public static UserModelVO initUserModelIVOValues(UserDTO userDto) {
        UserModelVO userModelVO = new UserModelVO();
        userModelVO.setUserId(userDto.getUserId());
        userModelVO.setUserMobile(sortBlank(userDto.getUserMobile()));
        userModelVO.setUserMailAdd(sortBlank(userDto.getMailAddress()));
        userModelVO.setTotalStock(userDto.getTotalStock());
        userModelVO.setAvailableStock(userDto.getAvailableStock());
        userModelVO.setPermissionId(userDto.getPermissionId());
        userModelVO.setUserName(sortBlank(userDto.getUserName()));
        return userModelVO;
    }

    private static String sortBlank(String s){
        if(StringUtils.isBlank(s)){
            return "";
        }else {
            return s;
        }
    }
}
