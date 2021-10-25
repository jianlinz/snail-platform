package indi.jl.sp.data.jpa.dao;

import com.querydsl.core.types.dsl.EntityPathBase;
import indi.jl.sp.data.jpa.annotation.SpDao;
import indi.jl.sp.data.jpa.domain.JpaTestT;
import indi.jl.sp.data.jpa.domain.QJpaTestT;
import indi.jl.sp.data.jpa.repository.AbstractJpaRepository;
import indi.jl.sp.data.jpa.repository.JpaTestTRepository;
import org.springframework.beans.factory.annotation.Autowired;

@SpDao
public class JpaTestDao extends BaseDao<JpaTestT> {

    @Autowired
    private JpaTestTRepository jpaTestTRepository;

    @Override
    protected AbstractJpaRepository<JpaTestT, Long> getRepository() {
        return jpaTestTRepository;
    }

    @Override
    protected EntityPathBase<JpaTestT> getEntityPathBase() {
        return QJpaTestT.jpaTestT;
    }
}
