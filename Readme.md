Java秒杀--项目进展
    2021.10.30-31 18:28 周末由于7点开班会，没时间了，只能做一个简单的回顾😤
    1、实现Java秒杀登陆的功能
        登陆有两个字段手机号码对应的数据库就是id和密码
        对手机号码  前端做了非空和格式的验证
                  后端同样也做了判断 校验组件
                  后端采用了springboot的validation 自定义了一个@IsMobile注解来实现格式和空的验证
        对密码
            两次加密
            前端和后端都采用MD5加密，采用相同的salt
                前端输入明文密码，通过MD5加密，得到第一次加密的密码
                后端通过前端传入的frompass，通过MD5Untils类中的formPassToDBPass() 再一次加密
                返回的dbPass才写入数据库
    2、实现对异常的处理 抛出
        异常分为运行时异常和编译时异常，对于报错信息，我们需要统一的处理，通常会根据不同类型的错误返回给用户不同的错误细信息。
        在我们的项目中，我们做了公共返回对象，也就是前台传进来的参数手机号码和密码，对它做了校验，但是用户又看不到，这就需要我们
        对用户展示。
        在RespBeanEnum定义了自定义的常见的错误信息。网上还有人用HashMap来做。🤓都是大佬，下次可以试一下
        因为我们用springboot来做，它自己就实现了封装对异常的处理@RestControllerAdvice和
        看的不是很懂了，主要做了还是全局异常和绑定异常。🤯
        先放个链接🔗，以后看吧。😶‍🌫️
        springboot继承AbstractErrorController实现全局的异常处理
        https://blog.csdn.net/qq_29684305/article/details/82286469
        spring boot 原生错误处理ErrorController
        https://blog.csdn.net/shenyunsese/article/details/53390116
        @ControllerAdvice 拦截异常并统一处理
        https://my.oschina.net/langwanghuangshifu/blog/2246890
    3、商品秒杀还会判断用户登录～我惊了，还的写～ 涉及到**分布式session的问题**
        用户是否登录成功的判断，哪登录成功的话就给它一个状态（班会开完回来继续撸～7:30🥶）
        有点懵 放个链接 session和cookie https://www.cnblogs.com/moyand/p/9047978.html
        cookie和session来做。
        分布式session问题：
            之前的代码我们都是部署在一台的Tomcat上，当我们部署多台系统，配合Nginx的时候会出现用户登录问题
        原因是Nginx使用默认负载均衡策略（轮询），请求将会按照时间顺序逐一分发到后端应用上。也就会出现，我们在请求Tomcat1
        的时候，登录成功，用户的session放在Tomcat1。过了一会，请求又被Nginx
        分发到Tomcat2上，这时Tomcat2伤的session里还没有用户刚才登录的信息，于是要重新登录😰就很。。。
        网上的解决方案：（水平扩展和垂直扩展？？？？🤯）
            1.Session复制。（又开始了copy～）
                优点：无需修改代码，只需要修改Tomcat的配置。
                缺点：Session同步传输占用网带宽。多台Tomcat同步性能指数级下降。Session占用内存，无法有效水平扩展
            2.前端存储
                优点：不占用服务器资源～ 。
                缺点：存在安全风险。数据大小受cookie限制。占用外网的带宽
            3.Session粘滞
                优点：无需修改代码。服务端可以水平扩展
                缺点：增加新机器，会重新Hash，导致重新登录。应用重启，需要重新登录。    
            4.后端集中存储
                优点：安全（！！！！）。容易水平扩展（？？？？扩展干嘛？🤓）
                缺点：增加复杂度。需要修改代码。
            （所以解决方案是什么？？？？）大佬🧍‍♂️出来了Redis👍
            5.Redis
                全是优点！！！！🔗https://www.cnblogs.com/toov5/p/9903017.html 都是大佬🧍‍♂️
                大佬说技术可以是最新的，版本不一定要最新的！
    21:40 回宿舍睡觉了 洗澡
    2021.11.1 10.36 10点下课 做了leetCode_88 合并数组 并有序输出 双指针后序
    11:00 当完工具人 去校医院 啥都没干
            1.使用Spring-Session 来存储用户的session 在redis的客户端 我们看到数据全是二进制的 此时我们要考虑序列化的问题
    4.用Redis存储用户信息 去除掉spring-session依赖 直接用redis来操作用户 也是变相的解决分布式session的问题
        配置了redis相关的配置 写了配置类 注入了连接工厂 设置了String、Hash类型的序列化
        在用户登录的时候，将cookie存入到redis（在用户进行下一步操作都会根据ticket判断用户是否登录--》
        用户每进行一个操作，就需要我们来判断ticket，就很麻烦，这个时候我们可以对代码改动。
        也就是说在用户进行之前操作，我们就要对其判断 这就需要mvc思想 --->引入springmvc-中的dispatcherServlet
        接受用户的请求，并调用相应的Model来处理。相当于一个总调配中心，有什么需求，就去调用相应模型进行处理，最后通过
        视图给用户进行展示。
        还是放个链接🔗https://www.cnblogs.com/williamjie/p/9109619.html

