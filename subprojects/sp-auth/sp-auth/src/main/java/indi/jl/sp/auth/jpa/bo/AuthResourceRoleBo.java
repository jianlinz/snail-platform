package indi.jl.sp.auth.jpa.bo;

import io.swagger.annotations.ApiModelProperty;

public class AuthResourceRoleBo {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("资源名称")
    private String name;

    @ApiModelProperty("url")
    private String url;

    @ApiModelProperty(" 请求方式  GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE  支持多个 逗号分割")
    private String method;

    @ApiModelProperty("角色id")
    private Long roleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
