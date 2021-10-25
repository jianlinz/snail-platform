package indi.jl.sp.test.exception;

import indi.jl.sp.core.exception.SpSystemException;

/**
 * 单元测试模拟前端异常
 */
public class ResponseErrorExceptionSp extends SpSystemException {
    public ResponseErrorExceptionSp(String message) {
        super(message);
    }
}
