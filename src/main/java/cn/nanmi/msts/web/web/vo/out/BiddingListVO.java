package cn.nanmi.msts.web.web.vo.out;

import java.io.Serializable;
import java.util.List;

/**
 * Created with cn.nanmi.msts.web.web.vo.out.
 * User: jiangbin
 * Date: 2017/3/16
 * Time: 15:06
 */
public class BiddingListVO implements Serializable{
    private Long totalCount;
    List<BiddingVO> biddingVOList;

    public BiddingListVO() {
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public List<BiddingVO> getBiddingVOList() {
        return biddingVOList;
    }

    public void setBiddingVOList(List<BiddingVO> biddingVOList) {
        this.biddingVOList = biddingVOList;
    }
}
