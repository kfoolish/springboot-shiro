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
package com.zyd.shiro.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zyd.shiro.business.entity.Role;
import com.zyd.shiro.business.service.SysRoleService;
import com.zyd.shiro.business.vo.RoleConditionVO;
import com.zyd.shiro.persistence.beans.SysRole;
import com.zyd.shiro.persistence.mapper.SysRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @project: springboot-shiro
 * @description: 实现角色相关的业务处理
 * @date: 2019-08-17 11:29 AM
 * @version: 1.0
 * @website: https://yubuntu0109.github.io/
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper roleMapper;

    /**
     * @description: 获取ztree使用的角色列表
     * @param: userId
     * @date: 2019-08-17 11:30 AM
     * @return: java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     */
    @Override
    public List<Map<String, Object>> queryRoleListWithSelected(Integer userId) {
        List<SysRole> sysRole = roleMapper.queryRoleListWithSelected(userId);
        if (CollectionUtils.isEmpty(sysRole)) {
            return null;
        }
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        for (SysRole role : sysRole) {
            map = new HashMap<String, Object>(3);
            map.put("id", role.getId());
            map.put("pId", 0);
            map.put("checked", role.getSelected() != null && role.getSelected() == 1);
            map.put("name", role.getDescription());
            mapList.add(map);
        }
        return mapList;
    }

    /**
     * @description: 分页查询系统角色信息
     * @param: vo
     * @date: 2019-08-17 11:30 AM
     * @return: com.github.pagehelper.PageInfo<com.zyd.shiro.business.entity.Role>
     */
    @Override
    public PageInfo<Role> findPageBreakByCondition(RoleConditionVO vo) {
        PageHelper.startPage(vo.getPageNumber(), vo.getPageSize());
        List<SysRole> sysRoles = roleMapper.findPageBreakByCondition(vo);
        if (CollectionUtils.isEmpty(sysRoles)) {
            return null;
        }
        List<Role> roles = new ArrayList<>();
        for (SysRole r : sysRoles) {
            roles.add(new Role(r));
        }
        PageInfo bean = new PageInfo<SysRole>(sysRoles);
        bean.setList(roles);
        return bean;
    }

    /**
     * @description: 获取用户所拥有的角色信息
     * @param: userId
     * @date: 2019-08-17 11:32 AM
     * @return: java.util.List<com.zyd.shiro.business.entity.Role>
     */
    @Override
    public List<Role> listRolesByUserId(Long userId) {
        List<SysRole> sysRoles = roleMapper.listRolesByUserId(userId);
        if (CollectionUtils.isEmpty(sysRoles)) {
            return null;
        }
        List<Role> roles = new ArrayList<>();
        for (SysRole r : sysRoles) {
            roles.add(new Role(r));
        }
        return roles;
    }

    /**
     * @description: 保存一个实体, null的属性不会保存(会使用数据库默认值)
     * @param: entity
     * @date: 2019-08-17 11:33 AM
     * @return: com.zyd.shiro.business.entity.Role
     */
    @Override
    public Role insert(Role entity) {
        Assert.notNull(entity, "Role不可为空！");
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        roleMapper.insert(entity.getSysRole());
        return entity;
    }

    /**
     * @description: 批量插入:支持批量插入的数据库可以使用,例如MySQL,H2等,另外该接口限制实体包含id属性并且必须为自增列
     * @param: entities
     * @date: 2019-08-17 11:34 AM
     * @return: void
     */
    @Override
    public void insertList(List<Role> entities) {
        Assert.notNull(entities, "entities不可为空！");
        List<SysRole> sysRole = new ArrayList<>();
        for (Role role : entities) {
            role.setUpdateTime(new Date());
            role.setCreateTime(new Date());
            sysRole.add(role.getSysRole());
        }
        roleMapper.insertList(sysRole);
    }

    /***
     *@description: 根据主键字段进行删除, 方法参数必须包含完整的主键属性
     *@param: primaryKey
     *@date: 2019-08-17 11:36 AM
     *@return: boolean
     */
    @Override
    public boolean removeByPrimaryKey(Long primaryKey) {
        return roleMapper.deleteByPrimaryKey(primaryKey) > 0;
    }

    /**
     * @description: 根据主键更新实体全部字段, null值会被更新
     * @param: entity
     * @date: 2019-08-17 11:36 AM
     * @return: boolean
     */
    @Override
    public boolean update(Role entity) {
        Assert.notNull(entity, "Role不可为空！");
        entity.setUpdateTime(new Date());
        return roleMapper.updateByPrimaryKey(entity.getSysRole()) > 0;
    }

    /**
     * @description: 根据主键更新属性不为null的值
     * @param: entity
     * @date: 2019-08-17 11:36 AM
     * @return: boolean
     */
    @Override
    public boolean updateSelective(Role entity) {
        Assert.notNull(entity, "Role不可为空！");
        entity.setUpdateTime(new Date());
        return roleMapper.updateByPrimaryKeySelective(entity.getSysRole()) > 0;
    }

    /**
     * @description: 根据主键字段进行查询, 方法参数必须包含完整的主键属性, 查询条件使用等号
     * @param: primaryKey
     * @date: 2019-08-17 11:37 AM
     * @return: com.zyd.shiro.business.entity.Role
     */
    @Override
    public Role getByPrimaryKey(Long primaryKey) {
        Assert.notNull(primaryKey, "PrimaryKey不可为空！");
        SysRole sysRole = roleMapper.selectByPrimaryKey(primaryKey);
        return null == sysRole ? null : new Role(sysRole);
    }

    /**
     * @description: 根据实体中的属性进行查询, 只能有一个返回值, 有多个结果时抛出异常, 查询条件使用等号
     * @param: entity
     * @date: 2019-08-17 11:37 AM
     * @return: com.zyd.shiro.business.entity.Role
     */
    @Override
    public Role getOneByEntity(Role entity) {
        Assert.notNull(entity, "User不可为空！");
        SysRole sysRole = roleMapper.selectOne(entity.getSysRole());
        return null == sysRole ? null : new Role(sysRole);
    }

    /**
     * @description: 获取所有角色信息, listByEntity(null)方法能达到同样的效果
     * @param:
     * @date: 2019-08-17 11:38 AM
     * @return: java.util.List<com.zyd.shiro.business.entity.Role>
     */
    @Override
    public List<Role> listAll() {
        List<SysRole> sysRole = roleMapper.selectAll();
        return getRole(sysRole);
    }

    /**
     * @description: 根据实体中的属性值进行查询, 查询条件使用等号
     * @param: entity
     * @date: 2019-08-17 11:40 AM
     * @return: java.util.List<com.zyd.shiro.business.entity.Role>
     */
    @Override
    public List<Role> listByEntity(Role entity) {
        Assert.notNull(entity, "Role不可为空！");
        List<SysRole> sysRole = roleMapper.select(entity.getSysRole());
        return getRole(sysRole);
    }

    /***
     *@description: 抽取所用角色信息
     *@param: sysRole
     *@date: 2019-08-17 11:40 AM
     *@return: java.util.List<com.zyd.shiro.business.entity.Role>
     */
    private List<Role> getRole(List<SysRole> sysRole) {
        if (CollectionUtils.isEmpty(sysRole)) {
            return null;
        }
        List<Role> roleList = new ArrayList<>();
        for (SysRole r : sysRole) {
            roleList.add(new Role(r));
        }
        return roleList;
    }

}
