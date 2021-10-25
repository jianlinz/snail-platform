package indi.jl.sp.security.api.handler;

import indi.jl.sp.core.vo.DataVO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface LoginSuccessHandler {

    ResponseEntity<DataVO> handle(Authentication authentication);
}
