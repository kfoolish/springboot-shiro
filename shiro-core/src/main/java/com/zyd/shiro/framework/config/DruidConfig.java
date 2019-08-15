/**
 * MIT License
 * <p>
 * Copyright (c) 2018 yadong.zhang
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.zyd.shiro.framework.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.zyd.shiro.framework.property.DruidProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @project: springboot-shiro
 * @description: Druid Monitor的配置信息
 * @date: 2019-08-15 11:38 AM
 * @version: 1.0
 * @website: https://yubuntu0109.github.io/
 */
@Configuration
public class DruidConfig {

    @Autowired
    private DruidProperties druidProperties;

    @Bean
    public ServletRegistrationBean druidStatViewServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), druidProperties.getServletPath());

        // IP黑名单(存在共同时,deny优先于allow) : 如果满足deny的话提示:Sorry,you are not permitted to view this page.
        List<String> denyIps = druidProperties.getDenyIps();
        if (!CollectionUtils.isEmpty(denyIps)) {
            bean.addInitParameter("deny", StringUtils.collectionToDelimitedString(denyIps, ","));
        }
        // IP白名单
        List<String> allowIps = druidProperties.getAllowIps();
        if (!CollectionUtils.isEmpty(allowIps)) {
            bean.addInitParameter("allow", StringUtils.collectionToDelimitedString(allowIps, ","));
        }
        // 登录查看信息的账号密码
        bean.addInitParameter("loginUsername", druidProperties.getUsername());
        bean.addInitParameter("loginPassword", druidProperties.getPassword());
        // 禁用HTML页面上的"Reset All"功能(默认false)
        bean.addInitParameter("resetEnable", String.valueOf(druidProperties.getResetEnable()));
        return bean;
    }

    /**
     * @description: 配置Druid的StatFilter
     * @param:
     * @date: 2019-08-15 11:39 AM
     * @return: org.springframework.boot.web.servlet.FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean druidStatFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean(new WebStatFilter());
        // 添加过滤规则
        bean.addUrlPatterns("/*");
        // 排除的url
        bean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return bean;
    }
}
