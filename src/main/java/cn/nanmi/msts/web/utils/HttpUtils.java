package cn.nanmi.msts.web.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by wormchaos on 2015/2/2.
 */
public class HttpUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * POST请求
     */
    private static final String METHOD_POST = "POST";
    /**
     * GET请求
     */
    private static final String METHOD_GET = "GET";
    /**
     * 编码
     */
    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 发送httpspost请求
     * @param urlString
     * @param body
     * @return String
     * @throws Exception
     */
    public static String sendHttpsPost(String urlString, String body) throws Exception{
        HttpsURLConnection conn = null;
        OutputStream out = null;
        String rsp = null;
        try {

            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[] { new DefaultTrustManager() },
                    new SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection
                    .setDefaultHostnameVerifier(new HostnameVerifier(){
                        @Override
                        public boolean verify(String s,
                                              SSLSession sslsession) {
                            return true;
                        }
                    });
            SSLContext.setDefault(sc);

            conn = getConnection(new URL(urlString), METHOD_POST,  "application/json; charset=utf-8");
            conn.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);

            out = conn.getOutputStream();
            out.write(body.getBytes());

            //获取返回数据
            rsp = getResponseAsString(conn);

            return rsp;

        } catch (Exception e) {
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public static String sendHttpsGet(String urlString) throws Exception{
        HttpsURLConnection conn = null;
        OutputStream out = null;
        String rsp = null;
        try {
            try {
                SSLContext sc = SSLContext.getInstance("TLS");
                sc.init(null, new TrustManager[] { new DefaultTrustManager() },
                        new SecureRandom());
                HttpsURLConnection
                        .setDefaultSSLSocketFactory(sc.getSocketFactory());
                HttpsURLConnection
                        .setDefaultHostnameVerifier(new HostnameVerifier() {
                            @Override
                            public boolean verify(String s,
                                                  SSLSession sslsession) {
                                return true;
                            }
                        });
                SSLContext.setDefault(sc);

                conn = getConnection(new URL(urlString), METHOD_GET,  "application/json; charset=utf-8");
                conn.setHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
                conn.setConnectTimeout(30000);
                conn.setReadTimeout(30000);

                rsp = getResponseAsString(conn);
                for(int j = 1;conn.getHeaderFieldKey(j) != null;j++){
                    if(conn.getHeaderFieldKey(j).toLowerCase().equals("set-cookie")){
                        // CookieManager.getInstance().setCookie(urlString,conn.getHeaderField(j));
                    }
                }
                return rsp;
            } catch (Exception e) {
                throw e;
            }
        } finally {
            if (out != null) {
                out.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
    }


    private static class DefaultTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

    }

    /**
     * 获取HTTPS连接
     * @param url 连接URL
     * @param method 发送发法
     * @param contentType 上下文类型
     * @return HttpsURLConnection
     * @throws IOException
     */
    private static HttpsURLConnection getConnection(URL url, String method, String contentType) throws IOException {
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Accept", "text/xml,text/javascript,text/html");
        conn.setRequestProperty("Content-Type", contentType);

        conn.setRequestProperty("Accept-Encoding", "gzip");
        conn.setRequestProperty("Accept-Language", "zh-cn");
        conn.setRequestProperty("Accept", "*/*");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty(
                "User-Agent",
                "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET4.0C; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");

        return conn;
    }

    protected static String getResponseAsString(HttpURLConnection conn) throws IOException {
        String charset = getResponseCharset(conn.getContentType());
        InputStream es = conn.getErrorStream();
        logger.info("获取HTTPS返回码为：" + conn.getResponseCode());
        //System.out.println(conn.getResponseCode());
        if (es == null) {
            return getStreamAsString(conn.getInputStream(), charset,conn);
        } else {
            String msg = getStreamAsString(es, charset,conn);
            if (isEmpty(msg)) {
                throw new IOException(conn.getResponseCode() + ":" + conn.getResponseMessage());
            } else {
                throw new IOException(msg);
            }
        }
    }


    private static String getStreamAsString(InputStream stream, String charset,HttpURLConnection conn) throws IOException {
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            byte[] d = new byte[10 * 1024];
            int ch = 0;
            while ((ch = stream.read(d)) != -1){
                bos.write(d, 0, ch);
            }
            byte [] data = bos.toByteArray();
            /*if("gzip".equals(conn.getHeaderField("Content-Encoding"))){
                data = Util.gzipdecompress(data);
            }*/

            return new String(data,charset);
        } finally {
            if(bos != null)
                bos.close();

            if (stream != null) {
                stream.close();
            }
        }
    }


    private static String getResponseCharset(String ctype) {
        String charset = DEFAULT_CHARSET;

        if (!isEmpty(ctype)) {
            String[] params = ctype.split(";");
            for (String param : params) {
                param = param.trim();
                if (param.startsWith("charset")) {
                    String[] pair = param.split("=", 2);
                    if (pair.length == 2) {
                        if (!isEmpty(pair[1])) {
                            charset = pair[1].trim();
                        }
                    }
                    break;
                }
            }
        }

        return charset;
    }

    private static boolean isEmpty(String str) {
        if (null == str || str.trim().equals("")) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        String urlString = "https://liumi.momodai.cn/liumiApp/liumi/security/cash/rechargeCallback";
        String body = "{\"respCode\":\"0000\",\"codeMsg\":\"成功\"}";
        sendHttpsPost(urlString, body);
        //System.out.println(result);
    }
    /**
     * 获取终端设备的ip地址
     * @param httpServletRequest
     * @return
     */
    public static String getxFFIp(HttpServletRequest httpServletRequest){
        String ip = httpServletRequest.getHeader("x-forwarded-for");
        return ip;
    }

    public static String getXRealIp(HttpServletRequest httpServletRequest) {
        String ip = httpServletRequest.getHeader("X-Real-IP");
        return ip;
    }

    public static String getRemoteIp(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getRemoteAddr();
    }
}
