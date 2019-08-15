## :closed_lock_with_key::busts_in_silhouette: 一个简洁优美的权限管理系统

### 项目简洁
*:sparkles: 如你所见 : 这或许是流程最清晰、代码最干净、注释最详细、配置最简单的Shiro项目啦 ! 非常具有参考与学习价值哟~*

### 开发环境

| 工具    | 版本或描述          |    
| ----- | -------------------- |    
| OS    | Windows 7            |    
| JDK   | 1.7+                 |    
| IDE   | IntelliJ IDEA 2017.3 |    
| Maven | 3.3.1                |    
| MySQL | 5.6.4                |    


### 项目模块划分

| 模块         | 释义                                       |    
| ----------  | ------------------------------------------ |    
| shiro-core  | 核心业务类模块，提供基本的数据操作、工具处理等 |    
| shiro-admin | 后台管理模块                                |    


### 数据库模型
![sql model](https://gitee.com/yadong.zhang/shiro/raw/master/docs/img/sql-model.png?v=1.0)


### 使用说明
1. 使用`IDE`导入本项目
2. 新建数据库`CREATE DATABASE shiro;`
3. 导入数据库`docs/db/shiro.sql`
4. 修改(`resources/application.yml`)配置文件
   1. 数据库链接属性(可搜索`datasource`或定位到L.19) 
   2. redis配置(可搜索`redis`或定位到L.69)
5. 运行项目(三种方式)
   1. 项目根目录下执行`mvn -X clean package -Dmaven.test.skip=true`编译打包，然后执行`java -jar shiro-admin/target/shiro-admin.jar`
   2. 项目根目录下执行`mvn springboot:run`
   3. 直接运行`ShiroAdminApplication.java`
6. 浏览器访问`http://127.0.0.1:8080`

#### 用户密码
*超级管理员 : 账号：`root` , 密码：`123456`*

*普通管理员 : 账号：`admin` , 密码：`123456`*

#### Druid监控
*链接 : `http://127.0.0.1:8080/druid/index.html`*

*用户名: `zyd-druid` , 密码：`zyd-druid`*


### 图片预览
*:camera_flash: 系统首页*

![首页](https://gitee.com/yadong.zhang/shiro/raw/master/docs/img/index.png?v=1.0)

*:camera_flash: 资源管理页*

![资源管理](https://gitee.com/yadong.zhang/shiro/raw/master/docs/img/root-resource.png?v=1.0)

*:camera_flash: 角色管理页*

![角色管理](https://gitee.com/yadong.zhang/shiro/raw/master/docs/img/root-role.png?v=1.0)

*:camera_flash: 为角色分配资源*

![角色分配资源](https://gitee.com/yadong.zhang/shiro/raw/master/docs/img/root-role[root].png?v=1.0)

*:camera_flash: 用户管理页*

![用户管理](https://gitee.com/yadong.zhang/shiro/raw/master/docs/img/root-user.png?v=1.0)

*:camera_flash: 为用户分配角色*

![用户分配角色](https://gitee.com/yadong.zhang/shiro/raw/master/docs/img/root-user[role].png?v=1.0)

- *注 : 以上图片为`root`用户登录后的部分截屏，`admin`用户的界面请参考`docs/img`下的图片哟~*


### 参考资料
1. *本项目部分代码参考自网络文章 : [查看原文链接](http://blog.csdn.net/poorcoder_/article/details/71374002)*    
2. *前端模板来源自开源模板 : [查看模板链接](https://colorlib.com/polygon/gentelella/index.html)，[查看开源项目](https://github.com/puikinsh/gentelella)*
3. 部分js实现参考自本人开源项目 : [查看开源项目](https://gitee.com/yadong.zhang/DBlog)
