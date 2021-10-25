package indi.jl.sp.auth.jpa.dao;

import com.querydsl.core.types.dsl.EntityPathBase;
import indi.jl.sp.data.jpa.annotation.SpDao;
import indi.jl.sp.data.jpa.dao.BaseDao;
import indi.jl.sp.auth.jpa.domain.AuthResourceDo;
import indi.jl.sp.auth.jpa.domain.QAuthResourceDo;
import indi.jl.sp.data.jpa.repository.AbstractJpaRepository;
import indi.jl.sp.auth.jpa.repository.AuthResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;

@SpDao
public class AuthResourceDao extends BaseDao<AuthResourceDo> {

    private final AuthResourceRepository authResourceRepository;

    @Autowired
    public AuthResourceDao(AuthResourceRepository authResourceRepository) {
        this.authResourceRepository = authResourceRepository;
    }
    
    @Override
    protected AbstractJpaRepository<AuthResourceDo, Long> getRepository() {
        return authResourceRepository;
    }

    @Override
    protected EntityPathBase<AuthResourceDo> getEntityPathBase() {
        return QAuthResourceDo.authResourceDo;
    }
}