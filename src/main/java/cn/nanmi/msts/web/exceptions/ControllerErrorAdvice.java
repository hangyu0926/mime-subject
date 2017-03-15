package cn.nanmi.msts.web.exceptions;

import cn.nanmi.msts.web.enums.ErrorCode;
import cn.nanmi.msts.web.response.CSResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;


/**
 * controller层异常处理<br>
 * 处理controller层的异常信息
 *
 * @author Vic Ding
 * @version [版本号, 2016年1月8日]
 */
@ControllerAdvice
public class ControllerErrorAdvice {

    /**
     * 日志记录器
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(ControllerErrorAdvice.class);

    /**
     * 
     * 功能描述: <br>
     * 系统异常处理
     *
     * @author Vic Ding
     * @version [版本号, 2016年1月8日]
     * @param e 异常
     * @param nativeWebRequest 用户请求信息
     * @return 处理结果
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public CSResponse handleIOException(Exception e, NativeWebRequest nativeWebRequest) {
        LOGGER.error("------ 系统异常 ------", e);
        return new CSResponse(ErrorCode.UNKNOWN_WRROR);
    }

}
