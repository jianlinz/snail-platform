package indi.jl.configuration;

import indi.jl.sp.core.controller.BaseController;
import indi.jl.sp.core.vo.DataVO;
import indi.jl.sp.jwt.JwtUtil;
import indi.jl.sp.jwt.enums.JwtType;
import indi.jl.sp.security.api.SecurityProperties;
import indi.jl.sp.security.api.handler.UserDetailsHandler;
import indi.jl.sp.security.api.pojo.SecurityUserDetail;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@ConditionalOnBean(UserDetailsHandler.class)
@RequestMapping("/jwt")
public class JwtControllerConfiguration extends BaseController {

    private final UserDetailsHandler userDetailsHandler;

    private final SecurityProperties securityProperties;

    public JwtControllerConfiguration(UserDetailsHandler userDetailsHandler, SecurityProperties securityProperties) {
        this.userDetailsHandler = userDetailsHandler;
        this.securityProperties = securityProperties;
    }

    @PostMapping("/token/refresh")
    @ApiOperation("根据refreshToken获得accessToken")
    public ResponseEntity<DataVO<String>> refresh(HttpServletRequest request) {
        String refreshToken = request.getHeader(securityProperties.getRefreshTokenName());
        JwtUtil.verify(refreshToken);
        if (JwtType.REFRESH != JwtUtil.getHeader(refreshToken).getTyp()) {
            throw new DisabledException("is not refresh token format");
        }
        return responseOfPost(JwtUtil.generateAccessToken(userDetailsHandler.loadUserByUsername(JwtUtil.getPayload(refreshToken).getUserName())));
    }

    @PostMapping("/token/secret")
    @ApiOperation("根据refreshToken获得accessToken")
    public ResponseEntity<DataVO<String>> secret(@RequestBody SecurityUserDetail securityUserDetail) {
        return responseOfPost(JwtUtil.generateSecretToken(securityUserDetail));
    }
}
