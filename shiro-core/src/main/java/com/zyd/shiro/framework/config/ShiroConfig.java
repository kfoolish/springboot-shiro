/**
 * MIT License
 * Copyright (c) 2018 yadong.zhang
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.zyd.shiro.framework.config;

import com.zyd.shiro.business.service.ShiroService;
import com.zyd.shiro.business.shiro.credentials.RetryLimitCredentialsMatcher;
import com.zyd.shiro.business.shiro.realm.ShiroRealm;
import com.zyd.shiro.framework.property.RedisProperties;
import com.zyd.shiro.framework.redis.CustomRedisManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;

import java.util.Map;

/**
 * @project: springboot-shiro
 * @description: Shiro配置类
 * @date: 2019-08-15 11:42 AM
 * @version: 1.0
 * @website: https://yubuntu0109.github.io/
 */
@Configuration
@Order(-1)
public class ShiroConfig {

    @Autowired
    private ShiroService shiroService;
    @Autowired
    private RedisProperties redisProperties;

    /**
     * @description: 将Shiro安全管理器注入到Bean的实例
     * @param: securityManager
     * @date: 2019-08-15 11:48 AM
     * @return: org.springframework.beans.factory.config.MethodInvokingFactoryBean
     */
    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean(SecurityManager securityManager) {
        MethodInvokingFactoryBean bean = new MethodInvokingFactoryBean();
        bean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        bean.setArguments(securityManager);
        return bean;
    }

    /**
     * @description: 生命周期处理器
     * @param:
     * @date: 2019-08-15 11:48 AM
     * @return: org.apache.shiro.spring.LifecycleBeanPostProcessor
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /*
     * Filter Chain定义说明:
     * 1、一个URL可以配置多个Filter,使用逗号分隔
     * 2、当设置多个过滤器时,全部验证通过,才视为通过
     * 3、部分过滤器可指定参数,如perms,roles
     */

    /**
     * @description: 配置Shiro过滤器
     * @param: securityManager
     * @date: 2019-08-15 11:49 AM
     * @return: org.apache.shiro.spring.web.ShiroFilterFactoryBean
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/passport/login/");
        // 指定登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");
        // 指定未授权操作要跳转的链接
        shiroFilterFactoryBean.setUnauthorizedUrl("/error/403");
        // 配置数据库中的resource
        Map<String, String> filterChainDefinitionMap = shiroService.loadFilterChainDefinitions();
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * @description: 开启shiro注解模式(如果使用注解模式, 必须采用cglib针对类进行代理)
     * @param:
     * @date: 2019-08-15 11:50 AM
     * @return: org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    /**
     * @description: 开启Shiro-aop注解支持(采用cglib针对类进行代理)
     * @param: securityManager
     * @date: 2019-08-15 11:54 AM
     * @return: org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * @description: 配置安全管理器
     * @param: authRealm
     * @date: 2019-08-15 11:51 AM
     * @return: org.apache.shiro.mgt.SecurityManager
     */
    @Bean(name = "securityManager")
    public SecurityManager securityManager(@Qualifier("shiroRealm") ShiroRealm authRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 关联自定义realm
        securityManager.setRealm(authRealm);
        // 关联安全管理器
        securityManager.setCacheManager(redisCacheManager());
        // 自定义Session管理器
        securityManager.setSessionManager(sessionManager());
        // 关联Cookie管理器
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    /**
     * @description: 配置自定义Realm
     * @param: matcher
     * @date: 2019-08-15 11:51 AM
     * @return: com.zyd.shiro.business.shiro.realm.ShiroRealm
     */
    @Bean(name = "shiroRealm")//the parameter is never used
    public ShiroRealm shiroRealm(@Qualifier("credentialsMatcher") RetryLimitCredentialsMatcher matcher) {
        ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setCredentialsMatcher(credentialsMatcher()); //设置凭证匹配器
        return shiroRealm;
    }

    /**
     * @description: 自定义凭证匹配器
     * @param:
     * @date: 2019-08-15 11:53 AM
     * @return: com.zyd.shiro.business.shiro.credentials.RetryLimitCredentialsMatcher
     */
    @Bean(name = "credentialsMatcher")
    public RetryLimitCredentialsMatcher credentialsMatcher() {
        return new RetryLimitCredentialsMatcher();
    }


    /**
     * @description: Redis管理器(使用的是shiro - redis开源插件)
     * @param:
     * @date: 2019-08-15 11:54 AM
     * @return: org.crazycake.shiro.RedisManager
     */
    public RedisManager redisManager() {
        CustomRedisManager redisManager = new CustomRedisManager();
        redisManager.setHost(redisProperties.getHost());
        redisManager.setPort(redisProperties.getPort());
        redisManager.setDatabase(redisProperties.getDatabase());
        redisManager.setTimeout(redisProperties.getTimeout());
        redisManager.setPassword(redisProperties.getPassword());
        return redisManager;
    }

    /**
     * @description: Redis缓存管理器(使用的是shiro - redis开源插件)
     * @param:
     * @date: 2019-08-15 11:55 AM
     * @return: org.crazycake.shiro.RedisCacheManager
     */
    @Bean
    public RedisCacheManager redisCacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager()); //设置Redis管理器
        return redisCacheManager;
    }


    /**
     * @description: RedisSessionDAO:shiro sessionDao层的实现(使用的是shiro-redis开源插件)
     * @param:
     * @date: 2019-08-15 11:56 AM
     * @return: org.crazycake.shiro.RedisSessionDAO
     */
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager()); //设置Redis管理器
        return redisSessionDAO;
    }

    /**
     * @description: Session管理器
     * @param:
     * @date: 2019-08-15 11:57 AM
     * @return: org.apache.shiro.web.session.mgt.DefaultWebSessionManager
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(redisProperties.getExpire() * 1000L);
        sessionManager.setSessionDAO(redisSessionDAO());
        return sessionManager;
    }

    /**
     * @description: 设置Cookie(RememberMe功能)
     * @param:
     * @date: 2019-08-15 11:58 AM
     * @return: org.apache.shiro.web.servlet.SimpleCookie
     */
    public SimpleCookie rememberMeCookie() {
        // 这个参数是cookie的名称,对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        // 记住我cookie生效时间30天,单位秒,默认情况下永不过期
        simpleCookie.setMaxAge(redisProperties.getExpire());
        return simpleCookie;
    }

    /**
     * @description: Cookie管理器(开启RememberMe功能)
     * @param:
     * @date: 2019-08-15 11:58 AM
     * @return: org.apache.shiro.web.mgt.CookieRememberMeManager
     */
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        //设置rememberMe cookie加密的密钥,建议每个项目都不一样,默认AES算法:密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("1QWLxg+NYmxraMoxAXu/Iw=="));
        return cookieRememberMeManager;
    }
}
