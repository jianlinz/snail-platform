package indi.jl.sp.data.jpa.querydsl.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import indi.jl.sp.data.jpa.querydsl.jpa.impl.SpJPAQueryFactory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class QueryDslService {


    private EntityManager entityManager;

    private SpJPAQueryFactory queryFactory;

    public QueryDslService(EntityManager entityManager) {
        this.entityManager = entityManager;
        queryFactory = new SpJPAQueryFactory(entityManager);
    }

    public JPAQueryFactory getQuery() {
        return queryFactory;
    }
}
