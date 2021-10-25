package indi.jl.sp.data.core.service;

public interface CacheRemoveService {

    /**
     * 根据 实体名称清楚实体相关缓存
     *
     * @param doClassName 实体类名称
     */
    void cleanCache(String doClassName);
}
