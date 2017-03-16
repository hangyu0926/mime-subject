package cn.nanmi.msts.web.business;

import cn.nanmi.msts.web.response.CSResponse;
import cn.nanmi.msts.web.web.vo.in.BiddingListQueryVO;

public interface IStockBusiness {

    /**
     *  查询竞拍列表（分页)
     * @param queryVO
     * @return
     */
    CSResponse getBiddingList(BiddingListQueryVO queryVO);


}
