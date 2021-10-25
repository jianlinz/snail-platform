package indi.jl.sp.auth.jpa.domain;

import indi.jl.sp.data.jpa.domain.BaseDo;
import indi.jl.sp.core.util.JsonUtil;
import javax.persistence.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "角色资源关联")
@Entity
@Table(name = "sp_auth_role_resource")
public class AuthRoleResourceDo extends BaseDo {

    public AuthRoleResourceDo(){}

    @Column
    @ApiModelProperty("角色id")
    private Long roleId;

    @Column
    @ApiModelProperty("资源id")
    private Long resourceId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public String toString() {
        return JsonUtil.toJsonString(this);
    }
}