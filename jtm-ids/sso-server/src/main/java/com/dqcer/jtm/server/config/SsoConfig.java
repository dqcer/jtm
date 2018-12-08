package com.dqcer.jtm.server.config;

import com.dqcer.sso.core.store.SsoLoginStore;
import com.dqcer.sso.core.util.JedisUtil;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: dongQin
 * @Date: 2018/12/8 13:57
 * @Description: 单点配置
 */
@Configuration
public class SsoConfig implements InitializingBean, DisposableBean {

    @Value("${sso.redis.address}")
    private String redisAddress;

    @Value("${sso.redis.expire.minite}")
    private int redisRxpireMinite;

    /**
     * Invoked by a BeanFactory on destruction of a singleton.
     *
     * @throws Exception in case of shutdown errors.
     *                   Exceptions will get logged but not rethrown to allow
     *                   other beans to release their resources too.
     */
    @Override
    public void destroy() throws Exception {
        JedisUtil.close();
    }

    /**
     * Invoked by a BeanFactory after it has set all bean properties supplied
     * (and satisfied BeanFactoryAware and ApplicationContextAware).
     * <p>This method allows the bean instance to perform initialization only
     * possible when all bean properties have been set and to throw an
     * exception in the event of misconfiguration.
     *
     * @throws Exception in the event of misconfiguration (such
     *                   as failure to set an essential property) or if initialization fails.
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        SsoLoginStore.setRedisExpireMinite(redisRxpireMinite);
        JedisUtil.init(redisAddress);
    }
}
