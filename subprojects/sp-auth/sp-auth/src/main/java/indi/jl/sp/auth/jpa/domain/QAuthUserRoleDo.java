package indi.jl.sp.auth.jpa.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuthUserRoleDo is a Querydsl query type for AuthUserRoleDo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAuthUserRoleDo extends EntityPathBase<AuthUserRoleDo> {

    private static final long serialVersionUID = 1361275406L;

    public static final QAuthUserRoleDo authUserRoleDo = new QAuthUserRoleDo("authUserRoleDo");

    public final indi.jl.sp.data.jpa.domain.QBaseDo _super = new indi.jl.sp.data.jpa.domain.QBaseDo(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createTime = _super.createTime;

    //inherited
    public final StringPath createUserName = _super.createUserName;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final NumberPath<Long> roleId = createNumber("roleId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    //inherited
    public final StringPath updateUserName = _super.updateUserName;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QAuthUserRoleDo(String variable) {
        super(AuthUserRoleDo.class, forVariable(variable));
    }

    public QAuthUserRoleDo(Path<? extends AuthUserRoleDo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuthUserRoleDo(PathMetadata metadata) {
        super(AuthUserRoleDo.class, metadata);
    }

}

