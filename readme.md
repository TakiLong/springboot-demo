# springboot-demo 说明文档
###### 我的QQ：982097798 VX：wtl982097798 欢迎交流
<font color="#00ff00">----- create time 2019-03-19 by 王天龙 -----</font>
#### 关于项目结构：
- base：是项目的基础核心，定义一些基础类，如BaseController、BaseService等；
- cache：是缓存相关；
- config：是配置中心，模块所有的配置放到config里统一管理；
- constants：里定义系统的常量；
- exception：里封装一些基础的异常类；
- system：是系统模块，开发新模块时注意按照system模块新建，模块化开发模式；
- utils：里则是一些通用工具类；

#### 关于持久化操作：
实体类按照如下规则和数据库表进行转换，注解全部是JPA中的注解：
1. 表名默认使用类名，驼峰转下划线(只对大写字母进行处理)，如UserInfo默认对应的表名为user_info
2. 表名可以使@Table(name = "tableName")进行指定，对不符合第一条默认规则的可以通过这种方式指定表名。
3. 字段默认和@Column一样，都会作为表字段，表字段默认为Java对象的Field名字驼峰转下划线形式。
4. 可以使用@Column(name = "fieldName")指定不符合第3条规则的字段名。
5. 使用@Transient注解可以忽略字段，添加该注解的字段不会作为表字段使用，注意，如果没有与表关联，一定要用@Transient标注。
6. 建议一定是有一个@Id注解作为主键的字段,可以有多个@Id注解的字段作为联合主键。
7. 默认情况下，实体类中如果不存在包含@Id注解的字段,所有的字段都会作为主键字段进行使用(这种效率极低)。
8. 由于基本类型，如int作为实体类字段时会有默认值0，而且无法消除，所以实体类中建议不要使用基本类型。

<font color="#00ff00">----- update time 2019-03-20 by 王天龙 -----</font>

#### 关于jdbc相关配置：
1. mysql-connector-java 6.0 的驱动：com.mysql.cj.jdbc.Driver，注意多了 cj ，若使用此驱动需要在url添加时区信息 serverTimezone=Asia/Shanghai。
```
# 示例连接
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/antd_pro?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false
spring.datasource.username=root
spring.datasource.password=root
```
2. mysql-connector-java 5.0 的驱动：com.mysql.jdbc.Driver ，即旧版本驱动，不会配置请百度。
```
# 示例连接
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/antd_pro?useUnicode=true&characterEncoding=utf8&useSSL=false
spring.datasource.username=root
spring.datasource.password=root
```
3. 若改回原来驱动去掉驱动中的 cj ，以及url中的时区信息即可。
4. 若jdbc版本和mysql版本不兼容，请将 useSSL=true 即可。
5. springboot 2.0版本默认使用光(Hikari)连接池，只需引入spring-boot-starter-parent即可。
> tips：光(Hikari)连接池号称java平台最快。

#### 关于日志框架简介：
Java有很多常用的日志框架，如Log4j、Log4j 2、Commons Logging、Slf4j、Logback等。有时候你可能会感觉有点混乱，下面简单介绍下。
1. Log4j：Apache Log4j是一个基于Java的日志记录工具，是Apache软件基金会的一个项目。
2. Log4j 2：Apache Log4j 2是apache开发的一款Log4j的升级产品。
3. Commons Logging：Apache基金会所属的项目，是一套Java日志接口。
4. Slf4j：类似于Commons Logging，是一套简易Java日志门面，本身并无日志的实现。（Simple Logging Facade for Java，缩写Slf4j）。
5. Logback：一套日志组件的实现(slf4j阵营)。
Commons Logging和Slf4j是日志门面，提供一个统一的高层接口，为各种loging API提供一个简单统一的接口。
log4j和Logback则是具体的日志实现方案。可以简单的理解为接口与接口的实现，调用者只需要关注接口而无需关注具体的实现，做到解耦。
比较常用的组合使用方式是Slf4j与Logback组合使用，Commons Logging与Log4j组合使用。

#### 关于日志框架的选取：
基于下面的一些优点，该项目选用Slf4j+Logback的日志框架
> tips：只要集成了spring-boot-starter-web，就引入了spring-boot-starter-logging，即slf4j和logback。
1. 更快的执行速度，Logback重写了内部的实现，在一些关键执行路径上性能提升10倍以上。而且logback不仅性能提升了，初始化内存加载也更小了。
2. 自动清除旧的日志归档文件，通过设置TimeBasedRollingPolicy 或者 SizeAndTimeBasedFNATP的 maxHistory 属性，你就可以控制日志归档文件的最大数量。
3. Logback拥有远比log4j更丰富的过滤能力，可以不用降低日志级别而记录低级别中的日志。
4. Logback必须配合Slf4j使用。由于Logback和Slf4j是同一个作者，其兼容性不言而喻。
5. 默认情况下，Spring Boot会用Logback来记录日志，并用INFO级别输出到控制台。

#### 使用Logger：
1. 只要集成了spring-boot-starter-web，就引入了spring-boot-starter-logging，即slf4j和logback。
2. 还需引入janino，logback依赖janino。
3. 引入的包是slf4j.Logger，代码里并没有引用任何一个跟 Logback 相关的类，这便是使用 Slf4j的好处，在需要将日志框架切换为其它日志框架时，无需改动已有的代码。
4. LoggerFactory 的 getLogger() 方法接收一个参数，以这个参数决定 logger 的名字，比如第二图中的日志输出。在为 logger 命名时，用类的全限定类名作为 logger name 是最好的策略，这样能够追踪到每一条日志消息的来源。
5. 可以通过提供占位符，以参数化的方式打印日志，避免字符串拼接的不必要损耗，也无需通过logger.isDebugEnabled()这种方式判断是否需要打印。

<font color="#00ff00">----- update time 2019-03-22 by 王天龙 -----</font>

#### 数据库乐观锁
##### 1. 乐观锁
在并发修改同一条记录时，为避免更新丢失，需要加锁。要么在应用层加锁，要么在缓存层加锁，要么在数据库层使用乐观锁，使用version作为更新依据【强制】。 —— 《阿里巴巴Java开发手册》
乐观锁，基于数据版本(version)记录机制实现，为数据库表增加一个"version"字段。读取出数据时，将此版本号一同读出，之后更新时，对此版本号加一。提交数据时，提交的版本数据与数据库表对应记录的当前版本信息进行比对，如果提交的数据版本号大于数据库表当前版本号，则予以更新，否则认为是过期数据。
【可选】处理BaseDTO中的"version"字段，通过增加一个mybatis插件来实现更新时版本号自动+1。

待补充...

https://www.cnblogs.com/chiangchou/p/sunny-1.html