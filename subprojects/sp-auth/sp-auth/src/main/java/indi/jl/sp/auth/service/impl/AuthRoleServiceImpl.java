package indi.jl.sp.auth.service.impl;

import indi.jl.sp.auth.jpa.dao.AuthRoleDao;
import indi.jl.sp.auth.jpa.domain.AuthRoleDo;
import indi.jl.sp.auth.service.AuthRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import indi.jl.sp.core.domain.PageChunk;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthRoleServiceImpl implements AuthRoleService {

    private final AuthRoleDao authRoleDao;

    @Autowired
    public AuthRoleServiceImpl(AuthRoleDao authRoleDao) {
        this.authRoleDao = authRoleDao;
    }

    @Override
    public Optional<AuthRoleDo> get(Long id) {
        return authRoleDao.getById(id);
    }

    @Override
    public AuthRoleDo save(AuthRoleDo authRoleDo) {
        return authRoleDao.save(authRoleDo);
    }

    @Override
    public AuthRoleDo update(AuthRoleDo authRoleDo) {
        return authRoleDao.update(authRoleDo);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        authRoleDao.deleteByIds(ids);
    }
    
    @Override
    public List<AuthRoleDo> findAll(AuthRoleDo authRoleDo) {
        return authRoleDao.findAll(authRoleDo);
    }

    @Override
    public PageChunk<AuthRoleDo> findAll(AuthRoleDo authRoleDo, Pageable pageable) {
        return authRoleDao.findAll(authRoleDo, pageable);
    }

}