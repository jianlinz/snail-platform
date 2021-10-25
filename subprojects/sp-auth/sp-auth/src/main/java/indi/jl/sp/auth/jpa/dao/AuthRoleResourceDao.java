package indi.jl.sp.auth.jpa.dao;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.EntityPathBase;
import indi.jl.sp.auth.jpa.bo.AuthResourceRoleBo;
import indi.jl.sp.auth.jpa.domain.*;
import indi.jl.sp.data.jpa.annotation.SpDao;
import indi.jl.sp.data.jpa.dao.BaseDao;
import indi.jl.sp.data.jpa.querydsl.query.QueryDsl;
import indi.jl.sp.data.jpa.repository.AbstractJpaRepository;
import indi.jl.sp.auth.jpa.repository.AuthRoleResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpDao
public class AuthRoleResourceDao extends BaseDao<AuthRoleResourceDo> {

    private final AuthRoleResourceRepository authRoleResourceRepository;

    @Autowired
    public AuthRoleResourceDao(AuthRoleResourceRepository authRoleResourceRepository) {
        this.authRoleResourceRepository = authRoleResourceRepository;
    }

    @Override
    protected AbstractJpaRepository<AuthRoleResourceDo, Long> getRepository() {
        return authRoleResourceRepository;
    }

    @Override
    protected EntityPathBase<AuthRoleResourceDo> getEntityPathBase() {
        return QAuthRoleResourceDo.authRoleResourceDo;
    }

    public List<AuthResourceRoleBo> findResourcesByRoleIds(List<Long> roleIds) {
        return QueryDsl.getQuery()
                .select(Projections.fields(AuthResourceRoleBo.class,
                        QAuthResourceDo.authResourceDo.id,
                        QAuthResourceDo.authResourceDo.method,
                        QAuthResourceDo.authResourceDo.name,
                        QAuthResourceDo.authResourceDo.url,
                        QAuthRoleResourceDo.authRoleResourceDo.roleId))
                .from(QAuthRoleResourceDo.authRoleResourceDo)
                .leftJoin(QAuthResourceDo.authResourceDo)
                .on(QAuthRoleResourceDo.authRoleResourceDo.resourceId.eq(QAuthResourceDo.authResourceDo.id))
                .where(QAuthRoleResourceDo.authRoleResourceDo.roleId.in(roleIds)).fetch();
    }
}