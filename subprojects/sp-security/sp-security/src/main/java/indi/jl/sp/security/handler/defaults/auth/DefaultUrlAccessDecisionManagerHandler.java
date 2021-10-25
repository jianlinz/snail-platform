package indi.jl.sp.security.handler.defaults.auth;

import indi.jl.sp.security.api.SpAuthentication;
import indi.jl.sp.security.api.pojo.SecurityResource;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DefaultUrlAccessDecisionManagerHandler implements AccessDecisionManager {

    private static final PathMatcher MATCHER = new AntPathMatcher();

    /**
     * 匹配当前登录人 是否有操作资源的权限
     *
     * @param auth             当前登录人
     * @param filterInvocation 当前request
     * @param resources        当前请求所需资源集合
     */
    @Override
    public void decide(Authentication auth, Object filterInvocation, Collection<ConfigAttribute> resources) {
        List<SecurityResource> securityResources = SpAuthentication.getUserDetail().getResources();
        String needResource = new ArrayList<>(resources).get(0).getAttribute();
        for (SecurityResource securityResource : securityResources) {
            String[] supportMethods = securityResource.getMethod().split(",");
            for (String supportMethod : supportMethods) {
                if (MATCHER.match(supportMethod.toUpperCase() + ":" + securityResource.getUrl(), needResource)) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("没有权限操作该资源!");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return SecurityResource.class.isAssignableFrom(aClass);
    }
}
