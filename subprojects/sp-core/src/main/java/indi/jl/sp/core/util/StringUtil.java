package indi.jl.sp.core.util;

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringUtil extends StringUtils {

    private StringUtil() {
    }

    public static boolean isNull(String str) {
        return isBlank(str);
    }

    public static boolean isNotNull(String str) {
        return isNotBlank(str);
    }

    public static String toEncodedString(final byte[] bytes, final Charset charset) {
        if (bytes != null) {
            return new String(bytes, charset);
        }
        return "";
    }

    public static String getString(Object object) {
        return object == null ? "" : object.toString();
    }


    public static String lowerFirst(String source) {
        if (isEmpty(source)) {
            return source;
        }
        String first = source.substring(0, 1);
        //substring(1),获取索引位置1后面所有剩余的字符串
        String after = source.substring(1);
        first = first.toLowerCase();
        return first + after;
    }

    /**
     * 将字符串中 正则表达式 中特殊字符进行 转义
     *
     * @param value
     * @return
     */
    public static String escapeSpecialWord(String value) {
        if (isEmpty(value)) {
            return value;
        }
        String[] specialWord = {"\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|"};
        for (String word : specialWord) {
            if (value.contains(word)) {
                value = value.replace(word, "\\" + word);
            }
        }
        return value;
    }


    /**
     * 将逗号分隔的string 转为Long集合
     *
     * @param str 逗号分隔的string
     * @return long集合
     */
    public static List<Long> convertLong(String str) {
        if (isEmpty(str)) {
            return null;
        }
        return Arrays.stream(str.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toList());
    }

}
