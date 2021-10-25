package indi.jl.sp.auth.jpa.domain;

import indi.jl.sp.data.jpa.domain.BaseDo;
import indi.jl.sp.core.util.JsonUtil;

import javax.persistence.*;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "sp_auth_user")
public class AuthUserDo extends BaseDo {

    public AuthUserDo() {
    }

    @Column(length = 20)
    @ApiModelProperty("账号")
    private String username;

    @Column(length = 32)
    @ApiModelProperty("密码")
    private String password;

    @Column(length = 20)
    @ApiModelProperty("显示名称")
    private String name;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return JsonUtil.toJsonString(this);
    }
}