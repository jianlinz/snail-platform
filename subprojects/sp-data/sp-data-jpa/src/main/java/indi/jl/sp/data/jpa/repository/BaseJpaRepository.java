package indi.jl.sp.data.jpa.repository;

import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseJpaRepository<T> extends AbstractJpaRepository<T, Long> {
}
