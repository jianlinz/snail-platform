package indi.jl.sp.core.util;

import indi.jl.sp.core.exception.SpSystemException;
import org.apache.commons.lang3.StringUtils;

import java.beans.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * java bean 工具类
 */
public class BeanUtil {

    private BeanUtil() {
    }


    /**
     * 反射调用get方法
     *
     * @param po        对象
     * @param fieldName 属性名
     * @return 返回属性值
     * @ {@inheritDoc}
     */
    public static Object getValue(Object po, String fieldName) {
        if (null == po || StringUtils.isEmpty(fieldName)) {
            throw new SpSystemException("parameter is null");
        }
        try {
            Field field = getField(po.getClass(), fieldName);
            field.setAccessible(true);
            return field.get(po);
        } catch (Exception e) {
            throw new SpSystemException(e);
        }
    }


    /**
     * 已知对象的field时获取对象的值
     *
     * @param source 具体对象
     * @param field  字段
     * @return Object 值
     */
    public static Object getValue(Object source, Field field) {
        try {
            field.setAccessible(true);
            return field.get(source);
        } catch (Exception e) {
            throw new SpSystemException(e);
        }
    }

    /**
     * 获取所有属性排除Transient，包括父类属性
     *
     * @param entityClass 业务实体类
     * @return 属性列表
     */
    public static List<Field> getAllFields(Class<?> entityClass) {
        Field[] fields = entityClass.getDeclaredFields();
        return getAllFields(entityClass.getSuperclass(), new ArrayList<>(Arrays.asList(fields)), true);
    }

    private static List<Field> getAllFields(Class<?> entityClass, List<Field> fields, boolean ignoreTransient) {
        if (Object.class.equals(entityClass)) {
            //排除Transient
            Iterator<Field> it = fields.iterator();
            while (it.hasNext()) {
                Field next = it.next();
                if ("serialVersionUID".equals(next.getName())) {
                    it.remove();
                    continue;
                }
                if (ignoreTransient) {
                    if (null != next.getAnnotation(Transient.class)) {
                        it.remove();
                    }
                }
            }

            return fields;
        }
        Collections.addAll(fields, entityClass.getDeclaredFields());
        return getAllFields(entityClass.getSuperclass(), fields, ignoreTransient);
    }

    /**
     * 获取非空属性列表
     */
    public static String[] getNotNullProperties(Object obj) {
        if (null == obj) {
            return new String[]{};
        }
        Set<String> properties = new HashSet<>();
        for (Field field : getAllFields(obj.getClass())) {
            if (null != getValue(obj, field)) {
                properties.add(field.getName());
            }
        }
        return properties.toArray(new String[properties.size()]);
    }

    /**
     * 获取类及父类的属性
     *
     * @param clazz     类
     * @param fieldName 字段
     * @return 字段
     */
    public static Field getField(Class clazz, String fieldName) {
        if (clazz == Object.class) {
            throw new SpSystemException("class does not have this field :" + fieldName);
        }
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        return getField(clazz.getSuperclass(), fieldName);

    }


    /**
     * 通过反射调用get方法获取value
     *
     * @param fieldName 成员变量名称
     * @return get返回值
     */
    public static Object readValue(Object source, String fieldName) {
        try {
            Method[] m = source.getClass().getMethods();
            for (int i = 0; i < m.length; i++) {
                if (("get" + fieldName).toLowerCase().equals(m[i].getName().toLowerCase())) {
                    return m[i].invoke(source);
                }
            }
        } catch (Exception e) {
            throw new SpSystemException("can't invoke get by " + source.getClass() + ":" + fieldName);
        }
        return null;
    }


    /**
     * 设置属性值
     *
     * @param source    对象
     * @param fieldName 属性名
     * @param value     属性值
     * @ {@inheritDoc}
     */
    public static void setValue(Object source, String fieldName, Object value) {
        if (null == source || StringUtils.isEmpty(fieldName)) {
            throw new SpSystemException("parameter is null");
        }
        try {
            Field field = getField(source.getClass(), fieldName);
            field.setAccessible(true);
            field.set(source, value);
        } catch (Exception e) {
            throw new SpSystemException(e);
        }
    }
}
