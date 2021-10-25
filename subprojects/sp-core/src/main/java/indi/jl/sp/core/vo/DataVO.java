package indi.jl.sp.core.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 跟前端交互的统一对象
 * T泛型 data内容必须为序列化内容
 */
public class DataVO<T> implements Serializable {

    public DataVO() {
    }

    public DataVO(T content) {
        this.content = content;
    }

    public DataVO(String errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    /**
     * 结果集
     */
    @JsonSerialize
    @ApiModelProperty("结果集")
    private T content;

    /**
     * 异常编码
     */
    @ApiModelProperty("异常编码 " +
            " 错误产生来源分为 A/B/C, 1.A 表示错误来源于用户，比如参数错误，用户安装版本过低，用户支付\n" +
            " 超时等问题；2.B 表示错误来源于当前系统，往往是业务逻辑出错，或程序健壮性差等问题；3.C 表示错误来源\n" +
            " 于第三方服务，比如 CDN 服务出错，消息投递超时等问题；五位数字编号从 00000 到 99999")
    private String errCode;

    /**
     * 异常消息
     */
    @ApiModelProperty("异常消息")
    private String errMsg;

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
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
