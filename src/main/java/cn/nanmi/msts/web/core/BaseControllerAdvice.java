 package cn.nanmi.msts.web.core;

 import cn.nanmi.msts.web.enums.ErrorCode;
 import cn.nanmi.msts.web.response.CSResponse;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.web.bind.annotation.ControllerAdvice;
 import org.springframework.web.bind.annotation.ExceptionHandler;
 import org.springframework.web.bind.annotation.ResponseBody;
 import org.springframework.web.context.request.NativeWebRequest;

 /**
  * spring controller的异常扑捉类，统一处理异常状态的响应
  */
 @ControllerAdvice
 public class BaseControllerAdvice {

     /**
      * 日志记录器
      */
     private final static Logger LOGGER = LoggerFactory.getLogger(BaseControllerAdvice.class);

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
         return new CSResponse(ErrorCode.SYSTEM_UNKNOW_ERROR);
     }
 }
