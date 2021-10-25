package indi.jl.sp.core.util;

import org.springframework.util.CollectionUtils;

import java.util.Collection;

public class CollectionUtil extends CollectionUtils {

    /**
     * 私有化工具类的构造函数，避免对工具类的实例化
     */
    private CollectionUtil() { }

    /**
     * 静态方法调用私有构造函数，以覆盖对构造函数的测试
     */
    static {
        new CollectionUtil();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

}