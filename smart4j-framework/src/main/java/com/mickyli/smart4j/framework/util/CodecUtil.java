package com.mickyli.smart4j.framework.util;

import org.apache.commons.lang3.CharSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;

/**
 * 编码与解码操作工具类
 * Created by liqian on 2017/7/31.
 */
public final class CodecUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CodecUtil.class);

    /**
     * 将url编码
     * @param source
     * @return
     */
    public static String encodeUrl(String source){
        String target;
        try {
            target = URLEncoder.encode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("encode url failure", e);
            throw new RuntimeException(e);
        }
        return target;
    }

    /**
     * 将url解码
     * @param source
     * @return
     */
    public static String decodeUrl(String source){
        String target;
        try {
            target = URLDecoder.decode(source,"UTF-8");
        }catch (UnsupportedEncodingException e){
            LOGGER.error("decoder url failure", e);
            throw new RuntimeException(e);
        }
        return target;
    }
}
