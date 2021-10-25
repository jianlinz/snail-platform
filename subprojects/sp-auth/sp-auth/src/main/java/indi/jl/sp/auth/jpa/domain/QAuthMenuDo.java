package indi.jl.sp.auth.jpa.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuthMenuDo is a Querydsl query type for AuthMenuDo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAuthMenuDo extends EntityPathBase<AuthMenuDo> {

    private static final long serialVersionUID = -995457524L;

    public static final QAuthMenuDo authMenuDo = new QAuthMenuDo("authMenuDo");

    public final indi.jl.sp.data.jpa.domain.QBaseDo _super = new indi.jl.sp.data.jpa.domain.QBaseDo(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createTime = _super.createTime;

    //inherited
    public final StringPath createUserName = _super.createUserName;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath name = createString("name");

    public final NumberPath<Long> parentId = createNumber("parentId", Long.class);

    public final StringPath route = createString("route");

    public final NumberPath<Integer> sequenceNumber = createNumber("sequenceNumber", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    //inherited
    public final StringPath updateUserName = _super.updateUserName;

    public QAuthMenuDo(String variable) {
        super(AuthMenuDo.class, forVariable(variable));
    }

    public QAuthMenuDo(Path<? extends AuthMenuDo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuthMenuDo(PathMetadata metadata) {
        super(AuthMenuDo.class, metadata);
    }

}

