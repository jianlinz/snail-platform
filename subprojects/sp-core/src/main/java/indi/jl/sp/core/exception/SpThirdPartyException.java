package indi.jl.sp.core.exception;

public class SpThirdPartyException extends RuntimeException {

    /**
     * 异常前缀
     * 错误产生来源分为 A/B/C，
     * A 表示错误来源于用户，比如参数错误，用户安装版本过低，用户支付超时等问题；
     * B 表示错误来源于当前系统，往往是业务逻辑出错，或程序健壮性差等问题；
     * C 表示错误来源于第三方服务，比如 CDN 服务出错，消息投递超时等问题；四位数字编号从 00001 到 99999
     */
    private static final String EXCEPTION_CODE_PREFIX = "C_";
    /**
     * 异常编码
     * 五位数字编号从 00000 到 99999
     */
    private String errCode;

    /**
     * 前端需要抛出的异常消息
     */
    private String errMsg;

    public SpThirdPartyException(String errCode, String errMsg) {
        super();
        this.errCode = EXCEPTION_CODE_PREFIX + errCode;
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
