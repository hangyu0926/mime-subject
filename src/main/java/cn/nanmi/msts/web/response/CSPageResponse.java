package cn.nanmi.msts.web.response;


import cn.nanmi.msts.web.enums.ErrorCode;

import java.io.Serializable;

/**
 * User: zhuhaiwei
 * Date: 2016/11/7
 * Project: cs-platform
 * Description:
 */
public class CSPageResponse extends CSResponse {
    private static final long serialVersionUID = 7839530623672715293L;
    private Long totalCount;

    public CSPageResponse(){}

    public CSPageResponse(Serializable detailInfo , Long totalCount){
        super(detailInfo);
        this.totalCount = totalCount;
    }

    public CSPageResponse(String errorCode, String errorDesc){
        super(errorCode,errorDesc);
    }

    public CSPageResponse(ErrorCode errorCode){
        super(errorCode);
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
}
