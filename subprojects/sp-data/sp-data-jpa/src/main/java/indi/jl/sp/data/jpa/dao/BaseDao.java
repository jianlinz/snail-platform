package indi.jl.sp.data.jpa.dao;

import indi.jl.sp.data.jpa.domain.BaseDo;
import indi.jl.sp.data.jpa.repository.AbstractJpaRepository;

public abstract class BaseDao<T extends BaseDo> extends AbstractDao<T, Long> {

    @Override
    protected abstract AbstractJpaRepository<T, Long> getRepository();

}
