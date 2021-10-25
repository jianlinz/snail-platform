package indi.jl.sp.security.handler.defaults.login;

import indi.jl.sp.core.vo.DataVO;
import indi.jl.sp.security.api.handler.LogoutSuccessHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public class DefaultLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public ResponseEntity<DataVO> handle(HttpServletRequest request) {
        return new ResponseEntity<>(new DataVO("注销成功"), HttpStatus.OK);
    }
}
