package cn.nanmi.msts.web.enums;

/**
 * Created by qianweijie on 2016/11/21.
 */
public enum ErrorCodeByOthers {

    SUCCESS("1000","成功"),
    FAIL("1001","失败");

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误描述
     */
    private String errorDesc;

    ErrorCodeByOthers(String errorCode, String errorDesc) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }
}
