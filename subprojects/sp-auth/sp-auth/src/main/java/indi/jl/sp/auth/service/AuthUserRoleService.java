package indi.jl.sp.auth.service;

import indi.jl.sp.auth.jpa.domain.AuthRoleDo;
import indi.jl.sp.auth.jpa.domain.AuthUserRoleDo;
import indi.jl.sp.core.domain.PageChunk;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface AuthUserRoleService {


    /**
     * 保存
     *
     * @param authUserRoleDo 保存对象
     * @return 保存后结果
     */
    @Transactional
    AuthUserRoleDo save(AuthUserRoleDo authUserRoleDo);

    /**
     * 更新
     *
     * @param authUserRoleDo 更新对象
     * @return 更新后结果
     */
    @Transactional
    AuthUserRoleDo update(AuthUserRoleDo authUserRoleDo);

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
    Optional<AuthUserRoleDo> get(Long id);

    /**
     * 查询
     *
     * @param authUserRoleDo 查询条件
     * @return 查询结果集
     */
    List<AuthUserRoleDo> findAll(AuthUserRoleDo authUserRoleDo);

    /**
     * 根据用户Id 查询用户角色集合
     *
     * @param userId 用户id
     * @return 用户角色集合
     */
    List<AuthRoleDo> findRolesByUserId(Long userId);


    /**
     * 分页查询
     *
     * @param authUserRoleDo 查询条件
     * @param pageable       分页参数
     * @return 查询结果集
     */
    PageChunk<AuthUserRoleDo> findAll(AuthUserRoleDo authUserRoleDo, Pageable pageable);
}