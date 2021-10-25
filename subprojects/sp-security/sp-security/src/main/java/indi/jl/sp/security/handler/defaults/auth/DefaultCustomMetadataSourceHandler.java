package indi.jl.sp.security.handler.defaults.auth;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.web.util.UrlPathHelper;

import java.util.Collection;
import java.util.Collections;

/**
 * 当前请求所需要资源获取处理器
 */
public class DefaultCustomMetadataSourceHandler implements FilterInvocationSecurityMetadataSource {

    private static UrlPathHelper helper;

    static {
        helper = new UrlPathHelper();
    }

    /**
     * 计算当前请求所需要的资源
     *
     * @param filter request外层filter
     * @return requestMethod:applicationPath 如:GET:/error
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object filter) {
        FilterInvocation filterInvocation = (FilterInvocation) filter;
        return SecurityConfig.createList(filterInvocation.getRequest().getMethod()
                + ":"
                + helper.getPathWithinApplication(filterInvocation.getRequest()));
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return Collections.emptyList();
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
