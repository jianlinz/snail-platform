package indi.jl.sp.security.api.handler;

import indi.jl.sp.core.vo.DataVO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface LogoutSuccessHandler {

    ResponseEntity<DataVO> handle(HttpServletRequest request);
}
