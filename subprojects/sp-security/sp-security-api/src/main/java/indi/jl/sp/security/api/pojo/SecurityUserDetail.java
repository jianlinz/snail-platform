package indi.jl.sp.security.api.pojo;

import indi.jl.sp.core.util.CollectionUtil;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;

public class SecurityUserDetail implements UserDetails, CredentialsContainer {


    /**
     * 人员ID
     */
    private Long id;

    /**
     * 账号
     */
    private String userName;

    /**
     * 账号
     */
    private String password;

    /**
     * 姓名
     */
    private String name;

    /**
     * 角色集合
     */
    private List<SecurityRole> roles;

    /**
     * 资源描述抽象
     */
    private Set<GrantedAuthority> authorities;

    /**
     * 扩展信息
     */
    private Map extMessage;

    /**
     * 是否在有效期 true在有效期内  false不在有效期内
     */
    private boolean accountNonExpired;

    /**
     * 是否未锁定 true 未锁定 false 锁定
     */
    private boolean accountNonLocked;

    /**
     * 密码是否在有效期  true 在 false 不在
     */
    private boolean credentialsNonExpired;

    /**
     * 是否启用 true启用 false未启用
     */
    private boolean enabled;


    public SecurityUserDetail() {

    }

    public SecurityUserDetail(Long id,
                              String username,
                              String password,
                              String name,
                              List<SecurityRole> roles,
                              Map<String, Object> extMessage,
                              boolean enabled,
                              boolean accountNonExpired,
                              boolean credentialsNonExpired,
                              boolean accountNonLocked) {

        if (((username == null) || "".equals(username)) || (password == null)) {
            throw new IllegalArgumentException(
                    "Cannot pass null or empty values to constructor");
        }
        this.id = id;
        this.name = name;
        this.userName = username;
        this.password = password;
        this.roles = roles;
        this.authorities = Collections.unmodifiableSet(sortAuthorities(roles));
        this.extMessage = extMessage;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
    }

    private static SortedSet<GrantedAuthority> sortAuthorities(
            Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
        // Ensure array iteration order is predictable (as per
        // UserDetails.getAuthorities() contract and SEC-717)
        SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<>(new AuthorityComparator());

        for (GrantedAuthority grantedAuthority : authorities) {
            Assert.notNull(grantedAuthority,
                    "GrantedAuthority list cannot contain any null elements");
            sortedAuthorities.add(grantedAuthority);
        }

        return sortedAuthorities;
    }

    private static class AuthorityComparator implements Comparator<GrantedAuthority>,
            Serializable {
        private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            // Neither should ever be null as each entry is checked before adding it to
            // the set.
            // If the authority is null, it is a custom authority and should precede
            // others.
            if (g2.getAuthority() == null) {
                return -1;
            }

            if (g1.getAuthority() == null) {
                return 1;
            }

            return g1.getAuthority().compareTo(g2.getAuthority());
        }
    }

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Map getExtMessage() {
        return extMessage;
    }

    public void setExtMessage(Map extMessage) {
        this.extMessage = extMessage;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return userName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void eraseCredentials() {
        password = null;
    }


}
