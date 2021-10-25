package indi.jl.sp.auth.jpa.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuthResourceDo is a Querydsl query type for AuthResourceDo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAuthResourceDo extends EntityPathBase<AuthResourceDo> {

    private static final long serialVersionUID = -1543072773L;

    public static final QAuthResourceDo authResourceDo = new QAuthResourceDo("authResourceDo");

    public final indi.jl.sp.data.jpa.domain.QBaseDo _super = new indi.jl.sp.data.jpa.domain.QBaseDo(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createTime = _super.createTime;

    //inherited
    public final StringPath createUserName = _super.createUserName;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath method = createString("method");

    public final StringPath name = createString("name");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    //inherited
    public final StringPath updateUserName = _super.updateUserName;

    public final StringPath url = createString("url");

    public QAuthResourceDo(String variable) {
        super(AuthResourceDo.class, forVariable(variable));
    }

    public QAuthResourceDo(Path<? extends AuthResourceDo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuthResourceDo(PathMetadata metadata) {
        super(AuthResourceDo.class, metadata);
    }

}

