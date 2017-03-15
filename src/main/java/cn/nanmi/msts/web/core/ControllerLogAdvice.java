package cn.nanmi.msts.web.core;

import cn.nanmi.msts.web.utils.JsonUtil;
import cn.nanmi.msts.web.web.filter.RequestIdFilter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 功能简述:<br> 
 * 访问日志:记录用户访问信息
 *
 * @author Vic Ding
 * @version [V1.0, 2016年11月10日]
 */
@Component
@Aspect
public class ControllerLogAdvice {

    /**
     * 日志
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(ControllerLogAdvice.class);

    /**
     * 
     * 功能描述: <br>
     * 记录用户访问前后日志
     *
     * @author Vic Ding
     * @version [版本号, 2016年1月9日]
     * @param pjp 切点
     * @return 访问处理结果
     * @throws Throwable 异常
     */
    @Around("execution(* cn.nanmi.msts.web.web.controller..*(..))")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        // 本次访问标识号
        String requestId = null;
        // 用户请求日志记录
        if (LOGGER.isInfoEnabled()) {
            // 获取访问参数
            Object[] args = pjp.getArgs();
            // 记录请求信息
            requestId = printArgs(args);
        }

        // 处理请求
        Object result = pjp.proceed();

        // 处理结果记录
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("请求编号：" + requestId + "，处理结果：" + JsonUtil.beanToJson(result));
        }

        return result;
    }

    /**
     * 
     * 功能描述: <br>
     * 记录请求信息
     *
     * @author Vic Ding
     * @version [版本号, 2016年1月9日]
     * @param args 访问参数
     * @return 访问标识号
     */
    private String printArgs(Object[] args) {
        // 生成唯一标识
        String requestId = null;
        // 访问信息
        StringBuffer sbRe = new StringBuffer();
        // 参数信息
        StringBuffer sbPa = new StringBuffer();
        sbPa.append("[params:");

        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof HttpServletRequest) {
                    HttpServletRequest request = (HttpServletRequest) args[i];
                    // 设备号
                    requestId = RequestIdFilter.RID_THREAD_LOCAL.get();
                    // 请求地址
                    String uri = request.getRequestURI();
                    sbRe.append("[uri:").append(uri).append("]");
                } else {
                    // 获取RequestBody，图片和响应不处理
                    if (!(args[i] instanceof HttpServletResponse) && !(args[i] instanceof MultipartFile)) {
                        try{
                            sbPa.append(JsonUtil.beanToJson(args[i]));
                        }catch (Exception e){

                        }

                    }
                }
            }
        }

        // 遍历完所有参数，加上封闭括号
        sbPa.append("]");

        // 打印参数日志
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("请求编号：" + requestId + "，请求信息：" + sbRe.toString() + sbPa.toString());
        }

        return requestId;
    }

}
