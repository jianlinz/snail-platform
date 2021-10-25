package indi.jl.sp.jwt.handler;

import indi.jl.sp.jwt.JwtUtil;
import indi.jl.sp.jwt.enums.JwtType;
import indi.jl.sp.jwt.pojo.JwtPayload;
import indi.jl.sp.security.api.handler.SecretTokenAuthenticationManagerHandler;
import indi.jl.sp.security.api.pojo.SecurityUserDetail;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 * token 校验管理器
 */
public class JwtSecretTokenAuthenticationManagerHandler implements SecretTokenAuthenticationManagerHandler {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getPrincipal();
        if (JwtType.SECRET != JwtUtil.getHeader(token).getTyp()) {
            throw new DisabledException("is not secret token format");
        }
        JwtUtil.verify(token);
        JwtPayload jwtPayload = JwtUtil.getPayload(token);
        SecurityUserDetail securityUserDetail = new SecurityUserDetail(jwtPayload.getUserId()
                , jwtPayload.getUserName(), "", jwtPayload.getName(),
                jwtPayload.getRoles(),
                jwtPayload.getExtMessage(),
                true,
                true,
                true,
                true);
        PreAuthenticatedAuthenticationToken preAuthenticatedAuthenticationToken = new PreAuthenticatedAuthenticationToken(securityUserDetail,
                token,
                jwtPayload.getRoles());
        preAuthenticatedAuthenticationToken.setAuthenticated(true);
        return preAuthenticatedAuthenticationToken;
    }

}
