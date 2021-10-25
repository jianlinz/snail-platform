package indi.jl.sp.data.jpa;

import indi.jl.sp.data.jpa.repository.SpJpaRepositoryBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = {"indi","com"})
@EnableJpaRepositories(
        basePackages = {"indi","com"},
        repositoryFactoryBeanClass = SpJpaRepositoryBean.class)
@PropertySource("classpath:/application-jpa.properties")
@EnableConfigurationProperties(SpJpaProperties.class)
public class SpJpaConfiguration {

    @Bean
    public String a() {
        return "dadad";
    }

}
