package indi.jl.sp.auth.jpa.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuthUserDo is a Querydsl query type for AuthUserDo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAuthUserDo extends EntityPathBase<AuthUserDo> {

    private static final long serialVersionUID = -753766024L;

    public static final QAuthUserDo authUserDo = new QAuthUserDo("authUserDo");

    public final indi.jl.sp.data.jpa.domain.QBaseDo _super = new indi.jl.sp.data.jpa.domain.QBaseDo(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createTime = _super.createTime;

    //inherited
    public final StringPath createUserName = _super.createUserName;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    //inherited
    public final StringPath updateUserName = _super.updateUserName;

    public final StringPath username = createString("username");

    public QAuthUserDo(String variable) {
        super(AuthUserDo.class, forVariable(variable));
    }

    public QAuthUserDo(Path<? extends AuthUserDo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuthUserDo(PathMetadata metadata) {
        super(AuthUserDo.class, metadata);
    }

}

