package cn.nanmi.msts.web.advice;

import cn.nanmi.msts.web.enums.ErrorCode;
import cn.nanmi.msts.web.response.CSResponse;
import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * Created by qianweijie on 2016/11/28.
 */
public class ErrorConvertAdvice implements AfterReturningAdvice {
    /**
     * SUCCESS("000000", "成功"),
     * SYSTEM_UNKONW_ERROR("000001", "未知异常"),
     * FAIL_INVALID_PARAMS("000002", "参数非法"),
     * ILLEGAL_NULL_PARAM("000003", "参数不能为空"),
     * PARAM_NOT_SAME("000004", "入参类型不一致");
     *
     * @param returnValue
     * @param method
     * @param args
     * @param target
     * @throws Throwable
     */
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        CSResponse csResponse = (CSResponse) returnValue;
        if (csResponse == null) {
            return;
        }
        switch (csResponse.getCode()) {
            case "000000":
                csResponse.setCode(ErrorCode.SUCCESS.getErrorCode());
                csResponse.setDesc(ErrorCode.SUCCESS.getErrorDesc());
                break;
            case "000001":
                csResponse.setCode(ErrorCode.UNKNOWN_WRROR.getErrorCode());
                csResponse.setDesc(ErrorCode.UNKNOWN_WRROR.getErrorDesc());
                break;
            case "000002":
                csResponse.setCode(ErrorCode.FAIL_INVALID_PARAMS.getErrorCode());
                csResponse.setDesc(ErrorCode.FAIL_INVALID_PARAMS.getErrorDesc());
                break;
            case "000003":
                csResponse.setCode(ErrorCode.ILLEGAL_NULL_PARAM.getErrorCode());
                csResponse.setDesc(ErrorCode.ILLEGAL_NULL_PARAM.getErrorDesc());
                break;
            case "000004":
                csResponse.setCode(ErrorCode.PARAM_NOT_SAME.getErrorCode());
                csResponse.setDesc(ErrorCode.PARAM_NOT_SAME.getErrorDesc());
                break;
            default:
                csResponse.setCode(csResponse.getCode());
                csResponse.setDesc(csResponse.getDesc());
                break;
        }
    }
}
