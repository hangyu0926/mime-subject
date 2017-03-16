package cn.nanmi.msts.web.web.vo.in;

import java.io.Serializable;

/**
 * Created by Solo on 2016/11/21.
 */
public class GetUserListVO implements Serializable{
    private int pageSize;
    private int pageNo;
    private String keyWords;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }


    @Override
    public String toString() {
        return "GetUserListVO{" +
                "pageSize=" + pageSize +
                ", pageNo=" + pageNo +
                ", keyWords='" + keyWords + '\'' +
                '}';
    }

    public GetUserListVO(int pageSize, int pageNo, String keyWords) {
        this.pageSize = pageSize;
        this.pageNo = pageNo;
        this.keyWords = keyWords;
    }

    public GetUserListVO() {
    }
}
