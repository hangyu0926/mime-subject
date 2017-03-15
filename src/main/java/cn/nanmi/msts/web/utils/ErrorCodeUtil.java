package cn.nanmi.msts.web.utils;

import java.io.Serializable;

import cn.nanmi.msts.web.core.ResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.nanmi.msts.web.enums.ErrorCode;

/**
 * 功能简述:<br> 
 * 错误码实体类
 *
 * @author Vic Ding
 * @version [V1.0, 2016年11月10日]
 */
public class ErrorCodeUtil {
    /**
     * 日志
     */
    protected final static Logger LOGGER = LoggerFactory.getLogger(ErrorCodeUtil.class);

    /**
     * 
     * 功能描述: <br>
     * 获取resultBean
     *
     * @author Vic Ding
     * @version [版本号, 2016年1月11日]
     * @param code 结果码
     * @return 处理结果
     */
    public static ResultBean getResultBean(ErrorCode code) {
        return getResultBean(code, null);
    }

    /**
     * 
     * 功能描述: <br>
     * 获取resultBean
     *
     * @author Vic Ding
     * @version [版本号, 2016年1月11日]
     * @param code 结果码
     * @param content 返回内容
     * @return 处理结果
     */
    public static ResultBean getResultBean(ErrorCode code, Serializable content) {
        return new ResultBean(code, content);
    }
}
