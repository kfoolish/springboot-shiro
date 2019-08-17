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

import com.zyd.shiro.business.entity.UserRole;
import com.zyd.shiro.business.service.SysUserRoleService;
import com.zyd.shiro.persistence.beans.SysUserRole;
import com.zyd.shiro.persistence.mapper.SysUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @project: springboot-shiro
 * @description: 实现用户角色相关的业务处理
 * @date: 2019-08-17 12:37 AM
 * @version: 1.0
 * @website: https://yubuntu0109.github.io/
 */
@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {

    @Autowired
    private SysUserRoleMapper resourceMapper;

    /**
     * @description: 保存一个实体, null的属性不会保存(使用数据库默认值)
     * @param: entity
     * @date: 2019-08-17 12:37 PM
     * @return: com.zyd.shiro.business.entity.UserRole
     */
    @Override
    public UserRole insert(UserRole entity) {
        Assert.notNull(entity, "UserRole不可为空！");
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        resourceMapper.insert(entity.getSysUserRole());
        return entity;
    }

    /**
     * @description: 批量插入:支持批量插入的数据库可以使用, 例如MySQL, H2等, 另外该接口限制实体包含id属性并且必须为自增列
     * @param: entities
     * @date: 2019-08-17 12:38 PM
     * @return: void
     */
    @Override
    public void insertList(List<UserRole> entities) {
        Assert.notNull(entities, "entities不可为空！");
        if (CollectionUtils.isEmpty(entities)) {
            return;
        }
        List<SysUserRole> sysUserRole = new ArrayList<>();
        for (UserRole ur : entities) {
            ur.setUpdateTime(new Date());
            ur.setCreateTime(new Date());
            sysUserRole.add(ur.getSysUserRole());
        }
        resourceMapper.insertList(sysUserRole);
    }

    /**
     * @description: 根据主键字段进行删除, 方法参数必须包含完整的主键属性
     * *@param: primaryKey
     * @date: 2019-08-17 12:38 PM
     * @return: boolean
     */
    @Override
    public boolean removeByPrimaryKey(Long primaryKey) {
        return resourceMapper.deleteByPrimaryKey(primaryKey) > 0;
    }

    /**
     * @description: 根据主键更新实体全部字段(null值也会被更新)
     * @param: entity
     * @date: 2019-08-17 12:38 PM
     * @return: boolean
     */
    @Override
    public boolean update(UserRole entity) {
        Assert.notNull(entity, "UserRole不可为空！");
        entity.setUpdateTime(new Date());
        return resourceMapper.updateByPrimaryKey(entity.getSysUserRole()) > 0;
    }

    /**
     * @description: 根据主键更新属性不为null的值
     * @param: entity
     * @date: 2019-08-17 12:39 PM
     * @return: boolean
     */
    @Override
    public boolean updateSelective(UserRole entity) {
        Assert.notNull(entity, "UserRole不可为空！");
        entity.setUpdateTime(new Date());
        return resourceMapper.updateByPrimaryKeySelective(entity.getSysUserRole()) > 0;
    }

    /**
     * @description: 根据主键字段进行查询, 方法参数必须包含完整的主键属性, 查询条件使用等号
     * @param: primaryKey
     * @date: 2019-08-17 12:40 PM
     * @return: com.zyd.shiro.business.entity.UserRole
     */
    @Override
    public UserRole getByPrimaryKey(Long primaryKey) {
        Assert.notNull(primaryKey, "PrimaryKey不可为空！");
        SysUserRole sysUserRole = resourceMapper.selectByPrimaryKey(primaryKey);
        return null == sysUserRole ? null : new UserRole(sysUserRole);
    }

    /**
     * @description: 根据实体中的属性进行查询, 只能有一个返回值, 有多个结果时抛出异常, 查询条件使用等号
     * @param: entity
     * @date: 2019-08-17 12:40 PM
     * @return: com.zyd.shiro.business.entity.UserRole
     */
    @Override
    public UserRole getOneByEntity(UserRole entity) {
        Assert.notNull(entity, "User不可为空！");
        SysUserRole sysUserRole = resourceMapper.selectOne(entity.getSysUserRole());
        return null == sysUserRole ? null : new UserRole(sysUserRole);
    }

    /**
     * @description: 查询全部结果, listByEntity(null)方法能达到同样的效果
     * @param:
     * @date: 2019-08-17 12:41 PM
     * @return: java.util.List<com.zyd.shiro.business.entity.UserRole>
     */
    @Override
    public List<UserRole> listAll() {
        List<SysUserRole> sysUserRole = resourceMapper.selectAll();
        return getUserRole(sysUserRole);
    }

    /**
     * @description: 根据实体中的属性值进行查询, 查询条件使用等号
     * @param: entity
     * @date: 2019-08-17 12:41 PM
     * @return: java.util.List<com.zyd.shiro.business.entity.UserRole>
     */
    @Override
    public List<UserRole> listByEntity(UserRole entity) {
        Assert.notNull(entity, "UserRole不可为空！");
        List<SysUserRole> sysUserRole = resourceMapper.select(entity.getSysUserRole());
        return getUserRole(sysUserRole);
    }


    /**
     * @description: 添加用户角色, 该节代码参考自: http://blog.csdn.net/poorcoder_/article/details/71374002
     * @param: userId
     * @param: roleIds
     * @date: 2019-08-17 12:44 PM
     * @return: void
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {Exception.class})
    public void addUserRole(Long userId, String roleIds) {
        //删除
        removeByUserId(userId);
        //添加
        String[] roleIdArr = roleIds.split(",");
        if (roleIdArr.length == 0) {
            return;
        }
        UserRole u = null;
        List<UserRole> roles = new ArrayList<>();
        for (String roleId : roleIdArr) {
            if (StringUtils.isEmpty(roleId)) {
                continue;
            }
            u = new UserRole();
            u.setUserId(userId);
            u.setRoleId(Long.parseLong(roleId));
            roles.add(u);
        }
        insertList(roles);
    }

    /**
     * @description: 根据用户id删除指定的用户角色
     * @param: userId
     * @date: 2019-08-17 12:45 PM
     * @return: void
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {Exception.class})
    public void removeByUserId(Long userId) {
        Example example = new Example(SysUserRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        resourceMapper.deleteByExample(example);
    }

    /**
     * @description: 抽取所有的用户角色信息
     * @param: sysUserRole
     * @date: 2019-08-17 12:42 PM
     * @return: java.util.List<com.zyd.shiro.business.entity.UserRole>
     */
    private List<UserRole> getUserRole(List<SysUserRole> sysUserRole) {
        if (CollectionUtils.isEmpty(sysUserRole)) {
            return null;
        }
        List<UserRole> urList = new ArrayList<>();
        for (SysUserRole r : sysUserRole) {
            urList.add(new UserRole(r));
        }
        return urList;
    }
}
