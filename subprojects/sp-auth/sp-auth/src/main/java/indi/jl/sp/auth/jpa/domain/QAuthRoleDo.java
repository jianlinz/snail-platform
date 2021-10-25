package indi.jl.sp.auth.jpa.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuthRoleDo is a Querydsl query type for AuthRoleDo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAuthRoleDo extends EntityPathBase<AuthRoleDo> {

    private static final long serialVersionUID = -843151517L;

    public static final QAuthRoleDo authRoleDo = new QAuthRoleDo("authRoleDo");

    public final indi.jl.sp.data.jpa.domain.QBaseDo _super = new indi.jl.sp.data.jpa.domain.QBaseDo(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createTime = _super.createTime;

    //inherited
    public final StringPath createUserName = _super.createUserName;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath name = createString("name");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    //inherited
    public final StringPath updateUserName = _super.updateUserName;

    public QAuthRoleDo(String variable) {
        super(AuthRoleDo.class, forVariable(variable));
    }

    public QAuthRoleDo(Path<? extends AuthRoleDo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuthRoleDo(PathMetadata metadata) {
        super(AuthRoleDo.class, metadata);
    }

}

