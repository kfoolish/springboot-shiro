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
package com.zyd.shiro.framework.object;

import java.util.List;

/**
 * @project: springboot-shiro
 * @description: 通用的Service接口:提高代码的简洁及可维护性
 * @date: 2019-08-16 3:47 PM
 * @version: 1.0
 * @website: https://yubuntu0109.github.io/
 */
public interface AbstractService<T, PK> {

    /**
     * @description: 保存一个实体, null的属性不会保存(使用数据库默认值)
     * @param: entity
     * @date: 2019-08-16 3:47 PM
     * @return: T
     */
    T insert(T entity);

    /**
     * @description: 批量插入:既支持批量插入的数据库可以使用,例如MySQL,H2等,另外该接口限制实体包含id属性并且必须为自增列
     * @param: entities
     * @date: 2019-08-16 4:05 PM
     * @return: void
     */
    void insertList(List<T> entities);

    /**
     * @description: 根据主键字段进行删除, 方法参数必须包含完整的主键属性
     * @param: primaryKey
     * @date: 2019-08-16 4:06 PM
     * @return: boolean
     */
    boolean removeByPrimaryKey(PK primaryKey);

    /**
     * @description: 根据主键更新实体全部字段, null值也会被更新
     * @param: entity
     * @date: 2019-08-16 4:07 PM
     * @return: boolean
     */
    boolean update(T entity);

    /**
     * @description: 根据主键更新属性不为null的值
     * @param: entity
     * @date: 2019-08-16 4:08 PM
     * @return: boolean
     */
    boolean updateSelective(T entity);

    /**
     * @description: 根据主键字段进行查询, 方法参数必须包含完整的主键属性, 查询条件使用等号
     * @param: primaryKey
     * @date: 2019-08-16 4:09 PM
     * @return: T
     */
    T getByPrimaryKey(PK primaryKey);

    /**
     * @description: 根据实体中的属性进行查询, 只能有一个返回值, 有多个结果时抛出异常, 查询条件使用等号
     * @param: entity
     * @date: 2019-08-16 4:11 PM
     * @return: T
     */
    T getOneByEntity(T entity);

    /**
     * @description: 查询全部结果, 注:listByEntity(null)方法能达到同样的效果
     * @param:
     * @date: 2019-08-16 4:12 PM
     * @return: java.util.List<T>
     */
    List<T> listAll();

    /**
     * @description: (never used)根据实体中的属性值进行查询, 查询条件使用等号
     * @param: entity
     * @date: 2019-08-16 4:12 PM
     * @return: java.util.List<T>
     */
    List<T> listByEntity(T entity);
}
