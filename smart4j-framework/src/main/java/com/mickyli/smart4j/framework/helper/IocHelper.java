package com.mickyli.smart4j.framework.helper;

import com.mickyli.smart4j.framework.annotation.Inject;
import com.mickyli.smart4j.framework.util.ArrayUtil;
import com.mickyli.smart4j.framework.util.CollectionUtil;
import com.mickyli.smart4j.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入助手类
 * Created by liqian on 2017/7/27.
 */
public class IocHelper {
    static {
        //获取所有的bean类和bean实例的映射关系
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (CollectionUtil.isNotEmpty(beanMap)){
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()){
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                Field[] fields = beanClass.getDeclaredFields();
                if(ArrayUtil.isNotEmpty(fields)){
                    for (Field beanField : fields){
                        if(beanField.isAnnotationPresent(Inject.class)){
                            //获取field对应的bean实例
                            Class<?> beanFieldClass = beanField.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if (beanFieldInstance != null){
                                //通过反射初始化beanField的值
                                ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                            }
                        }
                    }
                }

            }
        }

    }
}
