package indi.jl.sp.jwt.handler;

import indi.jl.sp.core.vo.DataVO;
import indi.jl.sp.jwt.JwtUtil;
import indi.jl.sp.jwt.pojo.TokenVo;
import indi.jl.sp.security.api.SecurityProperties;
import indi.jl.sp.security.api.handler.LoginSuccessHandler;
import indi.jl.sp.security.api.pojo.SecurityUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.MultiValueMap;

public class JwtLoginSuccessHandler implements LoginSuccessHandler {

    private final SecurityProperties securityProperties;

    @Autowired
    public JwtLoginSuccessHandler(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    public ResponseEntity<DataVO> handle(Authentication authentication) {
        String accessToken = JwtUtil.generateAccessToken((SecurityUserDetail) authentication.getPrincipal());
        String refreshToken = JwtUtil.generateRefreshToken((SecurityUserDetail) authentication.getPrincipal());
        MultiValueMap<String, String> responseHeader = new HttpHeaders();
        responseHeader.add(securityProperties.getAccessTokenName(), accessToken);
        responseHeader.add(securityProperties.getRefreshTokenName(), refreshToken);
        return new ResponseEntity<>(new DataVO(new TokenVo(accessToken, refreshToken)), responseHeader, HttpStatus.OK);
    }
}
