package com.dqcer.jtm.sso.web.config;

import com.dqcer.sso.core.conf.Conf;
import com.dqcer.sso.core.filter.XxlSsoWebFilter;
import com.dqcer.sso.core.util.JedisUtil;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: dongQin
 * @Date: 2018/12/8 17:58
 * @Description: sso 配置
 */
@Configuration
public class SsoConfig implements DisposableBean {

    @Value("${sso.server}")
    private String xxlSsoServer;

    @Value("${sso.logout.path}")
    private String xxlSsoLogoutPath;

    @Value("${sso.excluded.paths}")
    private String xxlSsoExcludedPaths;

    @Value("${sso.redis.address}")
    private String xxlSsoRedisAddress;

    /**
     * Invoked by a BeanFactory on destruction of a singleton.
     *
     * @throws Exception in case of shutdown errors.
     *                   Exceptions will get logged but not rethrown to allow
     *                   other beans to release their resources too.
     */
    @Override
    public void destroy() throws Exception {

        // xxl-sso, redis close
        JedisUtil.close();
    }

    @Bean
    public FilterRegistrationBean ssoFilterRegistration(){
        // xxl-sso, redis init
        JedisUtil.init(xxlSsoRedisAddress);

        // xxl-sso, filter init
        FilterRegistrationBean registration = new FilterRegistrationBean();

        registration.setName("XxlSsoWebFilter");
        registration.setOrder(1);
        registration.addUrlPatterns("/*");
        registration.setFilter(new XxlSsoWebFilter());
        registration.addInitParameter(Conf.SSO_SERVER, xxlSsoServer);
        registration.addInitParameter(Conf.SSO_LOGOUT_PATH, xxlSsoLogoutPath);
        registration.addInitParameter(Conf.SSO_EXCLUDED_PATHS, xxlSsoExcludedPaths);

        return registration;
    }
}
