package indi.jl.sp.auth.jpa.domain;

import indi.jl.sp.data.jpa.domain.BaseDo;
import indi.jl.sp.core.util.JsonUtil;

import javax.persistence.*;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "sp_auth_menu")
public class AuthMenuDo extends BaseDo {

    public AuthMenuDo() {
    }

    @Column(length = 20)
    @ApiModelProperty("菜单名称")
    private String name;

    @Column(length = 200)
    @ApiModelProperty("前端路由")
    private String route;

    @Column
    @ApiModelProperty("父菜单Id")
    private Long parentId;

    @Column
    @ApiModelProperty("同级排序")
    private Integer sequenceNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    @Override
    public String toString() {
        return JsonUtil.toJsonString(this);
    }
}