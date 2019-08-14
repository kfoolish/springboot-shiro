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
package com.zyd.shiro.persistence.mapper;

import com.zyd.shiro.business.vo.ResourceConditionVO;
import com.zyd.shiro.persistence.beans.SysResources;
import com.zyd.shiro.plugin.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @project: springboot-shiro
 * @description: 操控系统资源信息
 * @date: 2019-08-14 3:06 PM
 * @version: 1.0
 * @website: https://yubuntu0109.github.io/
 */
@Repository
public interface SysResourceMapper extends BaseMapper<SysResources> {

    /**
     * @description: 分页查询系统资源
     * @param: vo
     * @date: 2019-08-14 3:08 PM
     * @return: java.util.List<com.zyd.shiro.persistence.beans.SysResources>
     */
    List<SysResources> findPageBreakByCondition(ResourceConditionVO vo);

    /**
     * @description: 获取用户的资源列表
     * @param: map
     * @date: 2019-08-14 3:10 PM
     * @return: java.util.List<com.zyd.shiro.persistence.beans.SysResources>
     */
    List<SysResources> listUserResources(Map<String, Object> map);

    /**
     * @description: 根据角色id获取ztree使用的资源列表, 代码参考自 http://blog.csdn.net/poorcoder_/article/details/71374002
     * @param: rid
     * @date: 2019-08-14 3:12 PM
     * @return: java.util.List<com.zyd.shiro.persistence.beans.SysResources>
     */
    List<SysResources> queryResourcesListWithSelected(Long rid);

    /**
     * @description: 获取资源的url和Permission
     * @param:
     * @date: 2019-08-14 3:13 PM
     * @return: java.util.List<com.zyd.shiro.persistence.beans.SysResources>
     */
    List<SysResources> listUrlAndPermission();

    /**
     * @description: 获取所有可用的菜单资源
     * @param:
     * @date: 2019-08-14 3:15 PM
     * @return: java.util.List<com.zyd.shiro.persistence.beans.SysResources>
     */
    List<SysResources> listAllAvailableMenu();

    /**
     * @description: 通过父级菜单id获取父级资源下所有menu资源
     * @param: pid
     * @date: 2019-08-14 3:16 PM
     * @return: java.util.List<com.zyd.shiro.persistence.beans.SysResources>
     */
    List<SysResources> listMenuResourceByPid(Long pid);

    /**
     * @description: 通过用户id获取用户关联的所有资源
     * @param: userId
     * @date: 2019-08-14 3:18 PM
     * @return: java.util.List<com.zyd.shiro.persistence.beans.SysResources>
     */
    List<SysResources> listByUserId(Long userId);
}
