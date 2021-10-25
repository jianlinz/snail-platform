package indi.jl.sp.auth.jpa.dao;

import com.querydsl.core.types.dsl.EntityPathBase;
import indi.jl.sp.data.jpa.annotation.SpDao;
import indi.jl.sp.data.jpa.dao.BaseDao;
import indi.jl.sp.auth.jpa.domain.AuthUserDo;
import indi.jl.sp.auth.jpa.domain.QAuthUserDo;
import indi.jl.sp.data.jpa.querydsl.query.QueryDsl;
import indi.jl.sp.data.jpa.repository.AbstractJpaRepository;
import indi.jl.sp.auth.jpa.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@SpDao
public class AuthUserDao extends BaseDao<AuthUserDo> {

    private final AuthUserRepository authUserRepository;

    @Autowired
    public AuthUserDao(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }

    @Override
    protected AbstractJpaRepository<AuthUserDo, Long> getRepository() {
        return authUserRepository;
    }

    @Override
    protected EntityPathBase<AuthUserDo> getEntityPathBase() {
        return QAuthUserDo.authUserDo;
    }


    public Optional<AuthUserDo> get(String username) {
        return Optional.ofNullable(QueryDsl
                .getQuery()
                .selectFrom(QAuthUserDo.authUserDo)
                .where(QAuthUserDo.authUserDo.username.eq(username))
                .fetchOne());
    }
}