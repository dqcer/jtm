package com.dqcer.sso.core.login;

import com.dqcer.sso.core.conf.Conf;
import com.dqcer.sso.core.store.SsoLoginStore;
import com.dqcer.sso.core.store.SsoSessionIdHelper;
import com.dqcer.sso.core.user.SsoUser;
import com.dqcer.sso.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xuxueli 2018-11-15 15:54:40
 */
@Slf4j
public class SsoTokenLoginHelper {

    /**
     * client login
     *
     * @param sessionId
     * @param xxlUser
     */
    public static void login(String sessionId, SsoUser xxlUser) {

        String storeKey = SsoSessionIdHelper.parseStoreKey(sessionId);
        if (storeKey == null) {
            throw new RuntimeException("parseStoreKey Fail, sessionId:" + sessionId);
        }

        SsoLoginStore.put(storeKey, xxlUser);
    }

    /**
     * client logout
     *
     * @param sessionId
     */
    public static void logout(String sessionId) {

        String storeKey = SsoSessionIdHelper.parseStoreKey(sessionId);
        if (storeKey == null) {
            return;
        }

        SsoLoginStore.remove(storeKey);
    }
    /**
     * client logout
     *
     * @param request
     */
    public static void logout(HttpServletRequest request) {
        String headerSessionId = request.getHeader(Conf.SSO_SESSIONID);
        logout(headerSessionId);
    }


    /**
     * login check
     *
     * @param sessionId
     * @return
     */
    public static SsoUser loginCheck(String  sessionId){

        if (!StringUtils.hasLength(sessionId)){
            log.error("sessionId 不能为空");
            return null;
        }

        String storeKey = SsoSessionIdHelper.parseStoreKey(sessionId);
        if (storeKey == null) {
            return null;
        }

        SsoUser xxlUser = SsoLoginStore.get(storeKey);
        if (xxlUser != null) {
            String version = SsoSessionIdHelper.parseVersion(sessionId);
            if (xxlUser.getVersion().equals(version)) {

                // After the expiration time has passed half, Auto refresh
                if ((System.currentTimeMillis() - xxlUser.getExpireFreshTime()) > xxlUser.getExpireMinite()/2) {
                    xxlUser.setExpireFreshTime(System.currentTimeMillis());
                    SsoLoginStore.put(storeKey, xxlUser);
                }

                return xxlUser;
            }
        }
        return null;
    }


    /**
     * login check
     *
     * @param request
     * @return
     */
    public static SsoUser loginCheck(HttpServletRequest request){
        String headerSessionId = request.getHeader(Conf.SSO_SESSIONID);
        return loginCheck(headerSessionId);
    }


}
