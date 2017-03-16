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

    List<BiddingVO> biddingVOList;

    public BiddingListVO(List<BiddingVO> biddingVOList) {
        this.biddingVOList = biddingVOList;
    }

    public List<BiddingVO> getBiddingVOList() {
        return biddingVOList;
    }

    public void setBiddingVOList(List<BiddingVO> biddingVOList) {
        this.biddingVOList = biddingVOList;
    }
}
