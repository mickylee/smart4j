package com.mickyli.smart4j.framework.util;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 数组工具类
 * Created by liqian on 2017/7/27.
 */
public final class ArrayUtil {

    /**
     * 数组是否为空
     * @param array
     * @return
     */
    public static boolean isEmpty(Object[] array){
        return ArrayUtils.isEmpty(array);
    }

    /**
     * 数组是否非空
     * @param array
     * @return
     */
    public static boolean isNotEmpty(Object[] array){
        return !isEmpty(array);
    }
}
