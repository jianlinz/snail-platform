package indi.jl.sp.security.handler.defaults.login;

import indi.jl.sp.core.advice.ControllerExceptionAdvice;
import indi.jl.sp.core.vo.DataVO;
import indi.jl.sp.security.api.handler.LoginFailHandler;
import org.springframework.http.ResponseEntity;

public class DefaultLoginFailureHandler implements LoginFailHandler {

    private final ControllerExceptionAdvice controllerExceptionAdvice;

    public DefaultLoginFailureHandler(ControllerExceptionAdvice controllerExceptionAdvice) {
        this.controllerExceptionAdvice = controllerExceptionAdvice;
    }

    @Override
    public ResponseEntity<DataVO> handle(Throwable throwable) {
        return controllerExceptionAdvice.handleException(throwable);
    }
}
