package indi.jl.configuration;

import indi.jl.sp.auth.handler.SpAuthUserDetailsHandler;
import indi.jl.sp.auth.service.AuthRoleResourceService;
import indi.jl.sp.auth.service.AuthUserRoleService;
import indi.jl.sp.auth.service.AuthUserService;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@AutoConfigureBefore(SecurityApiAutoConfiguration.class)
public class AuthConfiguration {

    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public SpAuthUserDetailsHandler spAuthUserDetailsHandler(AuthUserService authUserService,
                                                             AuthUserRoleService authUserRoleService,
                                                             AuthRoleResourceService authRoleResourceService) {
        return new SpAuthUserDetailsHandler(authUserService, authUserRoleService, authRoleResourceService);
    }

    /**
     * 声明swagger分组
     *
     * @return docket
     */
    @Bean
    public Docket authDocket() {
        return new Docket(DocumentationType.OAS_30)
                .groupName("auth")
                .select()
                .paths(PathSelectors.ant("/auth/**"))
                .build();
    }

}