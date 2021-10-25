package indi.jl.sp.security.handler.defaults.login;

import indi.jl.sp.core.vo.DataVO;
import indi.jl.sp.security.api.handler.LoginSuccessHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public class DefaultLoginSuccessHandler implements LoginSuccessHandler {

    @Override
    public ResponseEntity<DataVO> handle(Authentication authentication) {
        return new ResponseEntity<>(new DataVO("登录成功"), HttpStatus.OK);
    }
}
