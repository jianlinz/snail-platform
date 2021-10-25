package indi.jl.sp.auth.service.impl;

import indi.jl.sp.auth.jpa.dao.AuthUserRoleDao;
import indi.jl.sp.auth.jpa.domain.AuthRoleDo;
import indi.jl.sp.auth.jpa.domain.AuthUserRoleDo;
import indi.jl.sp.auth.service.AuthUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import indi.jl.sp.core.domain.PageChunk;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthUserRoleServiceImpl implements AuthUserRoleService {

    private final AuthUserRoleDao authUserRoleDao;

    @Autowired
    public AuthUserRoleServiceImpl(AuthUserRoleDao authUserRoleDao) {
        this.authUserRoleDao = authUserRoleDao;
    }

    @Override
    public Optional<AuthUserRoleDo> get(Long id) {
        return authUserRoleDao.getById(id);
    }

    @Override
    public AuthUserRoleDo save(AuthUserRoleDo authUserRoleDo) {
        return authUserRoleDao.save(authUserRoleDo);
    }

    @Override
    public AuthUserRoleDo update(AuthUserRoleDo authUserRoleDo) {
        return authUserRoleDao.update(authUserRoleDo);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        authUserRoleDao.deleteByIds(ids);
    }
    
    @Override
    public List<AuthUserRoleDo> findAll(AuthUserRoleDo authUserRoleDo) {
        return authUserRoleDao.findAll(authUserRoleDo);
    }

    @Override
    public List<AuthRoleDo> findRolesByUserId(Long userId) {
        return authUserRoleDao.findRolesByUserId(userId);
    }

    @Override
    public PageChunk<AuthUserRoleDo> findAll(AuthUserRoleDo authUserRoleDo, Pageable pageable) {
        return authUserRoleDao.findAll(authUserRoleDo, pageable);
    }

}