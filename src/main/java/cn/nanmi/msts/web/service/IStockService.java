package cn.nanmi.msts.web.service;

import cn.nanmi.msts.web.model.BiddingListDTO;
import cn.nanmi.msts.web.response.CSResponse;
import java.util.List;
/**
 * Created with cn.nanmi.msts.web.service.
 * User: jiangbin
 * Date: 2017/3/16
 * Time: 9:33
 */
public interface IStockService {

    /**
     *  发布订单
     * @param biddingListDTO
     * @return
     */
    CSResponse releaseOrder(BiddingListDTO biddingListDTO);
    /**
     * 查询竞拍列表（分页)
     * @param startPage
     * @param pageSize
     * @return
     */
    List<BiddingListDTO> getBiddingList(int startPage,int pageSize);
}
