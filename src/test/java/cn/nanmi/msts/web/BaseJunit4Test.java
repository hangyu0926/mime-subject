package cn.nanmi.msts.web;


import cn.nanmi.msts.web.business.IStockBusiness;
import cn.nanmi.msts.web.dao.OrderMapper;
import cn.nanmi.msts.web.enums.ErrorCode;
import cn.nanmi.msts.web.model.BiddingDetailDTO;
import cn.nanmi.msts.web.response.CSResponse;
import cn.nanmi.msts.web.task.UpdateStatusJob;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 单元测试<br>
 * 单元测试基类
 *
 * @author Vic
 * @version [V1.0, 2016年3月17日]
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:application/applicationContext.xml",
        "classpath*:application/applicationContext-dao-test.xml",
        "classpath*:application/applicationContext-aopTx.xml"})
public class BaseJunit4Test {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private IStockBusiness stockBusiness;

    @Test
    public void test() throws ParseException {
        stockBusiness.updateOrderState();
    }

    @Test
    public void test2(){
        List<String> orders = orderMapper.getCompleteOrder();
    }
}
