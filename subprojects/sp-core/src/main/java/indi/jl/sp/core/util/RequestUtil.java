package indi.jl.sp.core.util;

import indi.jl.sp.core.exception.SpSystemException;
import org.springframework.util.StreamUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UrlPathHelper;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class RequestUtil {

    private static UrlPathHelper helper;

    private static String UNKNOW = "unknown";

    private RequestUtil() {

    }

    static {
        new RequestUtil();
        helper = new UrlPathHelper();
    }

    /**
     * 获得当前 Request
     *
     * @return 当前 Request
     * @throws IllegalStateException 当前线程未绑定 request 时抛出
     */
    public static HttpServletRequest getCurrentRequest() throws IllegalStateException {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (null == requestAttributes) {
            return null;
        }
        ServletRequestAttributes servletRequestAttributes = ((ServletRequestAttributes) requestAttributes);
        if (null != servletRequestAttributes) {
            return servletRequestAttributes.getRequest();
        }
        return null;
    }

    public static String getURIPath(HttpServletRequest request) {
        String result = "";
        try {
            result = new URI(helper.getPathWithinApplication(request)).getPath();
        } catch (URISyntaxException e) {
            throw new SpSystemException("Parse URI error!", e);
        }
        return result;
    }
}
