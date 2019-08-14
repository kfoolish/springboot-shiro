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
package com.zyd.shiro.business.service;


import com.github.pagehelper.PageInfo;
import com.zyd.shiro.business.entity.User;
import com.zyd.shiro.business.vo.UserConditionVO;
import com.zyd.shiro.framework.object.AbstractService;

import java.util.List;

/**
 * @project: springboot-shiro
 * @description: 用户相关的业务处理
 * @date: 2019-08-14 3:57 PM
 * @version: 1.0
 * @website: https://yubuntu0109.github.io/
 */
public interface SysUserService extends AbstractService<User, Long> {

    /**
     * @description: 分页查询用户信息
     * @param: vo
     * @date: 2019-08-14 3:58 PM
     * @return: com.github.pagehelper.PageInfo<com.zyd.shiro.business.entity.User>
     */
    PageInfo<User> findPageBreakByCondition(UserConditionVO vo);

    /**
     * @description: (never used)更新用户最后一次登录的状态信息
     * @param: user
     * @date: 2019-08-14 3:59 PM
     * @return: com.zyd.shiro.business.entity.User
     */
    User updateUserLastLoginInfo(User user);

    /**
     * @description: 根据用户名查找指定的用户信息
     * @param: userName
     * @date: 2019-08-14 4:00 PM
     * @return: com.zyd.shiro.business.entity.User
     */
    User getByUserName(String userName);

    /**
     * @description: 通过角色id获取用户列表
     * @param: roleId
     * @date: 2019-08-14 4:00 PM
     * @return: java.util.List<com.zyd.shiro.business.entity.User>
     */
    List<User> listByRoleId(Long roleId);

}
