package indi.jl.sp.data.redis.service.impl;

import indi.jl.sp.data.core.constant.CacheConstant;
import indi.jl.sp.data.core.service.CacheRemoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Primary
@Service
public class RedisCacheRemoveServiceImpl implements CacheRemoveService {

    @Autowired
    private RedisTemplate redisTemplate;

    public void cleanCache(String doClassName) {
        redisTemplate.delete(redisTemplate.keys(CacheConstant.CACHE_DURATION_HOUR + "*" + doClassName + "*"));
    }
}
