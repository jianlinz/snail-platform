package indi.jl.sp.data.jpa.querydsl.query;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import indi.jl.sp.core.SpApplicationContext;
import indi.jl.sp.core.domain.PageChunk;
import indi.jl.sp.core.util.BeanUtil;
import indi.jl.sp.core.util.CollectionUtil;
import indi.jl.sp.data.jpa.domain.BaseDo;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueryDsl {
    private QueryDsl() {
    }

    public static JPAQueryFactory getQuery() {
        return SpApplicationContext.getBean(QueryDslService.class).getQuery();
    }

    public static Predicate[] getPredicatesByNotNullFields(BaseDo baseDO, EntityPathBase entityPathBase) {
        List<Predicate> list = new ArrayList();
        Arrays.stream(BeanUtil.getNotNullProperties(baseDO)).forEach(fieldName -> {
            SimpleExpression expression = (SimpleExpression) BeanUtil.getValue(entityPathBase, fieldName);
            list.add(expression.eq(BeanUtil.getValue(baseDO, fieldName)));
        });
        return list.toArray(new Predicate[list.size()]);
    }

    public static <T extends BaseDo> PageChunk<T> page(JPAQuery<T> jpaQuery, Pageable pageable, EntityPathBase<T> entityPathBase) {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
        if (pageable.getSort() != null) {
            pageable.getSort().forEach(order -> {
                        SimpleExpression expression = (SimpleExpression) BeanUtil.getValue(entityPathBase, order.getProperty());
                        orderSpecifiers.add(new OrderSpecifier(order.getDirection().isAscending()
                                ? Order.ASC
                                : Order.DESC, expression));
                    }
            );
        }
        if (CollectionUtil.isNotEmpty(orderSpecifiers)) {
            OrderSpecifier[] orderSpecifierAttrs = orderSpecifiers.stream().toArray(OrderSpecifier[]::new);
            jpaQuery.orderBy(orderSpecifierAttrs);
        }
        jpaQuery = (JPAQuery) jpaQuery.limit(pageable.getPageSize()).offset(pageable.getPageNumber() * pageable.getPageSize());
        return new PageChunk<>(new PageImpl<>(jpaQuery.fetch(), pageable, jpaQuery.fetchCount()));
    }
}
