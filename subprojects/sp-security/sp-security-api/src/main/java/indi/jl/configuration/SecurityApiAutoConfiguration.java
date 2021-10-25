package indi.jl.configuration;

import indi.jl.sp.security.api.SecurityProperties;
import indi.jl.sp.security.api.filter.CleanThreadSecretTokenFilter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityApiAutoConfiguration {

    @Bean
    public FilterRegistrationBean cleanThreadSecretTokenFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new CleanThreadSecretTokenFilter());
        registration.addUrlPatterns("/*");
        registration.setName("cleanThreadSecretTokenFilter");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }
}