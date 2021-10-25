package indi.jl.sp.auth.service;

import indi.jl.sp.auth.jpa.domain.AuthUserDo;
import indi.jl.sp.core.domain.PageChunk;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface AuthUserService {


    /**
     * 保存
     *
     * @param authUserDo 保存对象
     * @return 保存后结果
     */
    @Transactional
    AuthUserDo save(AuthUserDo authUserDo);

    /**
     * 更新
     *
     * @param authUserDo 更新对象
     * @return 更新后结果
     */
    @Transactional
    AuthUserDo update(AuthUserDo authUserDo);

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
    Optional<AuthUserDo> get(Long id);


    /**
     * 根据username 查询
     *
     * @param username 主键
     * @return 查询结果
     */
    Optional<AuthUserDo> get(String username);

    /**
     * 查询
     *
     * @param authUserDo 查询条件
     * @return 查询结果集
     */
    List<AuthUserDo> findAll(AuthUserDo authUserDo);

    /**
     * 分页查询
     *
     * @param authUserDo 查询条件
     * @param pageable   分页参数
     * @return 查询结果集
     */
    PageChunk<AuthUserDo> findAll(AuthUserDo authUserDo, Pageable pageable);
}