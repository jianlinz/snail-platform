package indi.jl.sp.data.jpa.service.impl;

import indi.jl.sp.core.exception.SpSystemException;
import indi.jl.sp.data.jpa.dao.JpaTestDao;
import indi.jl.sp.data.jpa.domain.JpaTestT;
import indi.jl.sp.data.jpa.querydsl.query.QueryDsl;
import indi.jl.sp.data.jpa.service.JpaTestService;
import indi.jl.sp.data.core.annotation.CacheQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JpaTestServiceImpl implements JpaTestService {

    @Autowired
    private JpaTestDao jpaTestDao;

    @Override
    public JpaTestT save(JpaTestT jpaTestT) {
        return jpaTestDao.save(jpaTestT);
    }

    @Override
    public JpaTestT saveTestTx(JpaTestT jpaTestT) {
        this.save(jpaTestT);
        throw new SpSystemException("test tx");
    }

    @Override
    public JpaTestT readOnlyTx(JpaTestT jpaTestT) {
        return this.save(jpaTestT);
    }

    @Override
    public JpaTestT update(JpaTestT jpaTestT) {
        return jpaTestDao.update(jpaTestT);
    }

    @Override
    public void delete(Long id) {
        jpaTestDao.deleteById(id);
    }

    @Override
    public Optional<JpaTestT> get(Long id) {
        return jpaTestDao.getById(id);
    }

    @Override
    public Optional<JpaTestT> findByName(String name) {
        return jpaTestDao.get(new JpaTestT().setName(name));
    }

    @Override
    @CacheQuery(caches = JpaTestT.class)
    public Optional<JpaTestT> findByNameCache(String name) {
        return jpaTestDao.get(new JpaTestT().setName(name));
    }

    @Override
    public List<JpaTestT> findAll() {
        return jpaTestDao.findAll();
    }
}
