package indi.jl.sp.security.api.util;

import indi.jl.sp.core.SpApplicationContext;
import indi.jl.sp.core.util.StringUtil;
import indi.jl.sp.security.api.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class SecurityUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityUtil.class);

    private SecurityUtil() {

    }

    public static String getSecretToken(HttpServletRequest request) {
        if (null == request) {
            return null;
        }
        String token = getSecretTokenFromHeader(request);
        if (StringUtil.isNotEmpty(token)) {
            LOGGER.debug("从请求头中获取secretToken:{}", token);
        }
        if (token == null) {
            token = getSecretTokenFromReqParameter(request);
            if (StringUtil.isNotEmpty(token)) {
                LOGGER.debug("从request中获取secretToken:{}", token);
            }
        }
        return token;
    }

    public static String getAccessToken(HttpServletRequest request) {
        if (null == request) {
            return null;
        }
        String token = getAccessTokenFromHeader(request);
        if (StringUtil.isNotEmpty(token)) {
            LOGGER.debug("从请求头中获取accessToken:{}", token);
        }
        if (token == null) {
            token = getAccessTokenFromReqParameter(request);
            if (StringUtil.isNotEmpty(token)) {
                LOGGER.debug("从request中获取accessToken:{}", token);
            }
        }
        return token;
    }

    private static String getAccessTokenFromHeader(HttpServletRequest request) {
        return request.getHeader(SpApplicationContext.getBean(SecurityProperties.class).getAccessTokenName());
    }


    private static String getAccessTokenFromReqParameter(HttpServletRequest request) {
        return request.getParameter(SpApplicationContext.getBean(SecurityProperties.class).getAccessTokenName());
    }

    private static String getSecretTokenFromHeader(HttpServletRequest request) {
        return request.getHeader(SpApplicationContext.getBean(SecurityProperties.class).getSecretTokenName());
    }


    private static String getSecretTokenFromReqParameter(HttpServletRequest request) {
        return request.getParameter(SpApplicationContext.getBean(SecurityProperties.class).getSecretTokenName());
    }
}
