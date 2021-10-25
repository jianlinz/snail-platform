package indi.jl.sp.auth.service.impl;

import indi.jl.sp.auth.jpa.dao.AuthResourceDao;
import indi.jl.sp.auth.jpa.domain.AuthResourceDo;
import indi.jl.sp.auth.service.AuthResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import indi.jl.sp.core.domain.PageChunk;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthResourceServiceImpl implements AuthResourceService {

    private final AuthResourceDao authResourceDao;

    @Autowired
    public AuthResourceServiceImpl(AuthResourceDao authResourceDao) {
        this.authResourceDao = authResourceDao;
    }

    @Override
    public Optional<AuthResourceDo> get(Long id) {
        return authResourceDao.getById(id);
    }

    @Override
    public AuthResourceDo save(AuthResourceDo authResourceDo) {
        return authResourceDao.save(authResourceDo);
    }

    @Override
    public AuthResourceDo update(AuthResourceDo authResourceDo) {
        return authResourceDao.update(authResourceDo);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        authResourceDao.deleteByIds(ids);
    }
    
    @Override
    public List<AuthResourceDo> findAll(AuthResourceDo authResourceDo) {
        return authResourceDao.findAll(authResourceDo);
    }

    @Override
    public PageChunk<AuthResourceDo> findAll(AuthResourceDo authResourceDo, Pageable pageable) {
        return authResourceDao.findAll(authResourceDo, pageable);
    }

}