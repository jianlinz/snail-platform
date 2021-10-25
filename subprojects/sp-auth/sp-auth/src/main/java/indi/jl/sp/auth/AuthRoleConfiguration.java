package indi.jl.sp.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class AuthRoleConfiguration{

    /**
     * 声明swagger分组
     *
     * @return docket
     */
    @Bean
    public Docket authRoleDocket() {
        return new Docket(DocumentationType.OAS_30)
                .groupName("authRole")
                .select()
                .paths(PathSelectors.ant("/authRole/**"))
                .build();
    }

}