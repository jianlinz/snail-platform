package indi.jl.sp.auth.service.impl;

import indi.jl.sp.auth.jpa.dao.AuthMenuDao;
import indi.jl.sp.auth.jpa.domain.AuthMenuDo;
import indi.jl.sp.auth.service.AuthMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import indi.jl.sp.core.domain.PageChunk;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthMenuServiceImpl implements AuthMenuService {

    private final AuthMenuDao authMenuDao;

    @Autowired
    public AuthMenuServiceImpl(AuthMenuDao authMenuDao) {
        this.authMenuDao = authMenuDao;
    }

    @Override
    public Optional<AuthMenuDo> get(Long id) {
        return authMenuDao.getById(id);
    }

    @Override
    public AuthMenuDo save(AuthMenuDo authMenuDo) {
        return authMenuDao.save(authMenuDo);
    }

    @Override
    public AuthMenuDo update(AuthMenuDo authMenuDo) {
        return authMenuDao.update(authMenuDo);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        authMenuDao.deleteByIds(ids);
    }
    
    @Override
    public List<AuthMenuDo> findAll(AuthMenuDo authMenuDo) {
        return authMenuDao.findAll(authMenuDo);
    }

    @Override
    public PageChunk<AuthMenuDo> findAll(AuthMenuDo authMenuDo, Pageable pageable) {
        return authMenuDao.findAll(authMenuDo, pageable);
    }

}