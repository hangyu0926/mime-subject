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
        Integer a = 3;
        Integer b = 2;
        if(a==4 ||a ==3 && b<0){
            System.out.print("aaaa");
        }
    }

    @Test
    public void test2(){
    }
}
