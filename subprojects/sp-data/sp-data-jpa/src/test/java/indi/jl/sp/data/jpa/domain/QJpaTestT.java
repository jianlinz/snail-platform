package indi.jl.sp.data.jpa.domain;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QJpaTestT is a Querydsl query type for JpaTestT
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QJpaTestT extends EntityPathBase<JpaTestT> {

    private static final long serialVersionUID = -1356661149L;

    public static final QJpaTestT jpaTestT = new QJpaTestT("jpaTestT");

    public final QBaseDo _super = new QBaseDo(this);

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

    public QJpaTestT(String variable) {
        super(JpaTestT.class, forVariable(variable));
    }

    public QJpaTestT(Path<? extends JpaTestT> path) {
        super(path.getType(), path.getMetadata());
    }

    public QJpaTestT(PathMetadata metadata) {
        super(JpaTestT.class, metadata);
    }

}

