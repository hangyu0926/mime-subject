package cn.nanmi.msts.web.web.filter;

import cn.nanmi.msts.web.core.ConstantHelper;
import cn.nanmi.msts.web.model.UserDTO;
import cn.nanmi.msts.web.utils.JsonUtil;
import cn.nanmi.msts.web.enums.ErrorCode;
import cn.nanmi.msts.web.response.CSResponse;
import cn.nanmi.msts.web.utils.BusinessConfUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * User: zhanglei
 * Date: 2016/11/15
 * Description:
 */
public class SessionFilter implements javax.servlet.Filter {
    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 不需要session用户校验的接口
     */
    private String excludeUrl = null;
    private String wxUrl = null;
    private String fileServerOutPath;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        excludeUrl = filterConfig.getInitParameter("excludeUrl");
        excludeUrl = excludeUrl.replaceAll("\n", "");
        excludeUrl = excludeUrl.replaceAll("\\s*", "");

        wxUrl = filterConfig.getInitParameter("wxUrl");
        wxUrl = wxUrl.replaceAll("\n", "");
        wxUrl = wxUrl.replaceAll("\\s*", "");

        fileServerOutPath = BusinessConfUtil.get("sys.root.address");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        httpServletRequest.setCharacterEncoding("UTF-8");
        httpServletRequest.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        HttpSession session = httpServletRequest.getSession();
        UserDTO user = (UserDTO) session.getAttribute(ConstantHelper.USER_SESSION);
        String requestURI = httpServletRequest.getRequestURI();
        logger.info(JsonUtil.formatLog(httpServletRequest, "start " + " request", "\"" + requestURI + "\""));
//        //同个session再次打开登录页时，重定向到主页
//        if (matchURI("views/login", requestURI) && null != user) {
//            httpServletResponse.sendRedirect(fileServerOutPath + httpServletRequest.getContextPath() + ConstantHelper.USER_INDEX);
//            return;
//        }
        if (matchURI(excludeUrl, requestURI)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            logger.info(JsonUtil.formatLog(httpServletRequest, "request end", requestURI));
            return;
        }
        if (user != null) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
                return;
        }
        logger.error("SessionFilter：session失效用户需要登录:"+requestURI);
        if (isWeb(requestURI)) {
            logger.error("SessionFilter：isWeb:"+requestURI);
            httpServletResponse.sendRedirect(fileServerOutPath + httpServletRequest.getContextPath() + ConstantHelper.USER_LOGIN);
        } else {
            //处理接口逻辑
            CSResponse csResponse;
            if(matchURI(wxUrl, requestURI)){
                String requestStr = requestToString(httpServletRequest);
                if(requestStr.contains("weixin")){
                    csResponse= new CSResponse(ErrorCode.WX_REDIRECT_LOGIN);
                }else{
                    csResponse = new CSResponse(ErrorCode.REDIRECT_LOGIN);
                }
            }else{
                csResponse = new CSResponse(ErrorCode.REDIRECT_LOGIN);
            }
//            httpServletResponse.getWriter().write(csResponse.toString());
            httpServletResponse.getWriter().write(JsonUtil.beanToJson(csResponse));
            httpServletResponse.getWriter().flush();
        }
    }

    @Override
    public void destroy() {
    }

    protected boolean matchURI(String excludeUrl, String url) {
        if (null == excludeUrl || "".equals(excludeUrl)) {
            return false;
        }
        String[] urlPatterns = excludeUrl.split(",");
        for (int i = 0; i < urlPatterns.length; i++) {
            if (url.contains(urlPatterns[i])) {
                return true;
            }
        }
        return false;
    }

    private boolean isWeb(String url) {
        if (url.contains("user")
                || url.contains("permission")
                || url.contains("project")
                ) {
            return false;
        }
        return true;
    }


    public String  requestToString (HttpServletRequest httpServletRequest){
        StringBuffer jb = new StringBuffer();
        String line = null;
        String str = "";
        try
        {
            BufferedReader reader = httpServletRequest.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
            str = jb.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
      return str;
    }

}
