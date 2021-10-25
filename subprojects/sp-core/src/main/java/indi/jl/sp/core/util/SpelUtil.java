package indi.jl.sp.core.util;

import indi.jl.sp.core.SpApplicationContext;
import indi.jl.sp.core.service.SpelParserService;

/**
 * spel表达式工具类
 */
public class SpelUtil {

    private SpelUtil() {
    }

    /**
     * 解析spel
     *
     * @param spel spel
     * @return 解析后返回值
     */
    public static String parse(String spel) {
        return SpApplicationContext.getBean(SpelParserService.class).parse(spel);
    }


    /**
     * 解析spel
     *
     * @param spel spel
     * @param clz  返回值类型
     * @param <T>  泛型
     * @return 解析后的spel
     */
    public static <T> T parse(String spel, Class<T> clz) {
        return SpApplicationContext.getBean(SpelParserService.class).parse(spel, clz);
    }
}
