package indi.jl.sp.core.controller;

import indi.jl.sp.core.advice.ControllerExceptionAdvice;
import indi.jl.sp.core.vo.DataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 处理Controller未包含的遗漏的异常
 */
@RestController
public class BaseErrorController implements ErrorController {

    private final ControllerExceptionAdvice controllerExceptionAdvice;

    private final ErrorAttributes errorAttributes;

    @Autowired
    public BaseErrorController(ErrorAttributes errorAttributes,
                               ControllerExceptionAdvice controllerExceptionAdvice) {
        this.errorAttributes = errorAttributes;
        this.controllerExceptionAdvice = controllerExceptionAdvice;
    }

    @Override
    public String getErrorPath() {
        return null;
    }

    @RequestMapping("/error")
    public ResponseEntity<DataVO> error(HttpServletRequest request, WebRequest webRequest) {
        HttpStatus httpStatus = getStatus(request);
        Throwable throwable = this.errorAttributes.getError(webRequest);
        ErrorAttributeOptions options = ErrorAttributeOptions.defaults();
        options.including(ErrorAttributeOptions.Include.MESSAGE);
        options.including(ErrorAttributeOptions.Include.BINDING_ERRORS);
        Map<String, Object> error = this.errorAttributes.getErrorAttributes(webRequest, options);
        if (httpStatus == HttpStatus.NO_CONTENT) {
            return new ResponseEntity<>(new DataVO<>(), httpStatus);
        }
        if (null == throwable) {
            return new ResponseEntity(new DataVO<>(error), httpStatus);
        }
        return controllerExceptionAdvice.handleException(throwable);
    }

    protected HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

}
