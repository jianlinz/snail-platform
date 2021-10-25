package indi.jl.sp.cloud.feign;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/application-feign.properties")
public class CloudFeignConfiguration {

}