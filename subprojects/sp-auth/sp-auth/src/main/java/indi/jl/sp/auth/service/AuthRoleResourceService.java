package indi.jl.sp.auth.service;

import indi.jl.sp.auth.jpa.bo.AuthResourceRoleBo;
import indi.jl.sp.auth.jpa.domain.AuthResourceDo;
import indi.jl.sp.auth.jpa.domain.AuthRoleResourceDo;
import indi.jl.sp.core.domain.PageChunk;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface AuthRoleResourceService {


    /**
     * 保存
     *
     * @param authRoleResourceDo 保存对象
     * @return 保存后结果
     */
    @Transactional
    AuthRoleResourceDo save(AuthRoleResourceDo authRoleResourceDo);

    /**
     * 更新
     *
     * @param authRoleResourceDo 更新对象
     * @return 更新后结果
     */
    @Transactional
    AuthRoleResourceDo update(AuthRoleResourceDo authRoleResourceDo);

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
    Optional<AuthRoleResourceDo> get(Long id);

    /**
     * 查询
     *
     * @param authRoleResourceDo 查询条件
     * @return 查询结果集
     */
    List<AuthRoleResourceDo> findAll(AuthRoleResourceDo authRoleResourceDo);

    /**
     * 分页查询
     *
     * @param authRoleResourceDo 查询条件
     * @param pageable           分页参数
     * @return 查询结果集
     */
    PageChunk<AuthRoleResourceDo> findAll(AuthRoleResourceDo authRoleResourceDo, Pageable pageable);

    /**
     * 根据角色id集合查询资源集合
     *
     * @param roleIds 角色id集合
     * @return 资源集合
     */
    List<AuthResourceRoleBo> findResourcesByRoleIds(List<Long> roleIds);
}