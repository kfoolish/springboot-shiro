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
package com.zyd.shiro.framework.holder;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @project: springboot-shiro
 * @description: SpringContext的基础支架
 * @date: 2019-08-16 3:18 PM
 * @version: 1.0
 * @website: https://yubuntu0109.github.io/
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext appContext = null;

    /**
     * @description: (never used)通过name获取指定的Bean
     * @param: name
     * @date: 2019-08-16 3:20 PM
     * @return: java.lang.Object
     */
    public static Object getBean(String name) {
        return appContext.getBean(name);

    }

    /**
     * @description: 通过class获取指定的Bean
     * @param: clazz
     * @date: 2019-08-16 3:20 PM
     * @return: T
     */
    public static <T> T getBean(Class<T> clazz) {
        return appContext.getBean(clazz);
    }

    /**
     * @description: (never used)通过name及clazz获取指定的Bean
     * @param: name
     * @param: clazz
     * @date: 2019-08-16 3:21 PM
     * @return: T
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return appContext.getBean(name, clazz);
    }

    /**
     * @description: 初始化applicationContext
     * @param: applicationContext
     * @date: 2019-08-16 3:21 PM
     * @return: void
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (appContext == null) {
            appContext = applicationContext;
        }
    }
}
