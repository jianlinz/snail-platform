package indi.jl.sp.data.redis;

import indi.jl.sp.test.common.BaseSpringTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HashOperationsTest extends BaseSpringTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void testExpire() throws InterruptedException {
        redisTemplate.opsForHash().put("abc", "abc", "123");
        redisTemplate.opsForValue().set("ccc", "123123");
        redisTemplate.expire("abc", 5000, TimeUnit.MILLISECONDS);
        redisTemplate.expire("ccc", 5000, TimeUnit.MILLISECONDS);
        Thread.sleep(4000);
        redisTemplate.opsForHash().put("abc", "bcd", "456");
        redisTemplate.expire("abc", 5000, TimeUnit.MILLISECONDS);
        redisTemplate.opsForValue().set("ccc", "123123");
        Thread.sleep(4000);
        Map o = redisTemplate.opsForHash().entries("abc");
        assert null != o;
        String ccc = (String) redisTemplate.opsForValue().get("ccc");
        assert "123123".equals(ccc);
        Thread.sleep(4000);
        String cccForever = (String) redisTemplate.opsForValue().get("ccc");
        assert "123123".equals(cccForever);
        redisTemplate.delete("ccc");
    }

    @Test
    public void refreshTTL() throws InterruptedException {
        T t = new T();
        t.setA("a");
        t.setB("b");

        redisTemplate.opsForHash().put("a", "a", t);
        redisTemplate.opsForHash().put("a", "b", t);
        redisTemplate.expire("a", 3, TimeUnit.SECONDS);
        T t1 = (T) redisTemplate.opsForHash().get("a", "a");
        if (!"b".equals(t1.getB())) throw new AssertionError();
        Thread.sleep(2000);
        //refreshTTL
        redisTemplate.expire("a", 3, TimeUnit.SECONDS);
        Thread.sleep(2000);
        redisTemplate.expire("a", 3, TimeUnit.SECONDS);
        Thread.sleep(2000);
        assert null != redisTemplate.opsForHash().get("a", "a");
        Thread.sleep(3000);
        assert null == redisTemplate.opsForHash().get("a", "a");
    }


}
