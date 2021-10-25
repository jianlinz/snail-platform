package indi.jl.sp.security.api;

import indi.jl.sp.security.api.pojo.SecurityUserDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SpAuthentication {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpAuthentication.class);

    private SpAuthentication() {
    }

    private static final ThreadLocal<String> SECRET_TOKEN_THREAD_LOCAL = new InheritableThreadLocal<>();

    public static void setSecretToken(String secretToken) {
        SECRET_TOKEN_THREAD_LOCAL.set(secretToken);
    }

    public static String getSecretToken() {
        return SECRET_TOKEN_THREAD_LOCAL.get();
    }

    public static void cleanSecretToken() {
        SECRET_TOKEN_THREAD_LOCAL.remove();
    }

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static SecurityUserDetail getUserDetail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null == authentication) {
            return null;
        }
        if (authentication.getPrincipal() instanceof SecurityUserDetail) {
            return (SecurityUserDetail) authentication.getPrincipal();
        }
        LOGGER.error("当前线程中存在非法UserDetail，UserDetail类型:" + authentication.getPrincipal().getClass().getName());
        throw new RuntimeException("B_系统异常");
    }

}
