package indi.jl.sp.cloud.feign.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import indi.jl.sp.core.SpApplicationContext;
import indi.jl.sp.core.util.RequestUtil;
import indi.jl.sp.core.util.StringUtil;
import indi.jl.sp.security.api.SecurityProperties;
import indi.jl.sp.security.api.SpAuthentication;
import indi.jl.sp.security.api.util.SecurityUtil;

import javax.servlet.http.HttpServletRequest;

public class FeignAuthRequestInterceptor implements RequestInterceptor {

    private final static String COOKIE = "cookie";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        if (StringUtil.isNotEmpty(SpAuthentication.getSecretToken())) {
            requestTemplate.header(SpApplicationContext.getApplicationContext().getBean(SecurityProperties.class).getSecretTokenName(),
                    SpAuthentication.getSecretToken());
            return;
        }
        HttpServletRequest request = RequestUtil.getCurrentRequest();
        if (null == request) {
            return;
        }
        String token = SecurityUtil.getSecretToken(request);
        if (StringUtil.isNotEmpty(token)) {
            requestTemplate.header(SpApplicationContext.getApplicationContext().getBean(SecurityProperties.class).getSecretTokenName(), token);
            return;
        }
        token = SecurityUtil.getAccessToken(request);
        if (StringUtil.isNotEmpty(token)) {
            requestTemplate.header(SpApplicationContext.getApplicationContext().getBean(SecurityProperties.class).getAccessTokenName(), token);
            return;
        }
        requestTemplate.header(COOKIE, request.getHeader(COOKIE));
    }
}
