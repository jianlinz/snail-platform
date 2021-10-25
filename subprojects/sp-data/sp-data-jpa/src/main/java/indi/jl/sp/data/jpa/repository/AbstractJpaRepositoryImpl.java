package indi.jl.sp.data.jpa.repository;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;

public class AbstractJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements AbstractJpaRepository<T, ID> {

    private final JpaEntityInformation<T, ?> entityInformation;

    public AbstractJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T updateForSelective(T var1) {
        return this.save(var1);
    }

}

