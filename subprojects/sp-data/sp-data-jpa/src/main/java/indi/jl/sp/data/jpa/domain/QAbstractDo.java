package indi.jl.sp.data.jpa.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAbstractDo is a Querydsl query type for AbstractDo
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QAbstractDo extends EntityPathBase<AbstractDo> {

    private static final long serialVersionUID = -1076849463L;

    public static final QAbstractDo abstractDo = new QAbstractDo("abstractDo");

    public final DateTimePath<java.time.LocalDateTime> createTime = createDateTime("createTime", java.time.LocalDateTime.class);

    public final StringPath createUserName = createString("createUserName");

    public final DateTimePath<java.time.LocalDateTime> updateTime = createDateTime("updateTime", java.time.LocalDateTime.class);

    public final StringPath updateUserName = createString("updateUserName");

    public QAbstractDo(String variable) {
        super(AbstractDo.class, forVariable(variable));
    }

    public QAbstractDo(Path<? extends AbstractDo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAbstractDo(PathMetadata metadata) {
        super(AbstractDo.class, metadata);
    }

}

