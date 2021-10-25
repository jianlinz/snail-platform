package indi.jl.sp.data.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;
import java.io.Serializable;

public class SpJpaRepositoryBean<R extends JpaRepository<T, IDT>, T, IDT extends Serializable> extends JpaRepositoryFactoryBean<R, T, IDT> {

    public SpJpaRepositoryBean(Class<? extends R> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(
        EntityManager entityManager) {
        return new ExpandJpaRepositoryFactory(entityManager);
    }

    private static class ExpandJpaRepositoryFactory extends JpaRepositoryFactory {

        ExpandJpaRepositoryFactory(EntityManager entityManager) {
            super(entityManager);
        }

        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return AbstractJpaRepositoryImpl.class;
        }
    }

}
