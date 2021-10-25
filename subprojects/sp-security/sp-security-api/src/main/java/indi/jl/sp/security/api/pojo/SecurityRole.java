package indi.jl.sp.security.api.pojo;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SecurityRole implements GrantedAuthority, Serializable {

    public SecurityRole() {
    }

    public SecurityRole(Long roleId, String name) {
        this.roleId = roleId;
        this.name = name;
    }

    /**
     * 角色Id
     */
    private Long roleId;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 资源
     */
    private List<SecurityResource> resources;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    public List<SecurityResource> getResources() {
        return resources;
    }

    public void setResources(List<SecurityResource> resources) {
        this.resources = resources;
    }

    public void addSecurityResource(SecurityResource securityResource) {
        if (null == this.resources) {
            this.resources = new ArrayList<>();
        }
        this.resources.add(securityResource);
    }
}
