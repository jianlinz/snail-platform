package indi.jl.sp.security.api.handler;

import indi.jl.sp.core.vo.DataVO;
import org.springframework.http.ResponseEntity;

public interface LoginFailHandler {

    ResponseEntity<DataVO> handle(Throwable throwable);
}
