package indi.jl.sp.auth.service;

import indi.jl.sp.auth.jpa.domain.AuthMenuDo;
import indi.jl.sp.core.domain.PageChunk;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface AuthMenuService {
   
   
    /**
     * 保存
     *
     * @param authMenuDo 保存对象
     * @return 保存后结果
     */
    @Transactional
    AuthMenuDo save(AuthMenuDo authMenuDo);

    /**
     * 更新
     *
     * @param authMenuDo 更新对象
     * @return 更新后结果
     */
    @Transactional
    AuthMenuDo update(AuthMenuDo authMenuDo);

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
    Optional<AuthMenuDo> get(Long id);

    /**
     * 查询
     *
     * @param authMenuDo 查询条件
     * @return 查询结果集
     */   
    List<AuthMenuDo> findAll(AuthMenuDo authMenuDo);

    /**
     * 分页查询
     *
     * @param authMenuDo   查询条件
     * @param pageable 分页参数
     * @return 查询结果集
     */
    PageChunk<AuthMenuDo> findAll(AuthMenuDo authMenuDo, Pageable pageable);
}