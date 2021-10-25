package indi.jl.sp.core;

import indi.jl.sp.core.exception.SpSystemException;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sp.core")
public class SpCoreProperties {

    /**
     * 系统异常抛到前端的统一编码
     */
    private String responseSystemCode = SpSystemException.EXCEPTION_CODE_PREFIX + "00001";

    /**
     * 系统异常抛到前端的统一提示
     */
    private String responseSystemMsg = "系统繁忙";

    /**
     * 未知异常抛到前端的统一编码
     */
    private String responseUnKnowCode = SpSystemException.EXCEPTION_CODE_PREFIX + "99999";

    /**
     * 未知异常抛到前端的统一提示
     */
    private String responseUnKnowMsg = "系统繁忙";


    public String getResponseSystemMsg() {
        return responseSystemMsg;
    }

    public void setResponseSystemMsg(String responseSystemMsg) {
        this.responseSystemMsg = responseSystemMsg;
    }

    public String getResponseSystemCode() {
        return responseSystemCode;
    }

    public void setResponseSystemCode(String responseSystemCode) {
        this.responseSystemCode = responseSystemCode;
    }

    public String getResponseUnKnowCode() {
        return responseUnKnowCode;
    }

    public void setResponseUnKnowCode(String responseUnKnowCode) {
        this.responseUnKnowCode = responseUnKnowCode;
    }

    public String getResponseUnKnowMsg() {
        return responseUnKnowMsg;
    }

    public void setResponseUnKnowMsg(String responseUnKnowMsg) {
        this.responseUnKnowMsg = responseUnKnowMsg;
    }


}
