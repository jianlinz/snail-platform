package indi.jl.sp.core.exception;

/**
 * 不可控制的异常 如:非空校验后抛出异常 或 异步访问产生的不可控异常 和一些不可控的IO异常但是并不想声明抛出向上传递
 * 框架会将堆栈信息统一打印log
 * 前端响应500  消息内容默认为系统异常 或者通过${sp.core。response-system-msg}进行配置
 * 默认异常码B_00001 当前系统异常
 */
public class SpSystemException extends RuntimeException {

    /**
     * 异常前缀
     * 错误产生来源分为 A/B/C，
     * A 表示错误来源于用户，比如参数错误，用户安装版本过低，用户支付超时等问题；
     * B 表示错误来源于当前系统，往往是业务逻辑出错，或程序健壮性差等问题；
     * C 表示错误来源于第三方服务，比如 CDN 服务出错，消息投递超时等问题；四位数字编号从 00001 到 99999
     */
    public static final String EXCEPTION_CODE_PREFIX = "B_";

    public static final String SERVER_ERROR_MSG = "系统异常";

    public SpSystemException(String message) {
        super(message);
    }

    public SpSystemException(Throwable cause) {
        super(cause);
    }


    public SpSystemException(String message, Throwable cause) {
        super(message, cause);
    }
}
