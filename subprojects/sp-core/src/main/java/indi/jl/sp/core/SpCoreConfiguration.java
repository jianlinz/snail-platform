package indi.jl.sp.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import indi.jl.sp.core.util.JsonUtil;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;

@Configuration
@EnableConfigurationProperties(SpCoreProperties.class)
public class SpCoreConfiguration implements WebMvcConfigurer {

    /**
     * 全局声明 jackson的objectMapper
     *
     * @return spObjectMapper
     */
    @Bean
    public ObjectMapper spObjectMapper() {
        return JsonUtil.getObjectMapper();
    }

    /**
     * 重写 mappingJackson2HttpMessageConverter 使用sp的自定义jackson序列化
     *
     * @return spMappingJackson2HttpMessageConverter
     */
    @Bean
    public MappingJackson2HttpMessageConverter spMappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter(JsonUtil.getObjectMapper());
        mappingJackson2HttpMessageConverter.getSupportedMediaTypes();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        mappingJackson2HttpMessageConverter.getSupportedMediaTypes();
        return mappingJackson2HttpMessageConverter;
    }

    /**
     * spring 文档api
     *
     * @return actuatorDocket
     */
    @Bean
    public Docket actuatorDocket() {
        return new Docket(DocumentationType.OAS_30)
                .groupName("spring")
                .select()
                .paths(PathSelectors.ant("/actuator/**"))
                .build();
    }
}
