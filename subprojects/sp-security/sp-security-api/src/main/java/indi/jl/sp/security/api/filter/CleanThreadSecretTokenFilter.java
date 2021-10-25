package indi.jl.sp.security.api.filter;

import indi.jl.sp.security.api.SpAuthentication;

import javax.servlet.*;
import java.io.IOException;

public class CleanThreadSecretTokenFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        SpAuthentication.cleanSecretToken();
        chain.doFilter(request, response);
    }
}
