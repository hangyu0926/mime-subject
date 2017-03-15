package cn.nanmi.msts.web.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

/**
 * 实体与json映射<br>
 * 实体转换为json字符串
 *
 * @author Vic Ding
 * @version [版本号, 2016年1月7日]
 */
public class HibernateAwareObjectMapper extends ObjectMapper {

    /**
     * 序列化
     */
    private static final long serialVersionUID = -7073564922657772376L;

    /**
     * 使用父类方法改写构造
     */
    public HibernateAwareObjectMapper() {
        registerModule(new Hibernate4Module());
    }

}
