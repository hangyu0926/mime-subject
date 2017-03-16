package cn.nanmi.msts.web.web.Converter;

import cn.nanmi.msts.web.model.BiddingListDTO;
import cn.nanmi.msts.web.web.vo.out.BiddingVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with cn.nanmi.msts.web.web.Converter.
 * User: jiangbin
 * Date: 2017/3/16
 * Time: 15:14
 */
public class BiddingConverter {
    public static List<BiddingVO> BiddingDTO2VO(List<BiddingListDTO> biddingList){
        List<BiddingVO> biddingVOList = new ArrayList<>();



        if(biddingList != null && biddingList.size()>0){
            for(BiddingListDTO biddingListDTO:biddingList){

            }
        }



        return null;
    }
}
