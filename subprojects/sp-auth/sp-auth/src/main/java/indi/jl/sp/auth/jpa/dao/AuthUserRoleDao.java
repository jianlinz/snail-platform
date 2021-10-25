package indi.jl.sp.auth.jpa.dao;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.EntityPathBase;
import indi.jl.sp.auth.jpa.domain.AuthRoleDo;
import indi.jl.sp.auth.jpa.domain.QAuthRoleDo;
import indi.jl.sp.data.jpa.annotation.SpDao;
import indi.jl.sp.data.jpa.dao.BaseDao;
import indi.jl.sp.auth.jpa.domain.AuthUserRoleDo;
import indi.jl.sp.auth.jpa.domain.QAuthUserRoleDo;
import indi.jl.sp.data.jpa.querydsl.query.QueryDsl;
import indi.jl.sp.data.jpa.repository.AbstractJpaRepository;
import indi.jl.sp.auth.jpa.repository.AuthUserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpDao
public class AuthUserRoleDao extends BaseDao<AuthUserRoleDo> {

    private final AuthUserRoleRepository authUserRoleRepository;

    @Autowired
    public AuthUserRoleDao(AuthUserRoleRepository authUserRoleRepository) {
        this.authUserRoleRepository = authUserRoleRepository;
    }

    @Override
    protected AbstractJpaRepository<AuthUserRoleDo, Long> getRepository() {
        return authUserRoleRepository;
    }

    @Override
    protected EntityPathBase<AuthUserRoleDo> getEntityPathBase() {
        return QAuthUserRoleDo.authUserRoleDo;
    }

    public List<AuthRoleDo> findRolesByUserId(Long userId) {
        return QueryDsl.getQuery()
                .select(Projections.fields(AuthRoleDo.class,
                        QAuthRoleDo.authRoleDo.id,
                        QAuthRoleDo.authRoleDo.name))
                .from(QAuthUserRoleDo.authUserRoleDo)
                .leftJoin(QAuthRoleDo.authRoleDo)
                .on(QAuthUserRoleDo.authUserRoleDo.roleId.eq(QAuthRoleDo.authRoleDo.id))
                .where(QAuthUserRoleDo.authUserRoleDo.userId.eq(userId))
                .fetch();
    }
}