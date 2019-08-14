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
import com.zyd.shiro.business.entity.Role;
import com.zyd.shiro.business.vo.RoleConditionVO;
import com.zyd.shiro.framework.object.AbstractService;

import java.util.List;
import java.util.Map;

/**
 * @project: springboot-shiro
 * @description: 角色相关的业务处理
 * @date: 2019-08-14 4:01 PM
 * @version: 1.0
 * @website: https://yubuntu0109.github.io/
 */
public interface SysRoleService extends AbstractService<Role, Long> {

    /**
     * @description: 通过用户id获取ztree使用的角色列表
     * @param: uid
     * @date: 2019-08-14 4:02 PM
     * @return: java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     */
    List<Map<String, Object>> queryRoleListWithSelected(Integer uid);

    /**
     * @description: 分页查询角色列表
     * @param: vo
     * @date: 2019-08-14 4:02 PM
     * @return: com.github.pagehelper.PageInfo<com.zyd.shiro.business.entity.Role>
     */
    PageInfo<Role> findPageBreakByCondition(RoleConditionVO vo);

    /**
     * @description: 通过用户id获取用户所拥有的角色信息
     * @param: userId
     * @date: 2019-08-14 4:03 PM
     * @return: java.util.List<com.zyd.shiro.business.entity.Role>
     */
    List<Role> listRolesByUserId(Long userId);
}
