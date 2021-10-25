package indi.jl.sp.data.jpa.dao;

import com.querydsl.core.types.dsl.EntityPathBase;
import indi.jl.sp.core.domain.PageChunk;
import indi.jl.sp.data.jpa.domain.BaseDo;
import indi.jl.sp.data.jpa.querydsl.query.QueryDsl;
import indi.jl.sp.data.jpa.repository.AbstractJpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T extends BaseDo, ID> {

    protected abstract AbstractJpaRepository<T, ID> getRepository();

    protected abstract EntityPathBase<T> getEntityPathBase();

    public T save(T t) {
        return getRepository().save(t);
    }

    public List<T> saveAll(Iterable<T> dos) {
        return getRepository().saveAll(dos);
    }

    public T update(T t) {
        return getRepository().save(t);
    }

    public void delete(T t) {
        getRepository().delete(t);
    }

    public void deleteById(ID id) {
        getRepository().deleteById(id);
    }

    public void deleteInBatch(Iterable<T> dos) {
        getRepository().deleteInBatch(dos);
    }

    public void deleteByParam(T po) {
        QueryDsl
                .getQuery()
                .delete(getEntityPathBase())
                .where(QueryDsl.getPredicatesByNotNullFields(po, getEntityPathBase()))
                .execute();
    }

    public void deleteByIds(Iterable<ID> ids) {
        if (null != ids) {
            for (ID id : ids) {
                deleteById(id);
            }
        }
    }

    public Long count(T t) {
        return QueryDsl
                .getQuery()
                .selectFrom(getEntityPathBase())
                .where(QueryDsl.getPredicatesByNotNullFields(t, getEntityPathBase()))
                .fetchCount();
    }

    public Optional<T> getById(ID id) {
        return getRepository().findById(id);
    }

    public Optional<T> get(T t) {
        return Optional.ofNullable(QueryDsl
                .getQuery()
                .selectFrom(getEntityPathBase())
                .where(QueryDsl.getPredicatesByNotNullFields(t, getEntityPathBase()))
                .fetchOne());
    }

    public List<T> findAllById(Iterable<ID> ids) {
        return getRepository().findAllById(ids);
    }

    public List<T> findAll() {
        return QueryDsl.getQuery().selectFrom(getEntityPathBase()).fetch();
    }

    public List<T> findAll(T t) {
        return QueryDsl
                .getQuery()
                .selectFrom(getEntityPathBase())
                .where(QueryDsl.getPredicatesByNotNullFields(t, getEntityPathBase()))
                .fetch();
    }

    public PageChunk<T> findAll(T t, Pageable pageable) {
        return QueryDsl
                .page(
                        QueryDsl
                                .getQuery()
                                .selectFrom(getEntityPathBase())
                                .where(QueryDsl
                                        .getPredicatesByNotNullFields(t, getEntityPathBase())),
                        pageable,
                        getEntityPathBase()
                );
    }
}
