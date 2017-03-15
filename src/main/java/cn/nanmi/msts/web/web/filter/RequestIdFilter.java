package cn.nanmi.msts.web.web.filter;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * 请求标识<br>
 * 请求标识传递过滤器
 *
 * @author colonel
 * @version [V1.0, 2016年2月26日]
 */
public class RequestIdFilter implements Filter {

    /**
     * 请求标识线程变量
     */
    public final static ThreadLocal<String> RID_THREAD_LOCAL = new ThreadLocal<String>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        // 向下转型
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        // 将请求标识存入线程变量
        String rid = request.getParameter("mmRid");
        if (StringUtils.isBlank(rid)) {
            rid = UUID.randomUUID().toString();
        }
        RID_THREAD_LOCAL.set(rid);

        // 继续
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
