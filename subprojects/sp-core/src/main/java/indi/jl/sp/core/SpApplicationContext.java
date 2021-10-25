package indi.jl.sp.core;

import indi.jl.sp.core.exception.SpSystemException;
import indi.jl.sp.core.util.CollectionUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Lazy(false)
public class SpApplicationContext implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private static final SpApplicationContext HOLDER = new SpApplicationContext();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        HOLDER.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return HOLDER.applicationContext;
    }

    public static Object getBean(String name) throws BeansException {
        return HOLDER.applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> requiredType) throws BeansException {
        return HOLDER.applicationContext.getBean(requiredType);
    }

    public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return HOLDER.applicationContext.getBean(name, requiredType);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> beanType) {
        return HOLDER.applicationContext.getBeansOfType(beanType);
    }

    public static <T> T getBeanForTypeAndPrefix(Class<T> beanType, String prefix) {
        List<T> returnBean = new ArrayList<>();
        Map<String, T> beanMaps = HOLDER.applicationContext.getBeansOfType(beanType);
        if (CollectionUtil.isEmpty(beanMaps)) {
            throw new SpSystemException("cant find bean  by getBeanForTypeAndPrefix,type:" + beanType.getName() + "|prefix" + prefix);
        }
        beanMaps.forEach((beanName, bean) -> {
            if (beanName.startsWith(prefix)) {
                returnBean.add(bean);
            }
        });
        if (CollectionUtil.isEmpty(returnBean)) {
            throw new SpSystemException("cant find bean by getBeanForTypeAndPrefix,type:" + beanType.getName() + "|prefix" + prefix);
        }
        if (returnBean.size() > 1) {
            throw new SpSystemException("get bean is not only by getBeanForTypeAndPrefix,type:" + beanType.getName() + "|prefix" + prefix);
        }
        return returnBean.get(0);
    }

    public static <T> boolean isBeanForTypeAndPrefix(Class<T> beanType, String prefix) {
        List<T> returnBean = new ArrayList<>();
        Map<String, T> beanMaps = HOLDER.applicationContext.getBeansOfType(beanType);
        if (CollectionUtil.isEmpty(beanMaps)) {
            return false;
        }
        beanMaps.forEach((beanName, bean) -> {
            if (beanName.startsWith(prefix)) {
                returnBean.add(bean);
            }
        });
        if (CollectionUtil.isEmpty(returnBean)) {
            return false;
        }
        if (returnBean.size() > 1) {
            throw new SpSystemException("get bean is not only by getBeanForTypeAndPrefix,type:" + beanType.getName() + "|prefix" + prefix);
        }
        return true;
    }
}
