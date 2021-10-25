package indi.jl.sp.security.context;

import indi.jl.sp.security.api.util.SecurityUtil;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SpHttpSessionJwtSecurityContextRepository extends HttpSessionSecurityContextRepository {

    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        if (null != SecurityUtil.getAccessToken(requestResponseHolder.getRequest())) {
            return SecurityContextHolder.createEmptyContext();
        }
        if (null != SecurityUtil.getSecretToken(requestResponseHolder.getRequest())) {
            return SecurityContextHolder.createEmptyContext();
        }
        return super.loadContext(requestResponseHolder);
    }

    public void saveContext(SecurityContext context, HttpServletRequest request,
                            HttpServletResponse response) {
        if (null != SecurityUtil.getAccessToken(request)) {
            return;
        }
        if (null != SecurityUtil.getSecretToken(request)) {
            return;
        }
        super.saveContext(context, request, response);
    }

    public boolean containsContext(HttpServletRequest request) {
        if (null != SecurityUtil.getAccessToken(request)) {
            return true;
        }
        if (null != SecurityUtil.getSecretToken(request)) {
            return true;
        }
        return super.containsContext(request);
    }
}
