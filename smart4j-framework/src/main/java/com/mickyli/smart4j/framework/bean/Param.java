package com.mickyli.smart4j.framework.bean;

import com.mickyli.smart4j.framework.util.CastUtil;

import java.util.Map;

/**
 * 请求参数对象
 * Created by liqian on 2017/7/28.
 */
public class Param {
    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public Long getLong(String name){
        return CastUtil.castLong(paramMap.get(name));
    }

    public String getString(String name){
        return CastUtil.castString(paramMap.get(name));
    }

    public Boolean getBoolean(String name){
        return CastUtil.castBoolean(paramMap.get(name));
    }

    public Double getDouble(String name){
        return CastUtil.castDouble(paramMap.get(name));
    }

    public Integer getInt(String name){
        return CastUtil.castInt(paramMap.get(name));
    }
}
