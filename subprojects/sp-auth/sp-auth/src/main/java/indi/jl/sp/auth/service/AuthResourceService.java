package indi.jl.sp.auth.service;

import indi.jl.sp.auth.jpa.domain.AuthResourceDo;
import indi.jl.sp.core.domain.PageChunk;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface AuthResourceService {
   
   
    /**
     * 保存
     *
     * @param authResourceDo 保存对象
     * @return 保存后结果
     */
    @Transactional
    AuthResourceDo save(AuthResourceDo authResourceDo);

    /**
     * 更新
     *
     * @param authResourceDo 更新对象
     * @return 更新后结果
     */
    @Transactional
    AuthResourceDo update(AuthResourceDo authResourceDo);

    /**
     * 根据id集合删除
     *
     * @param ids ids
     */
    @Transactional
    void deleteByIds(List<Long> ids);
    
    /**
     * 根据id 查询 
     *
     * @param id 主键
     * @return 查询结果
     */
    Optional<AuthResourceDo> get(Long id);

    /**
     * 查询
     *
     * @param authResourceDo 查询条件
     * @return 查询结果集
     */   
    List<AuthResourceDo> findAll(AuthResourceDo authResourceDo);

    /**
     * 分页查询
     *
     * @param authResourceDo   查询条件
     * @param pageable 分页参数
     * @return 查询结果集
     */
    PageChunk<AuthResourceDo> findAll(AuthResourceDo authResourceDo, Pageable pageable);
}