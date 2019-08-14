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
package com.zyd.shiro.persistence.mapper;

import com.zyd.shiro.business.vo.UserConditionVO;
import com.zyd.shiro.persistence.beans.SysUser;
import com.zyd.shiro.plugin.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @project: springboot-shiro
 * @description: 操控系统用户信息
 * @date: 2019-08-14 2:47 PM
 * @version: 1.0
 * @website: https://yubuntu0109.github.io/
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * @description: 分页查询用户信息
     * @param: vo
     * @date: 2019-08-14 2:34 PM
     * @return: java.util.List<com.zyd.shiro.persistence.beans.SysUser>
     */
    List<SysUser> findPageBreakByCondition(UserConditionVO vo);

    /**
     * @description: 通过角色Id获取用户列表
     * @param: roleId
     * @date: 2019-08-14 2:34 PM
     * @return: java.util.List<com.zyd.shiro.persistence.beans.SysUser>
     */
    List<SysUser> listByRoleId(Long roleId);

}
