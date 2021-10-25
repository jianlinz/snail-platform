package indi.jl.sp.core.exception;

/**
 * 用于只关心错误消息，不关心异常堆栈等信息的场景
 * 前台会封装异常响应500  将message 抛到前台
 * 可以理解为业务异常
 */
public class SpBusinessException extends RuntimeException {

    /**
     * 异常前缀
     * 错误产生来源分为 A/B/C，
     * A 表示错误来源于用户，比如参数错误，用户安装版本过低，用户支付超时等问题；
     * B 表示错误来源于当前系统，往往是业务逻辑出错，或程序健壮性差等问题；
     * C 表示错误来源于第三方服务，比如 CDN 服务出错，消息投递超时等问题；四位数字编号从 0001 到 9999
     */
    private static final String EXCEPTION_CODE_PREFIX = "A_";
    /**
     * 异常编码
     * 五位数字编号从 00000 到 99999
     */
    private String errCode;

    /**
     * 前端需要抛出的异常消息
     */
    private String errMsg;

    public SpBusinessException(String errCode, String errMsg) {
        super();
        this.errCode = EXCEPTION_CODE_PREFIX + errCode;
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }
}
