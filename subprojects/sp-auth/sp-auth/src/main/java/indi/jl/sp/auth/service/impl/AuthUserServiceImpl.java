package indi.jl.sp.auth.service.impl;

import indi.jl.sp.auth.jpa.dao.AuthUserDao;
import indi.jl.sp.auth.jpa.domain.AuthUserDo;
import indi.jl.sp.security.api.encoder.Md5PasswordEncoder;
import indi.jl.sp.auth.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import indi.jl.sp.core.domain.PageChunk;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthUserServiceImpl implements AuthUserService {

    private final AuthUserDao authUserDao;

    @Autowired
    public AuthUserServiceImpl(AuthUserDao authUserDao) {
        this.authUserDao = authUserDao;
    }

    @Override
    public Optional<AuthUserDo> get(Long id) {
        return authUserDao.getById(id);
    }

    @Override
    public Optional<AuthUserDo> get(String username) {
        return authUserDao.get(username);
    }

    @Override
    public AuthUserDo save(AuthUserDo authUserDo) {
        authUserDo.setPassword(new Md5PasswordEncoder().encode(authUserDo.getPassword()));
        return authUserDao.save(authUserDo);
    }

    @Override
    public AuthUserDo update(AuthUserDo authUserDo) {
        return authUserDao.update(authUserDo);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        authUserDao.deleteByIds(ids);
    }

    @Override
    public List<AuthUserDo> findAll(AuthUserDo authUserDo) {
        return authUserDao.findAll(authUserDo);
    }

    @Override
    public PageChunk<AuthUserDo> findAll(AuthUserDo authUserDo, Pageable pageable) {
        return authUserDao.findAll(authUserDo, pageable);
    }

}