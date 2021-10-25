package indi.jl.sp.auth.service.impl;

import indi.jl.sp.auth.jpa.bo.AuthResourceRoleBo;
import indi.jl.sp.auth.jpa.dao.AuthRoleResourceDao;
import indi.jl.sp.auth.jpa.domain.AuthResourceDo;
import indi.jl.sp.auth.jpa.domain.AuthRoleResourceDo;
import indi.jl.sp.auth.service.AuthRoleResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import indi.jl.sp.core.domain.PageChunk;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthRoleResourceServiceImpl implements AuthRoleResourceService {

    private final AuthRoleResourceDao authRoleResourceDao;

    @Autowired
    public AuthRoleResourceServiceImpl(AuthRoleResourceDao authRoleResourceDao) {
        this.authRoleResourceDao = authRoleResourceDao;
    }

    @Override
    public Optional<AuthRoleResourceDo> get(Long id) {
        return authRoleResourceDao.getById(id);
    }

    @Override
    public AuthRoleResourceDo save(AuthRoleResourceDo authRoleResourceDo) {
        return authRoleResourceDao.save(authRoleResourceDo);
    }

    @Override
    public AuthRoleResourceDo update(AuthRoleResourceDo authRoleResourceDo) {
        return authRoleResourceDao.update(authRoleResourceDo);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        authRoleResourceDao.deleteByIds(ids);
    }

    @Override
    public List<AuthRoleResourceDo> findAll(AuthRoleResourceDo authRoleResourceDo) {
        return authRoleResourceDao.findAll(authRoleResourceDo);
    }

    @Override
    public PageChunk<AuthRoleResourceDo> findAll(AuthRoleResourceDo authRoleResourceDo, Pageable pageable) {
        return authRoleResourceDao.findAll(authRoleResourceDo, pageable);
    }

    @Override
    public List<AuthResourceRoleBo> findResourcesByRoleIds(List<Long> roleIds) {
        return authRoleResourceDao.findResourcesByRoleIds(roleIds);
    }
}