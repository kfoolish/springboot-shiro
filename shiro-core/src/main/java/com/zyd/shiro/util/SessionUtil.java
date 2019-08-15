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
package com.zyd.shiro.util;

import com.zyd.shiro.business.consts.SessionConst;
import com.zyd.shiro.business.entity.User;
import com.zyd.shiro.framework.holder.RequestHolder;

import java.util.UUID;

/**
 * @project: springboot-shiro
 * @description: Session工具类
 * @date: 2019-08-15 11:04 AM
 * @version: 1.0
 * @website: https://yubuntu0109.github.io/
 */
public class SessionUtil {

    /**
     * @description: 获取session中的用户信息
     * @param:
     * @date: 2019-08-15 11:04 AM
     * @return: com.zyd.shiro.business.entity.User
     */
    public static User getUser() {
        return (User) RequestHolder.getSession(SessionConst.USER_SESSION_KEY);
    }

    /**
     * @description: 将用户信息添加到session
     * @param: user
     * @date: 2019-08-15 11:05 AM
     * @return: void
     */
    public static void setUser(User user) {
        RequestHolder.setSession(SessionConst.USER_SESSION_KEY, user);
    }

    /**
     * @description: 删除session中的用户信息
     * @param:
     * @date: 2019-08-15 11:05 AM
     * @return: void
     */
    public static void removeUser() {
        RequestHolder.removeSession(SessionConst.USER_SESSION_KEY);
    }

    /**
     * @description: 获取session中的Token信息
     * @param: key
     * @date: 2019-08-15 11:09 AM
     * @return: java.lang.String
     */
    public static String getToken(String key) {
        return (String) RequestHolder.getSession(key);
    }

    /**
     * @description: 将Token信息添加到session
     * @param: key
     * @date: 2019-08-15 11:09 AM
     * @return: void
     */
    public static void setToken(String key) {
        RequestHolder.setSession(key, UUID.randomUUID().toString());
    }

    /**
     * @description: 删除session中的Token信息
     * @param: key
     * @date: 2019-08-15 11:10 AM
     * @return: void
     */
    public static void removeToken(String key) {
        RequestHolder.removeSession(key);
    }

    /**
     * @description: 删除所有的session信息
     * @param:
     * @date: 2019-08-15 11:11 AM
     * @return: void
     */
    public static void removeAllSession() {
        String[] keys = RequestHolder.getSessionKeys();
        if (keys != null && keys.length > 0) {
            for (String key : keys) {
                RequestHolder.removeSession(key);
            }
        }
    }
}
