package cn.nanmi.msts.web;


import cn.nanmi.msts.web.dao.OrderMapper;
import cn.nanmi.msts.web.model.BiddingDetailDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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
    public void test() {
        BiddingDetailDTO a = orderMapper.getOrderDetail("AAAAA");
    }

    @Test
    public void test2(){
    }
}
