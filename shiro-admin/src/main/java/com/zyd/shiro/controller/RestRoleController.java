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
package com.zyd.shiro.controller;

import com.github.pagehelper.PageInfo;
import com.zyd.shiro.business.entity.Role;
import com.zyd.shiro.business.enums.ResponseStatus;
import com.zyd.shiro.business.service.ShiroService;
import com.zyd.shiro.business.service.SysRoleResourcesService;
import com.zyd.shiro.business.service.SysRoleService;
import com.zyd.shiro.business.vo.RoleConditionVO;
import com.zyd.shiro.framework.object.PageResult;
import com.zyd.shiro.framework.object.ResponseVO;
import com.zyd.shiro.util.ResultUtil;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @project: springboot-shiro
 * @description: 系统角色管理模块的控制器
 * @date: 2019-08-15 6:51 PM
 * @version: 1.0
 * @website: https://yubuntu0109.github.io/
 */
@RestController
@RequestMapping("/roles")
public class RestRoleController {

    @Autowired
    private SysRoleService roleService;
    @Autowired
    private SysRoleResourcesService roleResourcesService;
    @Autowired
    private ShiroService shiroService;

    /**
     * @description: 分页显示系统角色信息
     * @param: vo
     * @date: 2019-08-15 6:51 PM
     * @return: com.zyd.shiro.framework.object.PageResult
     */
    @RequiresPermissions("roles")
    @PostMapping("/list")
    public PageResult getAll(RoleConditionVO vo) {
        PageInfo<Role> pageInfo = roleService.findPageBreakByCondition(vo);
        return ResultUtil.tablePage(pageInfo);
    }

    /**
     * @description: 显示所选择的系统角色信息
     * @param: uid
     * @date: 2019-08-15 6:52 PM
     * @return: com.zyd.shiro.framework.object.ResponseVO<java.util.List < com.zyd.shiro.business.entity.Role>>
     */
    @RequiresPermissions("user:allotRole")
    @PostMapping("/rolesWithSelected")
    public ResponseVO<List<Role>> rolesWithSelected(Integer uid) {
        return ResultUtil.success(null, roleService.queryRoleListWithSelected(uid));
    }

    /**
     * @description: 更新指定角色所拥有的资源信息
     * @param: roleId
     * @param: resourcesId
     * @date: 2019-08-15 6:53 PM
     * @return: com.zyd.shiro.framework.object.ResponseVO
     */
    @RequiresPermissions("role:allotResource")
    @PostMapping("/saveRoleResources")
    public ResponseVO saveRoleResources(Long roleId, String resourcesId) {
        if (StringUtils.isEmpty(roleId)) {
            return ResultUtil.error("error");
        }
        roleResourcesService.addRoleResources(roleId, resourcesId);
        // 重新加载所有拥有roleId的用户的权限信息
        shiroService.reloadAuthorizingByRoleId(roleId);
        return ResultUtil.success("成功");
    }

    /**
     * @description: 添加新系统角色信息
     * @param: role
     * @date: 2019-08-15 6:54 PM
     * @return: com.zyd.shiro.framework.object.ResponseVO
     */
    @RequiresPermissions("role:add")
    @PostMapping(value = "/add")
    public ResponseVO add(Role role) {
        roleService.insert(role);
        return ResultUtil.success("成功");
    }

    /**
     * @description: 删除系统角色信息
     * @param: ids 以","分隔的角色id字符串
     * @date: 2019-08-15 6:55 PM
     * @return: com.zyd.shiro.framework.object.ResponseVO
     */
    @RequiresPermissions(value = {"role:batchDelete", "role:delete"}, logical = Logical.OR)
    @PostMapping(value = "/remove")
    public ResponseVO remove(Long[] ids) {
        if (null == ids) {
            return ResultUtil.error(500, "请至少选择一条记录");
        }
        for (Long id : ids) {
            roleService.removeByPrimaryKey(id);
            roleResourcesService.removeByRoleId(id);
        }
        return ResultUtil.success("成功删除 [" + ids.length + "] 个角色");
    }

    /**
     * @description: 获取指定的系统角色信息
     * @param: id
     * @date: 2019-08-15 6:56 PM
     * @return: com.zyd.shiro.framework.object.ResponseVO
     */
    @RequiresPermissions("role:edit")
    @PostMapping("/get/{id}")
    public ResponseVO get(@PathVariable Long id) {
        return ResultUtil.success(null, this.roleService.getByPrimaryKey(id));
    }

    /**
     * @description: 更新系统角色信息
     * @param: role
     * @date: 2019-08-15 6:57 PM
     * @return: com.zyd.shiro.framework.object.ResponseVO
     */
    @RequiresPermissions("role:edit")
    @PostMapping("/edit")
    public ResponseVO edit(Role role) {
        try {
            roleService.updateSelective(role);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error("角色修改失败！");
        }
        return ResultUtil.success(ResponseStatus.SUCCESS);
    }

}
