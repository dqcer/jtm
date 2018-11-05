package com.dqcer.jtm.sso.util;

/**
 * @Author: dongQin
 * @Date: 2018/11/5 15:54
 * @Description: string tools class
 */

public class StringTools {

    public static boolean isNullOrEmpty(String str) {
        return null == str || "".equals(str) || "null".equals(str);
    }

    public static boolean isNullOrEmpty(Object obj) {
        return null == obj || "".equals(obj);
    }
}
