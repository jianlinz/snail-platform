package indi.jl.sp.data.jpa.listener;

import indi.jl.sp.data.core.service.CacheRemove;
import indi.jl.sp.data.jpa.domain.BaseDo;
import indi.jl.sp.security.api.SpAuthentication;

import javax.persistence.*;
import java.time.LocalDateTime;

public class DefaultDoLifeCycleListener {

    public static final String UNDEFINED = "undefined";

    @PrePersist
    public void prePersist(BaseDo baseDO) {
        baseDO.setCreateTime(LocalDateTime.now());
        baseDO.setUpdateTime(LocalDateTime.now());
        baseDO.setCreateUserName(null == SpAuthentication.getUserDetail() ? UNDEFINED : SpAuthentication.getUserDetail().getUsername());
        baseDO.setUpdateUserName(null == SpAuthentication.getUserDetail() ? UNDEFINED : SpAuthentication.getUserDetail().getUsername());
    }

    @PreUpdate
    public void preUpdate(BaseDo baseDO) {
        baseDO.setUpdateTime(LocalDateTime.now());
        baseDO.setUpdateUserName(null == SpAuthentication.getUserDetail() ? UNDEFINED : SpAuthentication.getUserDetail().getUsername());
    }

    @PostPersist
    public void postPersist(BaseDo baseDO) {
        if (null != baseDO) {
            CacheRemove.cleanCache(baseDO.getClass().getName());
        }
    }

    @PostUpdate
    public void postUpdate(BaseDo baseDO) {
        if (null != baseDO) {
            CacheRemove.cleanCache(baseDO.getClass().getName());
        }
    }

    @PostRemove
    public void postRemove(BaseDo baseDO) {
        if (null != baseDO) {
            CacheRemove.cleanCache(baseDO.getClass().getName());
        }
    }
}
