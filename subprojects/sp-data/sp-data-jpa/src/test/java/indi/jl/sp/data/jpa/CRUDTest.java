package indi.jl.sp.data.jpa;

import indi.jl.sp.data.jpa.domain.JpaTestT;
import indi.jl.sp.data.jpa.service.JpaTestService;
import indi.jl.sp.test.common.BaseSpringTest;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;

import java.util.UUID;

public class CRUDTest extends BaseSpringTest {

    @Autowired
    private JpaTestService jpaTestService;

    @Test
    public void crud() {
        JpaTestT jpaTestT = new JpaTestT();
        jpaTestT.setName("test");
        jpaTestService.save(jpaTestT);
        assert "test".equals(jpaTestService.get(jpaTestT.getId()).orElse(null).getName());
        jpaTestT.setName("test2");
        jpaTestT = jpaTestService.update(jpaTestT);
        assert "test2".equals(jpaTestService.get(jpaTestT.getId()).orElse(null).getName());
        jpaTestService.delete(jpaTestT.getId());
        assert !jpaTestService.findByName("test2").isPresent();
    }

    //测试事务回滚
    @Test
    public void testTx() {
        String name = UUID.randomUUID().toString();
        JpaTestT jpaTestT = new JpaTestT();
        jpaTestT.setName(name);
        try {
            jpaTestService.saveTestTx(jpaTestT);
        } catch (Exception e) {
            assert !jpaTestService.findByName(name).isPresent();
        }
        assert !jpaTestService.findByName(name).isPresent();
    }

    //测试只读事务无法写入
    @Test
    public void readOnlyTx() {
        JpaTestT jpaTestT = new JpaTestT();
        jpaTestT.setName("readOnlyTx");
        try {
            jpaTestService.readOnlyTx(jpaTestT);
        } catch (JpaSystemException e) {
            assert !jpaTestService.findByName("readOnlyTx").isPresent();
        }
        assert !jpaTestService.findByName("readOnlyTx").isPresent();
    }

    //测试缓存
    @Test
    public void cache() {
        String name = UUID.randomUUID().toString();
        JpaTestT jpaTestT = new JpaTestT();
        jpaTestT.setName(name);
        jpaTestT.setTestName("ImTest");
        jpaTestService.save(jpaTestT);
        //打印sql
        assert name.equals(jpaTestService.findByNameCache(name).orElse(null).getName());
        //不打印sql
        assert name.equals(jpaTestService.findByNameCache(name).orElse(null).getName());
        //打印sql
        assert null == jpaTestService.findByNameCache("testTx1").orElse(null);

        try {
            jpaTestT.setTestName("testCacheChange");
            jpaTestService.saveTestTx(jpaTestT);
        } catch (Exception e) {
            //不打印sql
            assert "ImTest".equals(jpaTestService.findByNameCache(name).orElse(null).getTestName());
        }
        jpaTestService.update(jpaTestT);
        //打印sql
        assert "testCacheChange".equals(jpaTestService.findByNameCache(name).orElse(null).getTestName());

        jpaTestService.delete(jpaTestT.getId());
        //不打印sql
        assert null == jpaTestService.findByNameCache("testTx1").orElse(null);

    }


    @Test
    @Ignore
    public void cachePerformance() {
        JpaTestT jpaTestT = new JpaTestT();
        jpaTestT.setName("testTx");
        jpaTestT.setTestName("ImTest");
        jpaTestService.save(jpaTestT);
        jpaTestService.findByNameCache("testTx").orElse(null).getName();
        Long start = System.currentTimeMillis();
        for (int i = 0; i < 50000; i++) {
            jpaTestService.findByNameCache("testTx").orElse(null).getName();
        }
        Long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
