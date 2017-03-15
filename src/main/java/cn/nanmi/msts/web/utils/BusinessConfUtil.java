package cn.nanmi.msts.web.utils;

import cn.nanmi.msts.web.core.ConstantHelper;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 业务配置工具类<br>
 * 从业务配置文件中读取配置信息
 *
 * @author Vic Ding
 * @version [版本号, 2016年1月8日]
 */
public class BusinessConfUtil {

    /**
     * 日志记录器
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(BusinessConfUtil.class);

    /**
     * 配置
     */
    private static PropertiesConfiguration properties = null;

    /**
     * 
     * 功能描述: <br>
     * 加载配置文件
     *
     * @author Vic Ding
     * @version [版本号, 2016年1月8日]
     * @param filePath 文件路径
     */
    private static void loadProperties() {
        properties = new PropertiesConfiguration();
        properties.setEncoding("utf-8");
        properties.setFileName(ConstantHelper.BUSINESS_CONF_PROPS);
        try {
            properties.load();
        } catch (Exception e) {
            LOGGER.error("------ 加载业务配置文件出现异常 ------", e);
            try {
                properties.load();
            } catch (Exception e1) {
                LOGGER.error("------ 第二次尝试加载业务配置文件出现异常 ------", e1);
            }
        }
    }

    /**
     * 
     * 功能描述: <br>
     * 根据键来获取资源文件中的值
     *
     * @author Vic Ding
     * @version [版本号, 2016年1月8日]
     * @param key 键
     * @return 值
     */
    public static String get(String key) {
        // 判断配置文件是否加载过
        if (properties == null) {
            // 加载配置文件
            loadProperties();
        }
        // 获取
        String value = null;
        if (properties.containsKey(key)) {
            value = properties.getString(key);
        } else {
            LOGGER.error("------ 没有找到对应的配置信息，key:" + key + " ------");
        }

        return value;
    }

    /**
     * 
     * 功能描述: <br>
     * 获取动态提示信息
     *
     * @author Vic Ding
     * @version [版本号, 2016年1月8日]
     * @param key 键
     * @param args 动态参数
     * @return 动态提示
     */
    public static String getArgs(String key, Object[] args) {
        // 判断配置文件是否加载过
        if (properties == null) {
            // 加载配置文件
            loadProperties();
        }
        // 获取
        String value = null;
        if (properties.containsKey(key)) {
            value = properties.getString(key);
            // 填入参数
            String.format(value, args);
        } else {
            LOGGER.error("------ 没有找到对应的配置信息，key:" + key + " ------");
        }

        return value;
    }
}
