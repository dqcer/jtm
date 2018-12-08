### 统一认证平台

- sso-core:关于单点登录的核心业务
- sso-server:单点登录-服务端
- sso-token-sample:基于token进行单点登录的client，适用于前后端分离、app项目使用
- sso-web-sample:基于cookie进行单点登录的client，适用于pc端web项目使用

- 使用方法：
    - 采用工具：idea + jdk1.8 + gradle + redis
    - 基于cookie（二选一）
        ```
        1. 修改sso-web-sample、sso-server配置文件中的sso.redis.address值，改为自己redis地址。
        2. 进入 C:\Windows\System32\drivers\etc，在hosts文件中追加 127.0.0.1 www.jtm.sso.server.com
        3. 启动sso-server、sso-web-sample项目，访问sso-web-sample项目 http://localhost:9002,系统自动跳转到单点登录系统中，
           登录成功后，自动跳转到原来要访问的sso-web-sample项目中。
        4. 我们再开启一个客户端，只是端口不一样。修改sso-web-sample项目的端口为9003，并启动，至于如何在idea中重复启动一个项目，
           网上有相应的教程。启动成功后 访问 http://localhost:9003，这是系统自动到登录成功页面。
        ```
    - 基于token（二选一）
        ```
        1. 修改sso-token、sso-server配置 文件的sso.redis.address值，改为自己的redis地址。
        2. 进入 C:\Windows\System32\drivers\etc，在hosts文件中追加 127.0.0.1 www.jtm.sso.server.com
        3. 启动sso-server、sso-token-sample项目，因为是基于token，所以我们先要登录获取其sessionId，
           因此访问 http://www.jtm.sso.server:9000/app/login?username=admin&password=123456 ,并复制返回的data属性的值。
        4. 查看是否登录 http://www.jtm.sso.server:9000/app/logincheck?sessionId=data属性的值，根据服务端返回的结果，
           至于该到登录界面还是到sso-token-sample界面，由客户端做相应的处理。
        5. 最后再开启一个客户端，可参考基于cookie中第四步骤。
        ```
注：该项目参考xxl-sso开源项目，后续逐渐完善到jtm项目中。
