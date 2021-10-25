package indi.jl.sp.data.jpa;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SpJpaProperties.class)
public class SpLiquibaseConfiguration {

}
