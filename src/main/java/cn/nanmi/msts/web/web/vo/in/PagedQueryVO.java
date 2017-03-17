package cn.nanmi.msts.web.web.vo.in;

import java.io.Serializable;
import java.util.List;

/**
 * User: jiangbin
 * Date: 2017/1/16
 * Time: 17:29
 */
public class PagedQueryVO implements Serializable {
    /**
     * 每页消息数
     */
    private int pageSize;
    /**
     * 消息页数
     */
    private int pageNo;

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

    @Override
    public String toString() {
        return "PagedQueryVO{" +
                "pageSize=" + pageSize +
                ", pageNo=" + pageNo +
                '}';
    }
}
