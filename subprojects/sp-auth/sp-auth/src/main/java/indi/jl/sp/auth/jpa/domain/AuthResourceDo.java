package indi.jl.sp.auth.jpa.domain;

import indi.jl.sp.data.jpa.domain.BaseDo;
import indi.jl.sp.core.util.JsonUtil;

import javax.persistence.*;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "资源")
@Entity
@Table(name = "sp_auth_resource")
public class AuthResourceDo extends BaseDo {

    public AuthResourceDo() {
    }

    @Column(length = 20, nullable = false)
    @ApiModelProperty("资源名称")
    private String name;

    @Column(length = 100, nullable = false)
    @ApiModelProperty("url")
    private String url;

    @Column(nullable = false)
    @ApiModelProperty(" 请求方式  GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE  支持多个 逗号分割")
    private String method;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return JsonUtil.toJsonString(this);
    }
}