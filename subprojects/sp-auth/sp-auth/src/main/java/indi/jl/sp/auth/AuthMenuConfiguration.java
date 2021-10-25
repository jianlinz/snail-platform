package indi.jl.sp.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class AuthMenuConfiguration{

    /**
     * 声明swagger分组
     *
     * @return docket
     */
    @Bean
    public Docket authMenuDocket() {
        return new Docket(DocumentationType.OAS_30)
                .groupName("authMenu")
                .select()
                .paths(PathSelectors.ant("/authMenu/**"))
                .build();
    }

}