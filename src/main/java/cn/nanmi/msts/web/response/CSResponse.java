package cn.nanmi.msts.web.response;


import cn.nanmi.msts.web.enums.ErrorCode;

import java.io.Serializable;

/**
 * User: zhuhaiwei
 * Date: 2016/11/3
 * Project: cs-platform
 * Description:
 */
public class CSResponse implements Serializable {

    private static final long serialVersionUID = -4496007777208425865L;
    private Serializable detailInfo;
    private String desc;
    private String code = ErrorCode.SUCCESS.getErrorCode();

    public CSResponse(){}


    public CSResponse(Serializable detailInfo,ErrorCode errorCode) {
        this.detailInfo = detailInfo;
        this.desc = errorCode.getErrorDesc();
        this.code = errorCode.getErrorCode();
    }

    public CSResponse(String errorCode, String errorDesc) {
        this.detailInfo = null;
        this.desc = errorDesc;
        this.code = errorCode;
    }

    public CSResponse(ErrorCode errorCode){
        this.desc = errorCode.getErrorDesc();
        this.code = errorCode.getErrorCode();
    }

    public CSResponse(Serializable detailInfo) {
        this.detailInfo = detailInfo;
    }

    public Serializable getDetailInfo() {
        return detailInfo;
    }

    public void setDetailInfo(Serializable detailInfo) {
        this.detailInfo = detailInfo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "CSResponse{" +
                "detailInfo=" + detailInfo +
                ", desc='" + desc + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}

