package indi.jl.sp.core.util;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.DefaultBaseTypeLimitingValidator;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import indi.jl.sp.core.constant.DateConstant;
import indi.jl.sp.core.exception.SpSystemException;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

public class JsonUtil {

    /**
     * 私有化工具类的构造函数，避免对工具类的实例化
     */
    private JsonUtil() {
    }

    private static final ObjectMapper objectMapper = buildObjectMapper();

    /**
     * 全局ObjectMapper获取都通过此方法 保证objectMapper声明的配置一致
     *
     * @return objectMapper
     */
    public static ObjectMapper getObjectMapper() {
        return buildObjectMapper();
    }

    /**
     * 全局ObjectMapper获取都通过此方法 保证objectMapper声明的配置一致
     * 序列化后带有java类型
     *
     * @return objectMapper
     */
    public static ObjectMapper getObjectMapperWithJavaType() {
        ObjectMapper objectMapper = buildObjectMapper();
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        return objectMapper;
    }

    /**
     * 全局ObjectMapper都需要通过此方法获得实例
     *
     * @return objectMapper
     */
    private static ObjectMapper buildObjectMapper() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DateConstant.DATE_TIME_FORMATTER));
        builder.serializerByType(LocalDate.class, new LocalDateSerializer(DateConstant.DATE_FORMATTER));
        builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(DateConstant.DATE_TIME_FORMATTER));
        builder.deserializerByType(LocalDate.class, new LocalDateDeserializer(DateConstant.DATE_FORMATTER));
        return builder.build();
    }

    /**
     * 将对象转换为 JSON 字符串
     *
     * @param obj 容器对象
     * @return JSON 字符串
     */
    public static String toJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
            throw new SpSystemException(e);
        }
    }

    /**
     * 将 JSON 字符串转换为相应容器类型的对象
     *
     * @param str JSON 字符串
     * @param clz 容器对象类型
     * @return 容器对象
     */
    public static <T> T parse(String str, Class<T> clz) {
        try {
            return objectMapper.readValue(str, clz);
        } catch (IOException e) {
            throw new SpSystemException(e);
        }
    }

    /**
     * 将 JSON 字符串转换为相应容器类型的对象
     *
     * @param str JSON 字符串
     * @param t   容器对象类型
     * @param g   容器对象类型内嵌套泛型  如T<G>
     * @return 容器对象
     */
    public static <T, G> T parseGenericities(String str, Class<T> t, Class<G> g) {
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(t, g);
            return objectMapper.readValue(str, javaType);
        } catch (IOException e) {
            throw new SpSystemException(e);
        }
    }

    /**
     * 反序列化集合类型并指定泛型
     *
     * @param str JSON 字符串
     * @param t   集合内泛型
     * @param c   集合类型  如T<G>
     * @return 容器对象
     */
    public static <T> Collection<T> parseCollectGenericities(String str, Class<T> t, Class<? extends Collection> c) {
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructType(t);
            CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(c, javaType);
            return objectMapper.readValue(str, collectionType);
        } catch (IOException e) {
            throw new SpSystemException(e);
        }
    }

    /**
     * 将 JSON 字符串转换为相应容器类型的对象
     *
     * @param node JsonNode
     * @param clz  容器对象类型
     * @return 容器对象
     */
    public static <T> T convert(JsonNode node, Class<T> clz) {
        return objectMapper.convertValue(node, clz);
    }
}
