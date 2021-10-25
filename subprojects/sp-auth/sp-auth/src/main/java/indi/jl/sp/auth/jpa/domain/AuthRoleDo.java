package indi.jl.sp.auth.jpa.domain;

import indi.jl.sp.data.jpa.domain.BaseDo;
import indi.jl.sp.core.util.JsonUtil;

import javax.persistence.*;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "sp_auth_role")
public class AuthRoleDo extends BaseDo {

    public AuthRoleDo() {
    }

    @Column(length = 20)
    @ApiModelProperty("角色名称")
    private String name;


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