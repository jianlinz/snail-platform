package indi.jl.sp.security.api.filter;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;

public class RequestHeaderOrParamSecretTokenFilter extends AbstractPreAuthenticatedProcessingFilter {

    private String principalRequestHeader = "SM_USER";

    private String principalRequestParam = "SM_USER";

    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        String principal = request.getHeader(principalRequestHeader);
        if (null != principal) {
            return principal;
        }
        principal = request.getParameter(principalRequestParam);
        return principal;
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "N/A";
    }

    public String getPrincipalRequestParam() {
        return principalRequestParam;
    }

    public void setPrincipalRequestParam(String principalRequestParam) {
        this.principalRequestParam = principalRequestParam;
    }

    public String getPrincipalRequestHeader() {
        return principalRequestHeader;
    }

    public void setPrincipalRequestHeader(String principalRequestHeader) {
        this.principalRequestHeader = principalRequestHeader;
    }
}
