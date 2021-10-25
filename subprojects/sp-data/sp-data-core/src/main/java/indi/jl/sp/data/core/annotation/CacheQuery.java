package indi.jl.sp.data.core.annotation;

import indi.jl.sp.data.core.constant.CacheConstant;
import org.springframework.cache.annotation.Cacheable;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Cacheable(cacheNames = CacheConstant.CACHE_DURATION_HOUR,
        keyGenerator = "cacheKeyGenerator",
        cacheManager = CacheConstant.CACHE_DURATION_HOUR)
public @interface CacheQuery {
    Class[] caches();
}
