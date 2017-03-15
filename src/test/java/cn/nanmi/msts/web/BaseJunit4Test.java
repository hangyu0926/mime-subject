package cn.nanmi.msts.web;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
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
@Transactional
public class BaseJunit4Test {

    @Test
    public void test() {
    }

    @Test
    public void test2(){
    }
}
