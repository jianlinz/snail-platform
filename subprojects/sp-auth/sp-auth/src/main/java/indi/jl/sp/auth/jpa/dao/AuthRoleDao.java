package indi.jl.sp.auth.jpa.dao;

import com.querydsl.core.types.dsl.EntityPathBase;
import indi.jl.sp.data.jpa.annotation.SpDao;
import indi.jl.sp.data.jpa.dao.BaseDao;
import indi.jl.sp.auth.jpa.domain.AuthRoleDo;
import indi.jl.sp.auth.jpa.domain.QAuthRoleDo;
import indi.jl.sp.data.jpa.repository.AbstractJpaRepository;
import indi.jl.sp.auth.jpa.repository.AuthRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

@SpDao
public class AuthRoleDao extends BaseDao<AuthRoleDo> {

    private final AuthRoleRepository authRoleRepository;

    @Autowired
    public AuthRoleDao(AuthRoleRepository authRoleRepository) {
        this.authRoleRepository = authRoleRepository;
    }
    
    @Override
    protected AbstractJpaRepository<AuthRoleDo, Long> getRepository() {
        return authRoleRepository;
    }

    @Override
    protected EntityPathBase<AuthRoleDo> getEntityPathBase() {
        return QAuthRoleDo.authRoleDo;
    }
}