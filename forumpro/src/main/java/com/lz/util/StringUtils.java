package com.lz.util;

import com.lz.exception.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.RecursiveTask;

public class StringUtils extends org.apache.commons.lang3.StringUtils {
    private static Logger logger= LoggerFactory.getLogger(StringUtils.class);
    public static String isoToUtf8(String str)  {
        try {
            str=new String(str.getBytes("ISO8859-1"),"UTF-8");
            return str;
        } catch (UnsupportedEncodingException e) {
            logger.error("字符转换错误",e);
            e.printStackTrace();
            throw new DataAccessException("字符转换错误",e);
        }

    }
}
