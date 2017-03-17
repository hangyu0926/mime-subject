package cn.nanmi.msts.web.web.vo.out;

import java.io.Serializable;
import java.util.List;

/**
 * Created with cn.nanmi.msts.web.web.vo.out.
 * User: jiangbin
 * Date: 2017/3/17
 * Time: 17:51
 */
public class MyBiddingListVO implements Serializable {
    private Long totalCount;
    List<MyBiddingVO> myBiddingVOList;

    public MyBiddingListVO() {
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public List<MyBiddingVO> getMyBiddingVOList() {
        return myBiddingVOList;
    }

    public void setMyBiddingVOList(List<MyBiddingVO> myBiddingVOList) {
        this.myBiddingVOList = myBiddingVOList;
    }
}
