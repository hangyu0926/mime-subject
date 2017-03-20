package cn.nanmi.msts.web;


import cn.nanmi.msts.web.dao.OrderMapper;
import cn.nanmi.msts.web.enums.ErrorCode;
import cn.nanmi.msts.web.model.BiddingDetailDTO;
import cn.nanmi.msts.web.response.CSResponse;
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
@Transactional
public class BaseJunit4Test {

    @Resource
    private OrderMapper orderMapper;

    @Test
    public void test() throws ParseException {
        String date1str = "2011-10-1 10:20:16";
        String date2str = "2011-10-07 15:50:35";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = dateFormat.parse(date1str);
        Date date2 = dateFormat.parse(date2str);
        int spanTime = date1.compareTo(date2);
        if(spanTime<=0){
            System.out.print("date1 < date2");
        }
    }

    @Test
    public void test2(){
    }
}
