package indi.jl.sp.security.controller;

import indi.jl.sp.core.controller.BaseController;
import indi.jl.sp.core.exception.SpSystemException;
import indi.jl.sp.core.vo.DataVO;
import indi.jl.sp.security.api.SpAuthentication;
import indi.jl.sp.security.api.handler.LoginFailHandler;
import indi.jl.sp.security.api.handler.LoginSuccessHandler;
import indi.jl.sp.security.api.handler.LogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.WebAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理Controller未包含的遗漏的异常
 */
@RestController
public class SecurityForwardUrlController extends BaseController {

    private final LoginSuccessHandler loginSuccessHandler;

    private final LoginFailHandler loginFailHandler;

    private final LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    public SecurityForwardUrlController(
            LoginSuccessHandler loginSuccessHandler,
            LoginFailHandler loginFailHandler,
            LogoutSuccessHandler logoutSuccessHandler) {
        this.loginSuccessHandler = loginSuccessHandler;
        this.loginFailHandler = loginFailHandler;
        this.logoutSuccessHandler = logoutSuccessHandler;
    }

    @PostMapping("/login/success")
    public ResponseEntity<DataVO> success() {
        if (null == SpAuthentication.getAuthentication()) {
            throw new SpSystemException("error request /login/success");
        }
        return loginSuccessHandler.handle(SpAuthentication.getAuthentication());
    }

    @PostMapping("/login/fail")
    public ResponseEntity<DataVO> fail(HttpServletRequest request) {
        Throwable throwable = (Throwable) request.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        if (null == throwable) {
            return new ResponseEntity(new DataVO<>("login unknow error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return loginFailHandler.handle(throwable);
    }

    @GetMapping("/logout/success")
    public ResponseEntity<DataVO> logoutSuccess(HttpServletRequest request) {
        return logoutSuccessHandler.handle(request);
    }
}
