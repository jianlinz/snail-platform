package indi.jl.sp.auth.service;

import indi.jl.sp.auth.jpa.domain.AuthRoleDo;
import indi.jl.sp.core.domain.PageChunk;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface AuthRoleService {
   
   
    /**
     * 保存
     *
     * @param authRoleDo 保存对象
     * @return 保存后结果
     */
    @Transactional
    AuthRoleDo save(AuthRoleDo authRoleDo);

    /**
     * 更新
     *
     * @param authRoleDo 更新对象
     * @return 更新后结果
     */
    @Transactional
    AuthRoleDo update(AuthRoleDo authRoleDo);

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
    Optional<AuthRoleDo> get(Long id);

    /**
     * 查询
     *
     * @param authRoleDo 查询条件
     * @return 查询结果集
     */   
    List<AuthRoleDo> findAll(AuthRoleDo authRoleDo);

    /**
     * 分页查询
     *
     * @param authRoleDo   查询条件
     * @param pageable 分页参数
     * @return 查询结果集
     */
    PageChunk<AuthRoleDo> findAll(AuthRoleDo authRoleDo, Pageable pageable);
}