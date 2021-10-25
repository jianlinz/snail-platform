package indi.jl.sp.data.redis.generator;

import indi.jl.sp.core.util.JsonUtil;
import indi.jl.sp.data.core.annotation.CacheQuery;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class CacheKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object o, Method method, Object... params) {
        Annotation[] annotations = method.getAnnotations();
        Class[] caches = null;
        for (Annotation annotation : annotations) {
            if (annotation instanceof CacheQuery) {
                caches = ((CacheQuery) annotation).caches();
            }
        }
        StringBuilder generateKey = new StringBuilder();
        generateKey.append(o.getClass().getName()).append("_");
        if (null != caches) {
            for (Class c : caches) {
                generateKey.append(c.getName()).append("_");
            }
        }
        generateKey.append(method.getName());
        for (Object param : params) {
            if (null == param) {
                continue;
            }
            generateKey.append(".").append(param.getClass().getSimpleName()).append(JsonUtil.toJsonString(param));
        }
        return generateKey.toString();
    }
}
