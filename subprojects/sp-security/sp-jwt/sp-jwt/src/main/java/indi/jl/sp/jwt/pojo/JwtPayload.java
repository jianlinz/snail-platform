package indi.jl.sp.jwt.pojo;

import indi.jl.sp.core.util.CollectionUtil;
import indi.jl.sp.jwt.enums.JwtType;
import indi.jl.sp.security.api.pojo.SecurityResource;
import indi.jl.sp.security.api.pojo.SecurityRole;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * JWT规范 第二段自定义信息结构 注意扩展信息中禁止放敏感信息和过大的信息
 * 如果放入敏感信息如密码 会有泄露风险
 * 如果放入过大信息会导致请求头过大
 */
public class JwtPayload implements Serializable {

    public JwtPayload() {
    }

    public JwtPayload(String userName) {
        this.userName = userName;
    }

    /**
     * 过期时间
     */
    private LocalDateTime exp;

    /**
     * jwt类型
     */
    private JwtType jwtType;

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 账号
     */
    private String userName;

    /**
     * 用户显示名
     */
    private String name;

    /**
     * 角色集合
     */
    private List<SecurityRole> roles;

    /**
     * 扩展信息
     */
    private Map<String, Object> extMessage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<SecurityRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SecurityRole> roles) {
        this.roles = roles;
    }

    public List<SecurityResource> getResources() {
        if (CollectionUtil.isEmpty(this.roles)) {
            return new ArrayList<>();
        }
        List<SecurityResource> securityResources = new ArrayList<>();
        for (SecurityRole securityRole : this.roles) {
            if (CollectionUtil.isNotEmpty(securityRole.getResources())) {
                securityResources.addAll(securityRole.getResources());
            }
        }
        return securityResources;
    }

    public Map<String, Object> getExtMessage() {
        return extMessage;
    }

    public void setExtMessage(Map<String, Object> extMessage) {
        this.extMessage = extMessage;
    }

    public LocalDateTime getExp() {
        return exp;
    }

    public void setExp(LocalDateTime exp) {
        this.exp = exp;
    }
}
