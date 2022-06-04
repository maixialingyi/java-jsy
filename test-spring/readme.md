#### 常用Jar包

| spring-core                         | 框架的基础功能，包括IOC和AOP功能                             |
| ----------------------------------- | ------------------------------------------------------------ |
| spring-aspects                      | 提供了与AspectJ的集成，AspectJ是一个面向切面的框架，它扩展了Java语言。AspectJ定义了AOP语法，它有一个专门的编译器用来生成遵守Java字节编码规范的Class文件。 |
| spring-beans                        | 所有应用都要用到，包含访问配置文件、创建和管理 bean 以及进行 Inversion of Control(控制反转) / Dependency Injection（依赖注入）操作相关的所有类。外部依赖 spring-core |
| spring-context                      |                                                              |
| spring-aop、spring-instrument       | 面向切面编程、植入代理                                       |
| spring-expression                   | 模块提供了强大的表达式语言去支持查询和操作运行时对象图。这是对JSP 2.1规范中规定的统一表达式语言的扩展。该语言支持设置和获取属性值，属性分配，方法调用，访问数组，集合和索引器的内容，逻辑和算术运算，变量命名以及从Spring的IoC容器中以名称检索对象。 它还支持列表投影和选择以及常见的列表聚合。 |
| spring-messaging                    | 消息传递                                                     |
| spring-jdbc、spring-jms、spring-orm | 数据访问支持                                                 |
| spring-jcl                          | Jakarta Commons Logging采用了设计模式中的“适配器模式”，它对外提供统一的接口，然后在适配类中将对日志的操作委托给具体的日志框架。 |
| spring-tx                           | 事务                                                         |
| spring-webmvc、spring-web           | Webmvc框架支持                                               |
| spring-webflux                      | Servlet3.1 + Netty 方式的WebMvc                              |
| spring-websocket                    | 对ws支持                                                     |