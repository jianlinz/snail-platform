package indi.jl.sp.data.jpa.repository;

import indi.jl.sp.data.jpa.domain.JpaTestT;
import indi.jl.sp.data.jpa.repository.BaseJpaRepository;

public interface JpaTestTRepository extends BaseJpaRepository<JpaTestT> {

    JpaTestT findByName(String name);
}
