package cn.nanmi.msts.web.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

/**
 * Json转换工具类<br>
 * 将对象转换为Json格式字符串
 *
 * @author Vic Ding
 * @version [版本号, 2016年1月7日]
 */
public class JsonUtil {

    /**
     * 
     * 功能描述: <br>
     * Map集合对象转为Json格式字符串
     *
     * @author Vic Ding
     * @version [版本号, 2016年1月7日]
     * @param map
     * @return
     */
    public static String toJson(Map<String, Object> map) {
        String result = "";
        for (String name : map.keySet()) {
            Object value = map.get(name);
            String s = castToJson(value);
            if (s != null) {
                result += "\"" + name + "\":" + s + ",";
            } else if (value instanceof List<?>) {
                String v = toJson((List<?>) value);
                result += "\"" + name + "\":" + v + ",";
            } else if (value instanceof Object[]) {
                String v = toJson((Object[]) value);
                result += "\"" + name + "\":" + v + ",";
            } else if (value instanceof Map<?, ?>) {
                Map<String, Object> attr = castMap((Map<?, ?>) value);
                attr = removeListAttr(attr);
                result += "\"" + name + "\":" + toJson(attr) + ",";
            } else if (value.getClass().getName().startsWith("java") == false) {
                Map<String, Object> attr = getAttributes(value);
                attr = removeListAttr(attr);
                result += "\"" + name + "\":" + toJson(attr) + ",";
            } else {
                result += "\"" + name + "\":" + "\"" + value.toString() + "\",";
            }
        }
        if (result.length() == 0) {
            return "{}";
        } else {
            return "{" + result.substring(0, result.length() - 1) + "}";
        }
    }

    /**
     * 
     * 功能描述: <br>
     * 数组对象转为Json格式字符串
     *
     * @author Vic Ding
     * @version [版本号, 2016年1月7日]
     * @param aa
     * @return
     */
    public static String toJson(Object[] aa) {
        if (aa.length == 0) {
            return "[]";
        } else {
            String result = "";
            for (Object obj : aa) {
                String s = castToJson(obj);
                if (s != null) {
                    result += s + ",";
                } else if (obj instanceof Map<?, ?>) {
                    Map<String, Object> map = castMap((Map<?, ?>) obj);
                    map = removeListAttr(map);
                    result += toJson(map) + ",";
                } else {
                    Map<String, Object> attr = getAttributes(obj);
                    attr = removeListAttr(attr);
                    result += toJson(attr) + ",";
                }
            }
            return "[" + result.substring(0, result.length() - 1) + "]";
        }
    }

    /**
     * 
     * 功能描述: <br>
     * List集合转为Json格式字符串
     *
     * @author Vic Ding
     * @version [版本号, 2016年1月7日]
     * @param ll
     * @return
     */
    public static String toJson(List<?> ll) {
        return toJson(ll.toArray());
    }

    /**
     * 
     * 功能描述: <br>
     * 获取对象的属性
     *
     * @author Vic Ding
     * @version [版本号, 2016年1月7日]
     * @param obj
     * @return
     */
    public static Map<String, Object> getAttributes(Object obj) {
        Class<?> c = obj.getClass();
        try {
            Method method = c.getMethod("isProxy");
            Boolean result = (Boolean) method.invoke(obj);
            if (result == true) {
                c = c.getSuperclass();
            }
        } catch (Exception e) {
        }
        Map<String, Object> attr = new HashMap<String, Object>();

        // 取得所有公共字段
        for (Field f : c.getFields()) {
            try {
                Object value = f.get(obj);
                attr.put(f.getName(), value);
            } catch (Exception e) {
            }
        }

        // 取得所有本类方法
        for (Method m : c.getDeclaredMethods()) {
            String mname = m.getName();
            if (mname.equals("getClass")) {
                continue;
            } else if (mname.startsWith("get")) {
                String pname = mname.substring(3);
                if (pname.length() == 1) {
                    pname = pname.toLowerCase();
                } else {
                    pname = pname.substring(0, 1).toLowerCase() + pname.substring(1);
                }

                try {
                    Object value = m.invoke(obj);
                    attr.put(pname, value);
                } catch (Exception e) {
                }
            } else if (mname.startsWith("is")) {
                String pname = mname.substring(2);
                if (pname.length() == 1) {
                    pname = pname.toLowerCase();
                } else {
                    pname = pname.substring(0, 1).toLowerCase() + pname.substring(1);
                }

                try {
                    Object value = m.invoke(obj);
                    attr.put(pname, value);
                } catch (Exception e) {
                }
            }
        }
        return attr;
    }

    /**
     * 
     * 功能描述: <br>
     * 简单对象转为Json格式字符串
     *
     * @author Vic Ding
     * @version [版本号, 2016年1月7日]
     * @param obj
     * @return 如果是简单对象则返回JSON，如果是复杂对象则返回null
     * @since [产品/模块版本](可选)
     */
    private static String castToJson(Object obj) {
        if (obj == null) {
            return "null";
        } else if (obj instanceof Boolean) {
            return obj.toString();
        } else if (obj instanceof Integer || obj instanceof Long || obj instanceof Float || obj instanceof Double
                || obj instanceof Short || obj instanceof java.math.BigInteger || obj instanceof java.math.BigDecimal) {
            return obj.toString();
        } else if (obj instanceof String) {
            String v = (String) obj;
            v = v.replaceAll("\\\\", "\\\\\\\\");
            v = v.replaceAll("\n", "\\\\n");
            v = v.replaceAll("\r", "\\\\r");
            v = v.replaceAll("\t", "\\\\t");
            v = v.replaceAll("\"", "\\\\\"");
            return "\"" + v + "\"";
        } else if (obj instanceof java.sql.Date) {
            java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
            java.sql.Date v = (java.sql.Date) obj;
            String s = df.format(new java.util.Date(v.getTime()));
            return "\"" + s + "\"";
        } else if (obj instanceof java.sql.Timestamp) {
            java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.sql.Timestamp v = (java.sql.Timestamp) obj;
            String s = df.format(new java.util.Date(v.getTime()));
            return "\"" + s + "\"";
        } else if (obj instanceof java.util.Date) {
            java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
            java.util.Date v = (java.util.Date) obj;
            String s = df.format(v);
            return "\"" + s + "\"";
        } else {
            return null;
        }

    }

    /**
     * 
     * 功能描述: <br>
     * Map键值类型转换
     *
     * @author Vic Ding
     * @version [版本号, 2016年1月7日]
     * @param map
     * @return
     */
    public static Map<String, Object> castMap(Map<?, ?> map) {
        Map<String, Object> newMap = new HashMap<String, Object>();
        for (Object key : map.keySet()) {
            newMap.put(key.toString(), map.get(key));
        }
        return newMap;
    }

    /**
     * 
     * 功能描述: <br>
     * 删除Map集合中值是List集合对象的元素
     *
     * @author Vic Ding
     * @version [版本号, 2016年1月7日]
     * @param map
     * @return
     */
    private static Map<String, Object> removeListAttr(Map<String, Object> map) {
        Map<String, Object> newMap = new HashMap<String, Object>();
        for (Entry<String, Object> en : map.entrySet()) {
            if (!(en.getValue() instanceof List<?>)) {
                newMap.put((String) en.getKey(), en.getValue());
            }
        }
        return newMap;
    }

    /**
     * 
     * 功能描述: <br>
     * 数组对象转为Json格式字符串
     *
     * @author Vic Ding
     * @version [版本号, 2016年1月7日]
     * @param array
     * @return
     */
    public static String array2Json(Object[] array) {
        return JSONArray.fromObject(array).toString();
    }

    /**
     * 
     * 功能描述: <br>
     * 对象转为Json格式字符串
     *
     * @author Vic Ding
     * @version [版本号, 2016年1月7日]
     * @param bean
     * @return
     */
    public static String beanToJson(Object bean) {
        return JSONObject.fromObject(bean).toString();
    }

    /**
     * 格式化输出的log信息
     * @param key
     * @return
     */
    public static String formatLog(String... key) {
        StringBuffer sb = new StringBuffer();
        StackTraceElement[] stacks = new Throwable().getStackTrace();
        sb.append("[method=").append(stacks[1].getMethodName()).
                append("] [line=").append(stacks[1].getLineNumber()).append("]");

        for (int i = 0; i < key.length; i++) {
            if (i % 2 == 0) {
                sb.append("[").append(key[i]).append("=");
            } else {
                sb.append(key[i]).append("]");
            }
        }
        return sb.toString();
    }

    /**
     * 前端访问的log日志输出
     * @param request
     * @param key
     * @return
     */
    public static String formatLog(HttpServletRequest request, String... key) {
        StringBuffer sb = new StringBuffer();
        StackTraceElement[] stacks = new Throwable().getStackTrace();
        String ip = HttpUtils.getxFFIp(request);
        String realIp = HttpUtils.getXRealIp(request);
        String remoteIp = HttpUtils.getRemoteIp(request);
        if (null != ip) {
            sb.append("[xFFIp=").append(ip).append("]");
        } else if (null != realIp) {
            sb.append("[realIp=").append(realIp).append("]");
        } else {
            sb.append("[remoteIp=").append(remoteIp).append("]");
        }
        sb.append("[method=").append(stacks[1].getMethodName()).
                append("] [line=").append(stacks[1].getLineNumber()).append("]");

        for (int i = 0; i < key.length; i++) {
            if (i % 2 == 0) {
                sb.append("[").append(key[i]).append("=");
            } else {
                sb.append(key[i]).append("]");
            }
        }
        return sb.toString();
    }
}
