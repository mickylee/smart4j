package com.mickyli.smart4j.framework;

import com.mickyli.smart4j.framework.helper.BeanHelper;
import com.mickyli.smart4j.framework.helper.ClassHelper;
import com.mickyli.smart4j.framework.helper.ControllerHelper;
import com.mickyli.smart4j.framework.helper.IocHelper;
import com.mickyli.smart4j.framework.util.ClassUtil;

/**
 * 加载helper类
 * Created by liqian on 2017/7/28.
 */
public final class HelperLoader {

    public static void init(){
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };

        for (Class<?> cls : classList){
            ClassUtil.loadClass(cls.getName());
        }
    }
}
