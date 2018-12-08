package com.dqcer.sso.core.store;

import com.dqcer.sso.core.conf.Conf;
import com.dqcer.sso.core.user.SsoUser;
import com.dqcer.sso.core.util.JedisUtil;

/**
 * @Author: dongQin
 * @Date: 2018/12/7 21:42
 * @Description: 本地登录存储
 */
public class SsoLoginStore {

    /**
     * 登录态有效期窗口，默认24H，当登录态有效期窗口过半时，自动顺延一个周期
     */
    private static int redisExpireMinite = 1440;

    /**
     * 设置时间
     * @param redisExpireMinite
     */
    public static void setRedisExpireMinite(int redisExpireMinite) {
        if (redisExpireMinite < 30) {
            redisExpireMinite = 30;
        }
        SsoLoginStore.redisExpireMinite = redisExpireMinite;
    }
    public static int getRedisExpireMinite() {
        return redisExpireMinite;
    }

    /**
     * 获取SsoUser
     * @param storeKey
     * @return
     */
    public static SsoUser get(String storeKey) {

        String redisKey = redisKey(storeKey);
        Object objectValue = JedisUtil.getObjectValue(redisKey);
        if (objectValue != null) {
            SsoUser xxlUser = (SsoUser) objectValue;
            return xxlUser;
        }
        return null;
    }

    /**
     * remove
     *
     * @param storeKey
     */
    public static void remove(String storeKey) {
        String redisKey = redisKey(storeKey);
        JedisUtil.del(redisKey);
    }

    /**
     * put
     *
     * @param storeKey
     * @param xxlUser
     */
    public static void put(String storeKey, SsoUser xxlUser) {
        String redisKey = redisKey(storeKey);
        JedisUtil.setObjectValue(redisKey, xxlUser, redisExpireMinite * 60);  // minite to second
    }

    private static String redisKey(String sessionId){
        return Conf.SSO_SESSIONID.concat("#").concat(sessionId);
    }
}
