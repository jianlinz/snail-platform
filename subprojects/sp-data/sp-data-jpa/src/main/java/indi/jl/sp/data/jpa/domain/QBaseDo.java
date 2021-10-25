package indi.jl.sp.data.jpa.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBaseDo is a Querydsl query type for BaseDo
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QBaseDo extends EntityPathBase<BaseDo> {

    private static final long serialVersionUID = 1369659832L;

    public static final QBaseDo baseDo = new QBaseDo("baseDo");

    public final QAbstractDo _super = new QAbstractDo(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createTime = _super.createTime;

    //inherited
    public final StringPath createUserName = _super.createUserName;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    //inherited
    public final StringPath updateUserName = _super.updateUserName;

    public QBaseDo(String variable) {
        super(BaseDo.class, forVariable(variable));
    }

    public QBaseDo(Path<? extends BaseDo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBaseDo(PathMetadata metadata) {
        super(BaseDo.class, metadata);
    }

}

