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
import com.zyd.shiro.business.entity.Resources;
import com.zyd.shiro.business.vo.ResourceConditionVO;
import com.zyd.shiro.framework.object.AbstractService;

import java.util.List;
import java.util.Map;

/**
 * @project: springboot-shiro
 * @description: 系统资源相关的业务处理
 * @date: 2019-08-14 4:05 PM
 * @version: 1.0
 * @website: https://yubuntu0109.github.io/
 */
public interface SysResourcesService extends AbstractService<Resources, Long> {

    /**
     * @description: 分页查询资源列表
     * @param: vo
     * @date: 2019-08-14 4:05 PM
     * @return: com.github.pagehelper.PageInfo<com.zyd.shiro.business.entity.Resources>
     */
    PageInfo<Resources> findPageBreakByCondition(ResourceConditionVO vo);

    /**
     * @description: 获取用户所拥有的资源列表
     * @param: map
     * @date: 2019-08-14 4:06 PM
     * @return: java.util.List<com.zyd.shiro.business.entity.Resources>
     */
    List<Resources> listUserResources(Map<String, Object> map);

    /**
     * @description: 通过角色id获取ztree使用的资源列表
     * @param: rid
     * @date: 2019-08-14 4:06 PM
     * @return: java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     */
    List<Map<String, Object>> queryResourcesListWithSelected(Long rid);

    /**
     * @description: 获取资源的url和Permission
     * @param:
     * @date: 2019-08-14 4:07 PM
     * @return: java.util.List<com.zyd.shiro.business.entity.Resources>
     */
    List<Resources> listUrlAndPermission();

    /**
     * @description: 获取所有可用的菜单资源
     * @param:
     * @date: 2019-08-14 4:07 PM
     * @return: java.util.List<com.zyd.shiro.business.entity.Resources>
     */
    List<Resources> listAllAvailableMenu();

    /**
     * @description: (never used)获取父级资源下所有menu资源
     * @param: pid
     * @date: 2019-08-14 4:08 PM
     * @return: java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     */
    List<Map<String, Object>> listChildMenuByPid(Long pid);

    /**
     * @description: 通过用户id获取用户关联的所有资源
     * @param: userId
     * @date: 2019-08-14 4:08 PM
     * @return: java.util.List<com.zyd.shiro.business.entity.Resources>
     */
    List<Resources> listByUserId(Long userId);
}
