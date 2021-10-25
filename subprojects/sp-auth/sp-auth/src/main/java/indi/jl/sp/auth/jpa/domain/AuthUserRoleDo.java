package indi.jl.sp.auth.jpa.domain;

import indi.jl.sp.data.jpa.domain.BaseDo;
import indi.jl.sp.core.util.JsonUtil;

import javax.persistence.*;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "用户角色关联")
@Entity
@Table(name = "sp_auth_user_role")
public class AuthUserRoleDo extends BaseDo {

    public AuthUserRoleDo() {
    }

    @Column
    @ApiModelProperty("用户id")
    private Long userId;

    @Column
    @ApiModelProperty("角色id")
    private Long roleId;

    @Override
    public String toString() {
        return JsonUtil.toJsonString(this);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}