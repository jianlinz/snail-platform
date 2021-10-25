package indi.jl.sp.auth.handler;

import indi.jl.sp.auth.jpa.bo.AuthResourceRoleBo;
import indi.jl.sp.auth.jpa.domain.AuthRoleDo;
import indi.jl.sp.auth.jpa.domain.AuthUserDo;
import indi.jl.sp.auth.service.AuthRoleResourceService;
import indi.jl.sp.auth.service.AuthUserRoleService;
import indi.jl.sp.auth.service.AuthUserService;
import indi.jl.sp.core.util.CollectionUtil;
import indi.jl.sp.security.api.handler.UserDetailsHandler;
import indi.jl.sp.security.api.pojo.SecurityResource;
import indi.jl.sp.security.api.pojo.SecurityRole;
import indi.jl.sp.security.api.pojo.SecurityUserDetail;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 登录时请求的处理器
 * 需要根据username 查询用户具体信息
 * 包含 角色集合 资源集合 和扩展信息 并封装成JwtUserDetails返回
 * 默认不实现 具体认证服务器自己实现
 */
public class SpAuthUserDetailsHandler implements UserDetailsHandler {

    private final AuthUserService authUserService;

    private final AuthUserRoleService authUserRoleService;

    private final AuthRoleResourceService authRoleResourceService;

    public SpAuthUserDetailsHandler(AuthUserService authUserService,
                                    AuthUserRoleService authUserRoleService,
                                    AuthRoleResourceService authRoleResourceService) {
        this.authUserService = authUserService;
        this.authUserRoleService = authUserRoleService;
        this.authRoleResourceService = authRoleResourceService;
    }

    @Override
    public SecurityUserDetail loadUserByUsername(String username) {
        Optional<AuthUserDo> authUserDoOptional = authUserService.get(username);
        AuthUserDo authUserDo = authUserDoOptional.orElseThrow(() -> new UsernameNotFoundException(username));
        List<AuthRoleDo> authRoleDos = authUserRoleService
                .findRolesByUserId(authUserDo.getId());
        List<SecurityRole> securityRoles = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(authRoleDos)) {
            securityRoles = authRoleDos
                    .stream()
                    .map(authRoleDo -> new SecurityRole(authRoleDo.getId(), authRoleDo.getName()))
                    .collect(Collectors.toList());
        }
        List<AuthResourceRoleBo> authResourceRoleBos = authRoleResourceService
                .findResourcesByRoleIds(securityRoles.stream().map(SecurityRole::getRoleId).collect(Collectors.toList()));
        if (CollectionUtil.isNotEmpty(authResourceRoleBos)) {
            for (SecurityRole securityRole : securityRoles) {
                for (AuthResourceRoleBo authResourceRoleBo : authResourceRoleBos) {
                    if (securityRole.getRoleId().equals(authResourceRoleBo.getRoleId())) {
                        securityRole.addSecurityResource(new SecurityResource(authResourceRoleBo.getMethod(), authResourceRoleBo.getUrl()));
                    }
                }
            }
        }
        Map<String, Object> extMap = new HashMap<>();
        return new SecurityUserDetail(authUserDo.getId(),
                authUserDo.getUsername(),
                authUserDo.getPassword(),
                authUserDo.getName(),
                securityRoles,
                extMap,
                true,
                true,
                true,
                true);
    }


}
