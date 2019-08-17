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

import com.zyd.shiro.business.entity.RoleResources;
import com.zyd.shiro.business.service.SysRoleResourcesService;
import com.zyd.shiro.persistence.beans.SysRoleResources;
import com.zyd.shiro.persistence.mapper.SysRoleResourcesMapper;
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
 * @description: 实现角色资源相关的业务处理
 * @date: 2019-08-17 12:55 AM
 * @version: 1.0
 * @website: https://yubuntu0109.github.io/
 */
@Service
public class SysRoleResourcesServiceImpl implements SysRoleResourcesService {

    @Autowired
    private SysRoleResourcesMapper resourceMapper;

    /**
     * @description: 保存一个实体, null的属性不会保存(使用数据库默认值)
     * @param: entity
     * @date: 2019-08-17 12:55 PM
     * @return: com.zyd.shiro.business.entity.RoleResources
     */
    @Override
    public RoleResources insert(RoleResources entity) {
        Assert.notNull(entity, "RoleResources不可为空！");
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        resourceMapper.insert(entity.getSysRoleResources());
        return entity;
    }

    /**
     * @description: 批量插入:支持批量插入的数据库可以使用,例如MySQL,H2等,另外该接口限制实体包含id属性并且必须为自增列
     * @param: entities
     * @date: 2019-08-17 12:55 PM
     * @return: void
     */
    @Override
    public void insertList(List<RoleResources> entities) {
        Assert.notNull(entities, "entities不可为空！");
        if (CollectionUtils.isEmpty(entities)) {
            return;
        }
        List<SysRoleResources> sysRoleResources = new ArrayList<>();
        for (RoleResources rr : entities) {
            rr.setUpdateTime(new Date());
            rr.setCreateTime(new Date());
            sysRoleResources.add(rr.getSysRoleResources());
        }
        resourceMapper.insertList(sysRoleResources);
    }


    /**
     * @description: 根据主键字段进行删除, 方法参数必须包含完整的主键属性
     * @param: primaryKey
     * @date: 2019-08-17 12:56 PM
     * @return: boolean
     */
    @Override
    public boolean removeByPrimaryKey(Long primaryKey) {
        return resourceMapper.deleteByPrimaryKey(primaryKey) > 0;
    }

    /**
     * @description: 根据主键更新实体全部字段(null值也会被更新)
     * @param: entity
     * @date: 2019-08-17 12:56 PM
     * @return: boolean
     */
    @Override
    public boolean update(RoleResources entity) {
        Assert.notNull(entity, "RoleResources不可为空！");
        entity.setUpdateTime(new Date());
        return resourceMapper.updateByPrimaryKey(entity.getSysRoleResources()) > 0;
    }

    /**
     * @description: 根据主键更新属性不为null的值
     * @param: entity
     * @date: 2019-08-17 12:57 PM
     * @return: boolean
     */
    @Override
    public boolean updateSelective(RoleResources entity) {
        Assert.notNull(entity, "RoleResources不可为空！");
        entity.setUpdateTime(new Date());
        return resourceMapper.updateByPrimaryKeySelective(entity.getSysRoleResources()) > 0;
    }

    /**
     * @description: 根据主键字段进行查询, 方法参数必须包含完整的主键属性, 查询条件使用等号
     * @param: primaryKey
     * @date: 2019-08-17 12:57 PM
     * @return: com.zyd.shiro.business.entity.RoleResources
     */
    @Override
    public RoleResources getByPrimaryKey(Long primaryKey) {
        Assert.notNull(primaryKey, "PrimaryKey不可为空！");
        SysRoleResources sysRoleResources = resourceMapper.selectByPrimaryKey(primaryKey);
        return null == sysRoleResources ? null : new RoleResources(sysRoleResources);
    }

    /**
     * @description: 根据实体中的属性进行查询, 只能有一个返回值, 有多个结果时抛出异常, 查询条件使用等号
     * @param: entity
     * @date: 2019-08-17 12:58 PM
     * @return: com.zyd.shiro.business.entity.RoleResources
     */
    @Override
    public RoleResources getOneByEntity(RoleResources entity) {
        Assert.notNull(entity, "User不可为空！");
        SysRoleResources sysRoleResources = resourceMapper.selectOne(entity.getSysRoleResources());
        return null == sysRoleResources ? null : new RoleResources(sysRoleResources);
    }

    /**
     * @description: 查询全部结果, listByEntity(null)方法能达到同样的效果
     * @param:
     * @date: 2019-08-17 12:58 PM
     * @return: java.util.List<com.zyd.shiro.business.entity.RoleResources>
     */
    @Override
    public List<RoleResources> listAll() {
        List<SysRoleResources> sysRoleResources = resourceMapper.selectAll();
        return getRoleResources(sysRoleResources);
    }

    /**
     * @description: 根据实体中的属性值进行查询, 查询条件使用等号
     * @param: entity
     * @date: 2019-08-17 12:58 PM
     * @return: java.util.List<com.zyd.shiro.business.entity.RoleResources>
     */
    @Override
    public List<RoleResources> listByEntity(RoleResources entity) {
        Assert.notNull(entity, "RoleResources不可为空！");
        List<SysRoleResources> sysRoleResources = resourceMapper.select(entity.getSysRoleResources());
        return getRoleResources(sysRoleResources);
    }


    /**
     * @description: 添加角色资源, 该节代码参考自: http://blog.csdn.net/poorcoder_/article/details/71374002
     * @param: roleId
     * @param: resourcesIds
     * @date: 2019-08-17 1:04 PM
     * @return: void
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {Exception.class})
    public void addRoleResources(Long roleId, String resourcesIds) {
        //删除
        removeByRoleId(roleId);
        //添加
        if (!StringUtils.isEmpty(resourcesIds)) {
            String[] resourcesArr = resourcesIds.split(",");
            if (resourcesArr.length == 0) {
                return;
            }
            SysRoleResources r = null;
            List<SysRoleResources> roleResources = new ArrayList<>();
            for (String ri : resourcesArr) {
                if (StringUtils.isEmpty(ri)) {
                    continue;
                }
                r = new SysRoleResources();
                r.setRoleId(roleId);
                r.setResourcesId(Long.parseLong(ri));
                r.setCreateTime(new Date());
                r.setUpdateTime(new Date());
                roleResources.add(r);

            }
            if (roleResources.size() == 0) {
                return;
            }
            resourceMapper.insertList(roleResources);
        }
    }

    /**
     * @description: 通过角色id批量删除
     * @param: roleId
     * @date: 2019-08-17 1:04 PM
     * @return: void
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {Exception.class})
    public void removeByRoleId(Long roleId) {
        //删除
        Example example = new Example(SysRoleResources.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleId", roleId);
        resourceMapper.deleteByExample(example);
    }

    /**
     * @description: 抽取所有角色资源信息
     * @param: sysRoleResources
     * @date: 2019-08-17 12:59 PM
     * @return: java.util.List<com.zyd.shiro.business.entity.RoleResources>
     */
    private List<RoleResources> getRoleResources(List<SysRoleResources> sysRoleResources) {
        if (CollectionUtils.isEmpty(sysRoleResources)) {
            return null;
        }
        List<RoleResources> rr = new ArrayList<>();
        for (SysRoleResources r : sysRoleResources) {
            rr.add(new RoleResources(r));
        }
        return rr;
    }
}
