package indi.jl.sp.data.core.service;

import indi.jl.sp.core.SpApplicationContext;
import indi.jl.sp.core.util.StringUtil;

public class CacheRemove {

    private CacheRemove() {
    }

    /**
     * 根据 实体名称清楚实体相关缓存
     *
     * @param doClassName 实体类名称
     */
    public static void cleanCache(String doClassName) {
        if (StringUtil.isEmpty(doClassName)) {
            return;
        }
        SpApplicationContext.getBean(CacheRemoveService.class).cleanCache(doClassName);
    }
}
