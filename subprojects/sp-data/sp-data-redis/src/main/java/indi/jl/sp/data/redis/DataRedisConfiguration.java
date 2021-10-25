package indi.jl.sp.data.redis;

import indi.jl.sp.core.util.JsonUtil;
import indi.jl.sp.data.core.constant.CacheConstant;
import indi.jl.sp.data.redis.generator.CacheKeyGenerator;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;


@Configuration
@EnableCaching
@PropertySource("classpath:/sp-redis.properties")
public class DataRedisConfiguration {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory, ResourceLoader resourceLoader) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer(resourceLoader.getClassLoader()));
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer(JsonUtil.getObjectMapperWithJavaType()));
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean(name = "cacheKeyGenerator")
    public CacheKeyGenerator cacheKeyGenerator() {
        return new CacheKeyGenerator();
    }


    @Primary
    @Bean(CacheConstant.CACHE_DURATION_HOUR)
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        config = config.serializeValuesWith(RedisSerializationContext.SerializationPair
                .fromSerializer(new GenericJackson2JsonRedisSerializer(JsonUtil.getObjectMapperWithJavaType())));
        config = config.entryTtl(Duration.ofHours(1L));
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager
                .builder(redisConnectionFactory)
                .cacheDefaults(config);
        List<String> cacheNames = new ArrayList<>();
        cacheNames.add(CacheConstant.CACHE_DURATION_HOUR);
        builder.initialCacheNames(new LinkedHashSet<>(cacheNames));
        return builder.build();
    }

    /**
     * 声明swagger分组
     *
     * @return docket
     */
    @Bean
    public Docket dataRedisDocket() {
        return new Docket(DocumentationType.OAS_30)
                .groupName("dataRedis")
                .select()
                .paths(PathSelectors.ant("/dataRedis/**"))
                .build();
    }

}