package indi.jl.sp.auth.jpa.dao;

import com.querydsl.core.types.dsl.EntityPathBase;
import indi.jl.sp.data.jpa.annotation.SpDao;
import indi.jl.sp.data.jpa.dao.BaseDao;
import indi.jl.sp.auth.jpa.domain.AuthMenuDo;
import indi.jl.sp.auth.jpa.domain.QAuthMenuDo;
import indi.jl.sp.data.jpa.repository.AbstractJpaRepository;
import indi.jl.sp.auth.jpa.repository.AuthMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;

@SpDao
public class AuthMenuDao extends BaseDao<AuthMenuDo> {

    private final AuthMenuRepository authMenuRepository;

    @Autowired
    public AuthMenuDao(AuthMenuRepository authMenuRepository) {
        this.authMenuRepository = authMenuRepository;
    }
    
    @Override
    protected AbstractJpaRepository<AuthMenuDo, Long> getRepository() {
        return authMenuRepository;
    }

    @Override
    protected EntityPathBase<AuthMenuDo> getEntityPathBase() {
        return QAuthMenuDo.authMenuDo;
    }
}