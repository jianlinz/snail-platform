package indi.jl.sp.auth.jpa.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuthRoleResourceDo is a Querydsl query type for AuthRoleResourceDo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAuthRoleResourceDo extends EntityPathBase<AuthRoleResourceDo> {

    private static final long serialVersionUID = -1775426543L;

    public static final QAuthRoleResourceDo authRoleResourceDo = new QAuthRoleResourceDo("authRoleResourceDo");

    public final indi.jl.sp.data.jpa.domain.QBaseDo _super = new indi.jl.sp.data.jpa.domain.QBaseDo(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createTime = _super.createTime;

    //inherited
    public final StringPath createUserName = _super.createUserName;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final NumberPath<Long> resourceId = createNumber("resourceId", Long.class);

    public final NumberPath<Long> roleId = createNumber("roleId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    //inherited
    public final StringPath updateUserName = _super.updateUserName;

    public QAuthRoleResourceDo(String variable) {
        super(AuthRoleResourceDo.class, forVariable(variable));
    }

    public QAuthRoleResourceDo(Path<? extends AuthRoleResourceDo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuthRoleResourceDo(PathMetadata metadata) {
        super(AuthRoleResourceDo.class, metadata);
    }

}

