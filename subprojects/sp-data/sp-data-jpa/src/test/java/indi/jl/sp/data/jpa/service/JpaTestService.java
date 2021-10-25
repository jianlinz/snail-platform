package indi.jl.sp.data.jpa.service;

import indi.jl.sp.data.jpa.domain.JpaTestT;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface JpaTestService {

    Optional<JpaTestT> get(Long id);

    Optional<JpaTestT> findByName(String name);

    Optional<JpaTestT> findByNameCache(String name);

    List<JpaTestT> findAll();

    JpaTestT readOnlyTx(JpaTestT jpaTestT);

    @Transactional
    JpaTestT save(JpaTestT jpaTestT);

    @Transactional
    JpaTestT saveTestTx(JpaTestT jpaTestT);

    @Transactional
    JpaTestT update(JpaTestT jpaTestT);

    @Transactional
    void delete(Long id);
}
