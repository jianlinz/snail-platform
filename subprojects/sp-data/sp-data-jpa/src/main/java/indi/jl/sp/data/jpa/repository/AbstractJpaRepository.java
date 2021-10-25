package indi.jl.sp.data.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AbstractJpaRepository<T,ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    /**
     * 更新
     *
     * @param var1 var1
     * @return T 泛型
     */
    T updateForSelective(T var1);


}
