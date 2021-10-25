package indi.jl.sp.core.advice;

import indi.jl.sp.core.SpCoreProperties;
import indi.jl.sp.core.constant.SecurityExceptionCodeConstant;
import indi.jl.sp.core.exception.SpBusinessException;
import indi.jl.sp.core.exception.SpSystemException;
import indi.jl.sp.core.exception.SpThirdPartyException;
import indi.jl.sp.core.vo.DataVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControllerExceptionAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionAdvice.class);

    private final SpCoreProperties spCoreProperties;

    @Autowired
    public ControllerExceptionAdvice(SpCoreProperties spCoreProperties) {
        this.spCoreProperties = spCoreProperties;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DataVO> handleException(Throwable ex) {
        if (ex instanceof SpBusinessException) {
            return handleBusinessException((SpBusinessException) ex);
        }
        if (ex instanceof SpSystemException) {
            return handleSystemException((SpSystemException) ex);
        }
        if (ex instanceof SpThirdPartyException) {
            return handleThirdPartyException((SpThirdPartyException) ex);
        }
        if (ex instanceof AuthenticationException) {
            return handleAuthenticationException((AuthenticationException) ex);
        }
        if (ex instanceof AccessDeniedException) {
            return handleAccessDeniedException((AccessDeniedException) ex);
        }
        return handleUnKnowException(ex);
    }

    private ResponseEntity<DataVO> handleBusinessException(SpBusinessException spBusinessException) {
        return new ResponseEntity<>(new DataVO<>(spBusinessException.getErrCode(), spBusinessException.getErrMsg()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<DataVO> handleSystemException(SpSystemException spSystemException) {
        LOGGER.error(SpSystemException.SERVER_ERROR_MSG, spSystemException);
        return new ResponseEntity<>(new DataVO<>(spCoreProperties.getResponseSystemCode(),
                spCoreProperties.getResponseSystemMsg()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<DataVO> handleThirdPartyException(SpThirdPartyException spThirdPartyException) {
        LOGGER.error(spThirdPartyException.getErrMsg() + spThirdPartyException.getErrCode());
        return new ResponseEntity<>(new DataVO<>(spThirdPartyException.getErrCode(), spThirdPartyException.getErrMsg()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<DataVO> handleAuthenticationException(AuthenticationException authenticationException) {
        if (authenticationException instanceof AccountExpiredException) {
            return new ResponseEntity<>(new DataVO<>(SecurityExceptionCodeConstant.TOKEN_EXPIRED, authenticationException.getMessage()),
                    HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(new DataVO<>(spCoreProperties.getResponseUnKnowCode(), authenticationException.getMessage()),
                HttpStatus.UNAUTHORIZED);
    }

    private ResponseEntity<DataVO> handleAccessDeniedException(AccessDeniedException accessDeniedException) {
        return new ResponseEntity<>(new DataVO<>(spCoreProperties.getResponseUnKnowCode(), accessDeniedException.getMessage()),
                HttpStatus.FORBIDDEN);
    }

    private ResponseEntity<DataVO> handleUnKnowException(Throwable ex) {
        LOGGER.error(spCoreProperties.getResponseUnKnowCode(), ex);
        return new ResponseEntity<>(new DataVO<>(spCoreProperties.getResponseUnKnowCode(),
                spCoreProperties.getResponseUnKnowMsg()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
