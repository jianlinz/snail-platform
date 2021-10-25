package indi.jl.configuration;

import indi.jl.sp.jwt.JwtProperties;
import indi.jl.sp.jwt.handler.JwtAccessTokenAuthenticationManagerHandler;
import indi.jl.sp.jwt.handler.JwtLoginSuccessHandler;
import indi.jl.sp.jwt.handler.JwtSecretTokenAuthenticationManagerHandler;
import indi.jl.sp.security.api.SecurityProperties;
import indi.jl.sp.security.api.handler.UserDetailsHandler;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@AutoConfigureBefore(SecurityApiAutoConfiguration.class)
@EnableConfigurationProperties(JwtProperties.class)
@PropertySource("classpath:/application-jwt.properties")
@Import(JwtControllerConfiguration.class)
public class JwtAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(JwtAccessTokenAuthenticationManagerHandler.class)
    public JwtAccessTokenAuthenticationManagerHandler jwtAuthenticationManagerHandler() {
        return new JwtAccessTokenAuthenticationManagerHandler();
    }


    @Bean
    @ConditionalOnMissingBean(JwtSecretTokenAuthenticationManagerHandler.class)
    public JwtSecretTokenAuthenticationManagerHandler jwtSecretTokenAuthenticationManagerHandler() {
        return new JwtSecretTokenAuthenticationManagerHandler();
    }

    @Bean
    @ConditionalOnMissingBean(JwtLoginSuccessHandler.class)
    public JwtLoginSuccessHandler jwtLoginSuccessHandler(SecurityProperties securityProperties) {
        return new JwtLoginSuccessHandler(securityProperties);
    }

    /**
     * 声明swagger分组
     *
     * @return docket
     */
    @Bean
    public Docket authJwtDocket() {
        return new Docket(DocumentationType.OAS_30)
                .groupName("authJwt")
                .select()
                .paths(PathSelectors.ant("/authJwt/**"))
                .build();
    }

}