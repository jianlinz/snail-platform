package indi.jl.sp.data.jpa.querydsl.jpa.impl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;

import javax.annotation.Nullable;
import javax.inject.Provider;
import javax.persistence.EntityManager;

public class SpJPAQueryFactory extends JPAQueryFactory implements JPQLQueryFactory {

    @Nullable
    private final JPQLTemplates templates;
    private final Provider<EntityManager> entityManager;

    public SpJPAQueryFactory(final EntityManager entityManager) {
        super(entityManager);
        this.entityManager = new Provider<EntityManager>() {
            public EntityManager get() {
                return entityManager;
            }
        };
        this.templates = null;
    }

    public SpJPAQueryFactory(JPQLTemplates templates, final EntityManager entityManager) {
        super(templates, entityManager);
        this.entityManager = new Provider<EntityManager>() {
            public EntityManager get() {
                return entityManager;
            }
        };
        this.templates = templates;
    }

    public SpJPAQueryFactory(Provider<EntityManager> entityManager) {
        super(entityManager);
        this.entityManager = entityManager;
        this.templates = null;
    }

    public SpJPAQueryFactory(JPQLTemplates templates, Provider<EntityManager> entityManager) {
        super(templates, entityManager);
        this.entityManager = entityManager;
        this.templates = templates;
    }

    public <T> JPAQuery<T> select(Expression<T> expr) {
        return this.query().select(expr);
    }

    public JPAQuery<Tuple> select(Expression<?>... exprs) {
        return this.query().select(exprs);
    }

    public <T> JPAQuery<T> selectDistinct(Expression<T> expr) {
        return (JPAQuery) this.select(expr).distinct();
    }

    public JPAQuery<Tuple> selectDistinct(Expression<?>... exprs) {
        return (JPAQuery) this.select(exprs).distinct();
    }

    public JPAQuery<Integer> selectOne() {
        return this.select((Expression) Expressions.ONE);
    }

    public JPAQuery<Integer> selectZero() {
        return this.select((Expression) Expressions.ZERO);
    }

    public <T> JPAQuery<T> selectFrom(EntityPath<T> from) {
        return (JPAQuery) this.select((Expression) from).from(from);
    }

    public JPAQuery<?> from(EntityPath<?> from) {
        return (JPAQuery) this.query().from(from);
    }

    public JPAQuery<?> from(EntityPath<?>... from) {
        return (JPAQuery) this.query().from(from);
    }

    public JPADeleteClause delete(EntityPath<?> path) {
        return this.templates != null ? new JPADeleteClause((EntityManager) this.entityManager.get(), path, this.templates) : new JPADeleteClause((EntityManager) this.entityManager.get(), path);
    }

    public JPAUpdateClause update(EntityPath<?> path) {
        return this.templates != null ? new JPAUpdateClause((EntityManager) this.entityManager.get(), path, this.templates) : new JPAUpdateClause((EntityManager) this.entityManager.get(), path);
    }

    public JPAQuery<?> query() {
        return this.templates != null ? new SpJPAQuery<>((EntityManager) this.entityManager.get(), this.templates) : new SpJPAQuery((EntityManager) this.entityManager.get());
    }
}
