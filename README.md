# jtm 平台


``运行：依次启动jtm_euraka、jtm_sso项目``
****************************************
## 系统设置

- jtm_core 核心
```
1、常量
2、反射
3、工具包
4、web

```
- jtm_eureka 注册中心
- jtm_gateway 网关
- jtm_ribbon 负载均衡器
- jtm_selenium selenium自动化测试
- jtm_sso 单点登录
- jtm_sys 系统配置

- **登录注册**
```
    - 登录
        - 口令登录（用户账号/邮箱、密码、动态图/验证码、记住密码）
        - key登录（待定）
        - 二维码登录（待定）
    - 注册
        - 邮箱注册（用户账号、姓名、密码、确认密码、动态图/验证码、邮箱）
```
- **用户模块**
````
    - 用户状态
        - 启用、停用、是否锁定
````
- **权限模块**
````
待定
````
- **参数模块**（所属系统根据对应参数控制相应的界面显示和业务逻辑）
````
    - 系统参数
        - 登录方式
            - 口令登录/key登录/二维码登录
        - 用户新增方式
            - 注册/管理员新增
````
- 注意：如果注解@Slf4j注入后找不到变量log，那就给IDE安装lombok插件
https://jingyan.baidu.com/article/0a52e3f4e53ca1bf63ed725c.html
