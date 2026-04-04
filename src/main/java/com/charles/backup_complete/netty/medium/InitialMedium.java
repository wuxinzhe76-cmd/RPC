package com.charles.netty.medium;

import com.charles.netty.annotation.Remote;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

@Component
public class InitialMedium implements BeanPostProcessor {


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean.getClass().isAnnotationPresent(Remote.class)) {
            Method[] methods = bean.getClass().getDeclaredMethods();
            for(Method m : methods){
                String key = bean.getClass().getInterfaces()[0].getName() + "." + m.getName();
                Map<String, BeanMethod> beanMethodMap = Medium.beanMethodMap;
                BeanMethod beanMethod = new BeanMethod();
                beanMethod.setBean(bean);
                beanMethod.setMethod(m);
                beanMethodMap.put(key, beanMethod);
            }
        }
        return bean;
    }
}
