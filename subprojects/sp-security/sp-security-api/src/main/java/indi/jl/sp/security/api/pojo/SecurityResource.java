package indi.jl.sp.security.api.pojo;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.web.bind.annotation.RequestMethod;

public class SecurityResource implements ConfigAttribute {

    public SecurityResource() {
    }

    public SecurityResource(String method, String url) {
        this.method = method;
        this.url = url;
    }

    /**
     * 请求方式  GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE
     * 支持多个 逗号分割
     */
    private String method;

    /**
     * 请求地址
     */
    private String url;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getAttribute() {
        return this.method + ":" + this.url;
    }

}
