/*
 * Copyright 2015, The Querydsl Team (http://www.querydsl.com/team)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package indi.jl.sp.data.jpa.querydsl.jpa;

import com.google.common.collect.Sets;
import com.querydsl.core.JoinExpression;
import com.querydsl.core.JoinType;
import com.querydsl.core.QueryMetadata;
import com.querydsl.core.types.*;
import com.querydsl.jpa.JPAQueryMixin;
import com.querydsl.jpa.JPQLOps;
import com.querydsl.jpa.JPQLSerializer;
import com.querydsl.jpa.JPQLTemplates;
import indi.jl.sp.data.jpa.querydsl.core.SpDefaultQueryMetadata;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import java.util.*;

/**
 * {@code JPQLSerializer} serializes Querydsl expressions into JPQL syntax.
 *
 * @author tiwe
 */
public class SpJPQLSerializer extends JPQLSerializer {

    private static final Set<? extends Operator> NUMERIC = Sets.immutableEnumSet(
            Ops.ADD, Ops.SUB, Ops.MULT, Ops.DIV,
            Ops.LT, Ops.LOE, Ops.GT, Ops.GOE, Ops.BETWEEN);

    private static final Set<? extends Operator> CASE_OPS = Sets.immutableEnumSet(
            Ops.CASE_EQ_ELSE, Ops.CASE_ELSE);

    private static final String COMMA = ", ";

    private static final String DELETE = "delete from ";

    private static final String FROM = "from ";

    private static final String GROUP_BY = "\ngroup by ";

    private static final String HAVING = "\nhaving ";

    private static final String ORDER_BY = "\norder by ";

    private static final String SELECT = "select ";

    private static final String SELECT_COUNT = "select count(";

    private static final String SELECT_COUNT_DISTINCT = "select count(distinct ";

    private static final String SELECT_DISTINCT = "select distinct ";

    private static final String SET = "\nset ";

    private static final String UPDATE = "update ";

    private static final String WHERE = "\nwhere ";

    private static final String WITH = " with ";

    private static final String ON = " on ";

    private static final Map<JoinType, String> joinTypes = new EnumMap<JoinType, String>(JoinType.class);

    private final JPQLTemplates templates;

    private final EntityManager entityManager;

    private boolean inProjection = false;

    private boolean inCaseOperation = false;

    static {
        joinTypes.put(JoinType.DEFAULT, COMMA);
        joinTypes.put(JoinType.FULLJOIN, "\n  full join ");
        joinTypes.put(JoinType.INNERJOIN, "\n  inner join ");
        joinTypes.put(JoinType.JOIN, "\n  inner join ");
        joinTypes.put(JoinType.LEFTJOIN, "\n  left join ");
        joinTypes.put(JoinType.RIGHTJOIN, "\n  right join ");
    }

    private boolean wrapElements = false;

    public SpJPQLSerializer(JPQLTemplates templates) {
        this(templates, null);
    }

    public SpJPQLSerializer(JPQLTemplates templates, EntityManager em) {
        super(templates);
        this.templates = templates;
        this.entityManager = em;
    }

    private String getEntityName(Class<?> clazz) {
        final Entity entityAnnotation = clazz.getAnnotation(Entity.class);
        if (entityAnnotation != null && entityAnnotation.name().length() > 0) {
            return entityAnnotation.name();
        } else if (clazz.getPackage() != null) {
            String pn = clazz.getPackage().getName();
            return clazz.getName().substring(pn.length() + 1);
        } else {
            return clazz.getName();
        }
    }

    private void handleJoinTarget(JoinExpression je) {
        // type specifier
        if (je.getTarget() instanceof EntityPath<?>) {
            final EntityPath<?> pe = (EntityPath<?>) je.getTarget();
            if (pe.getMetadata().isRoot()) {
                append(getEntityName(pe.getType()));
                append(" ");
            }
            handle(je.getTarget());
        } else if (je.getTarget() instanceof Operation) {
            Operation<?> op = (Operation) je.getTarget();
            if (op.getOperator() == Ops.ALIAS) {
                boolean treat = false;
                if (Collection.class.isAssignableFrom(op.getArg(0).getType())) {
                    if (op.getArg(0) instanceof CollectionExpression) {
                        Class<?> par = ((CollectionExpression) op.getArg(0)).getParameter(0);
                        treat = !par.equals(op.getArg(1).getType());
                    }
                } else if (Map.class.isAssignableFrom(op.getArg(0).getType())) {
                    if (op.getArg(0) instanceof MapExpression) {
                        Class<?> par = ((MapExpression) op.getArg(0)).getParameter(1);
                        treat = !par.equals(op.getArg(1).getType());
                    }
                } else {
                    treat = !op.getArg(0).getType().equals(op.getArg(1).getType());
                }
                if (treat) {
                    Expression<?> entityName = ConstantImpl.create(getEntityName(op.getArg(1).getType()));
                    Expression<?> t = ExpressionUtils.operation(op.getType(), JPQLOps.TREAT, op.getArg(0), entityName);
                    op = ExpressionUtils.operation(op.getType(), Ops.ALIAS, t, op.getArg(1));
                }
            }
            handle(op);
        } else {
            handle(je.getTarget());
        }
    }

    public void serialize(QueryMetadata metadata, boolean forCountRow, @Nullable String projection) {
        final Expression<?> select = metadata.getProjection();
        final List<JoinExpression> joins = metadata.getJoins();
        Expression<?> whereProjection = null;
        SpDefaultQueryMetadata defaultQueryMetadata = ((SpDefaultQueryMetadata) metadata);
        switch (defaultQueryMetadata.getJoinType()) {
            case DEFAULT:
                whereProjection = defaultQueryMetadata.getJoinTarget();
                break;
            case JOIN:
            case FULLJOIN:
            case LEFTJOIN:
            case INNERJOIN:
            case RIGHTJOIN:
                for (JoinExpression joinExpression : defaultQueryMetadata.getJoins()) {
                    if (JoinType.DEFAULT == joinExpression.getType()) {
                        whereProjection = joinExpression.getTarget();
                    }
                }
                break;
            default:
                throw new RuntimeException("未知的QueryDsl join 类型");
        }
//查询条件统一扩展
//        SimpleExpression tenantIdExpression = (SimpleExpression) BeanUtil
//                .getValue(whereProjection, "tenantID");
//        metadata.addWhere(tenantIdExpression.eq(Tenant.getTenantID()));

        final Predicate where = metadata.getWhere();
        final List<? extends Expression<?>> groupBy = metadata.getGroupBy();
        final Predicate having = metadata.getHaving();
        final List<OrderSpecifier<?>> orderBy = metadata.getOrderBy();

        // select
        boolean inProjectionOrig = inProjection;
        inProjection = true;
        if (projection != null) {
            append(SELECT).append(projection).append("\n");

        } else if (forCountRow) {
            if (!groupBy.isEmpty()) {
                append(SELECT_COUNT_DISTINCT);
                handle(", ", groupBy);
            } else {
                if (!metadata.isDistinct()) {
                    append(SELECT_COUNT);
                } else {
                    append(SELECT_COUNT_DISTINCT);
                }
                if (select != null) {
                    if (select instanceof FactoryExpression) {
                        handle(joins.get(0).getTarget());
                    } else {
                        // TODO : make sure this works
                        handle(select);
                    }
                } else {
                    handle(joins.get(0).getTarget());
                }
            }
            append(")\n");

        } else if (select != null || !joins.isEmpty()) {
            if (!metadata.isDistinct()) {
                append(SELECT);
            } else {
                append(SELECT_DISTINCT);
            }
            if (select != null) {
                handle(select);
            } else {
                handle(joins.get(0).getTarget());
            }
            append("\n");

        }
        inProjection = inProjectionOrig;

        // from
        if (!joins.isEmpty()) {
            append(FROM);
            serializeSources(forCountRow, joins);
        }

        // where
        if (where != null) {
            append(WHERE).handle(where);
        }

        // group by
        if (!groupBy.isEmpty() && !forCountRow) {
            append(GROUP_BY).handle(COMMA, groupBy);
        }

        // having
        if (having != null) {
            append(HAVING).handle(having);
        }

        // order by
        if (!orderBy.isEmpty() && !forCountRow) {
            append(ORDER_BY);
            boolean first = true;
            for (final OrderSpecifier<?> os : orderBy) {
                if (!first) {
                    append(COMMA);
                }
                handle(os.getTarget());
                append(os.getOrder() == Order.ASC ? " asc" : " desc");
                if (os.getNullHandling() == OrderSpecifier.NullHandling.NullsFirst) {
                    append(" nulls first");
                } else if (os.getNullHandling() == OrderSpecifier.NullHandling.NullsLast) {
                    append(" nulls last");
                }
                first = false;
            }
        }
    }

    public void serializeForDelete(QueryMetadata md) {
        append(DELETE);
        handleJoinTarget(md.getJoins().get(0));
        if (md.getWhere() != null) {
            append(WHERE).handle(md.getWhere());
        }
    }

    public void serializeForUpdate(QueryMetadata md, Map<Path<?>, Expression<?>> updates) {
        append(UPDATE);
        handleJoinTarget(md.getJoins().get(0));
        append(SET);
        boolean first = true;
        for (Map.Entry<Path<?>, Expression<?>> entry : updates.entrySet()) {
            if (!first) {
                append(", ");
            }
            handle(entry.getKey());
            append(" = ");
            handle(entry.getValue());
            first = false;
        }
        if (md.getWhere() != null) {
            append(WHERE).handle(md.getWhere());
        }
    }

    private void serializeSources(boolean forCountRow, List<JoinExpression> joins) {
        for (int i = 0; i < joins.size(); i++) {
            final JoinExpression je = joins.get(i);
            if (i > 0) {
                append(joinTypes.get(je.getType()));
            }
            if (je.hasFlag(JPAQueryMixin.FETCH) && !forCountRow) {
                handle(JPAQueryMixin.FETCH);
            }
            handleJoinTarget(je);
            // XXX Hibernate specific flag
            if (je.hasFlag(JPAQueryMixin.FETCH_ALL_PROPERTIES) && !forCountRow) {
                handle(JPAQueryMixin.FETCH_ALL_PROPERTIES);
            }

            if (je.getCondition() != null) {
                append(templates.isWithForOn() ? WITH : ON);
                handle(je.getCondition());
            }
        }
    }

    public void visitLiteral(Object constant) {
        append(templates.asLiteral(constant));
    }


    @Override
    public Void visit(SubQueryExpression<?> query, Void context) {
        append("(");
        serialize(query.getMetadata(), false, null);
        append(")");
        return null;
    }

    @Override
    public Void visit(Path<?> expr, Void context) {
        // only wrap a PathCollection, if it the pathType is PROPERTY
        boolean wrap = wrapElements
                && (Collection.class.isAssignableFrom(expr.getType()) || Map.class.isAssignableFrom(expr.getType()))
                && expr.getMetadata().getPathType().equals(PathType.PROPERTY);
        if (wrap) {
            append("elements(");
        }
        super.visit(expr, context);
        if (wrap) {
            append(")");
        }
        return null;
    }



}
