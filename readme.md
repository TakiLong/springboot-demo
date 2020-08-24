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


#### 数据库乐观锁
##### 1. 乐观锁
在并发修改同一条记录时，为避免更新丢失，需要加锁。要么在应用层加锁，要么在缓存层加锁，要么在数据库层使用乐观锁，使用version作为更新依据【强制】。 —— 《阿里巴巴Java开发手册》

乐观锁，基于数据版本(version)记录机制实现，为数据库表增加一个"version"字段。读取出数据时，将此版本号一同读出，之后更新时，对此版本号加一。提交数据时，提交的版本数据与数据库表对应记录的当前版本信息进行比对，如果提交的数据版本号大于数据库表当前版本号，则予以更新，否则认为是过期数据。

【可选】处理BaseDTO中的"version"字段，通过增加一个mybatis插件来实现更新时版本号自动+1。
##### 2. MyBatis插件介绍
MyBatis允许在己映射语句执行过程中的某一点进行拦截调用。默认情况下， MyBatis 允许使用插件来拦截的接口和方法包括以下几个：
* Executor (update 、query 、flushStatements 、commit 、rollback 、getTransaction 、close 、isClosed)
* ParameterHandler (getParameterObject 、setParameters)
* ResultSetHandler (handleResul tSets 、handleCursorResultSets、handleOutputParameters)
* StatementHandler (prepare 、parameterize 、batch update 、query)MyBatis 插件实现拦截器接口Interceptor，在实现类中对拦截对象和方法进行处理 。
MyBatis 插件实现拦截器接口Interceptor，在实现类中对拦截对象和方法进行处理 。
* setProperties：传递插件的参数，可以通过参数来改变插件的行为。
* plugin：参数 target 就是要拦截的对象，作用就是给被拦截对象生成一个代理对象，并返回。
* intercept：会覆盖所拦截对象的原方法，Invocation参数可以反射调度原来对象的方法，可以获取到很多有用的东西。

引入相关包： package org.apache.ibatis.plugin;

除了需要实现拦截器接口外，还需要给实现类配置拦截器签名。 使用 @Intercepts 和 @Signature 这两个注解来配置拦截器要拦截的接口的方法，接口方法对应的签名基本都是固定的。
@Intercepts 注解的属性是一个 ＠Signature  数组，可以在同 一个拦截器中同时拦截不同的接口和方法。
@Signature 注解包含以下三个属性。
type：设置拦截的接口，可选值是前面提到的4个接口 。
method：设置拦截接口中的方法名， 可选值是前面4个接口对应的方法，需要和接口匹配 。
args：设置拦截方法的参数类型数组，通过方法名和参数类型可以确定唯一一个方法 。
##### 3. 数据版本插件
要实现版本号自动更新，我们需要在SQL被执行前修改SQL，因此我们需要拦截的就是 StatementHandler  接口的 prepare 方法，该方法会在数据库执行前被调用，优先于当前接口的其它方法而被执行。

在 core.plugin 包下新建一个VersionPlugin插件，实现Interceptor拦截器接口。

该接口方法签名如下：
```
@Intercepts(
    @Signature(
        type = StatementHandler.class,
        method = "prepare",
        args = {Connection.class, }
    )
)
public class VersionInterceptor implements Interceptor {
    ...
}
```
在 interceptor 方法中对 UPDATE 类型的操作，修改原SQL，加入version，修改后的SQL类似下图，更新时就会自动将version+1。同时带上version条件，如果该版本号小于数据库记录版本号，则不会更新。

VersionInterceptor插件： 见 com.wtl.core.plugin.VersionInterceptor

最后，如果版本不匹配，更新失败，需要往外抛出异常提醒，所以修改BaseService的update方法，增加检查更新是否失败。

最后，能不用插件尽量不要用插件，因为它将修改MyBatis的底层设计。插件生成的是层层代理对象的责任链模式，通过反射方法运行，会有一定的性能消耗。

我们也可以修改 tk.mapper 生成SQL的方法，加入version，这里通过插件方式实现乐观锁主要是不为了去修改 mapper 的底层源码，比较方便。
#### Druid数据库连接池
创建数据库连接是一个很耗时的操作，也很容易对数据库造成安全隐患。对数据库连接的管理能显著影响到整个应用程序的伸缩性和健壮性，影响程序的性能指标。

数据库连接池负责分配、管理和释放数据库连接，它允许应用程序重复使用一个现有的数据库连接，而不是再重新建立一个；释放空闲时间超过最大空闲时间的数据库连接来避免因为没有释放数据库连接而引起的数据库连接遗漏。数据库连接池能明显提高对数据库操作的性能。
##### 1. Druid
Druid首先是一个数据库连接池，但它不仅仅是一个数据库连接池，它还包含一个ProxyDriver，一系列内置的JDBC组件库，一个SQLParser。Druid支持所有JDBC兼容的数据库，包括Oracle、MySql、Derby、Postgresql、SQLServer、H2等等。 Druid针对Oracle和MySql做了特别优化，比如Oracle的PSCache内存占用优化，MySql的ping检测优化。Druid在监控、可扩展性、稳定性和性能方面都有明显的优势。Druid提供了Filter-Chain模式的扩展API，可以自己编写Filter拦截JDBC中的任何方法，可以在上面做任何事情，比如说性能监控、SQL审计、用户名密码加密、日志等等。
##### 2. 配置
在pom.xml中<properties>标签中添加druid版本配置
```
<properties>
    <druid.version>1.0.18</druid.version>
</properties>
```
且在pom.xml中引入druid包
```
<dependency>
			<groupId>com.alibaba</groupId>
			<artifactId>druid</artifactId>
			<version>${druid.version}</version>
		</dependency>
```
Druid配置到core模块下，只需在application.properties中添加如下配置即可，大部分配置是默认配置，可更改。有详细的注释，比较容易理解。



之后启动项目在地址栏输入ip:port/druid/index.html并登录就可以看到Druid监控页面
#### Redis缓存
对于如今的一个中小型系统来说，至少也需要一个缓存来缓存热点数据，加快数据的访问数据，这里选用Redis做缓存数据库。在以后可以使用Redis做分布式缓存、做Session共享等。

##### 1.SpringBoot的缓存支持

Spring定义了org.springframework.cache.CacheManager和org.springframework.cache.Cache接口来统一不同的缓存技术。CacheManager是Spring提供的各种缓存技术抽象接口，Cache接口包含缓存的各种操作。

针对不同的缓存技术，需要实现不同的CacheManager，Redis缓存则提供了RedisCacheManager的实现。

我将redis缓存功能放到sunny-starter-cache模块下，cache模块下可以有多种缓存技术，同时，对于其它项目来说，缓存是可插拔的，想用缓存直接引入cache模块即可。首先引入Redis的依赖：
```
<!--
    springboot redis缓存支持
-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```
SpringBoot已经默认为我们自动配置了多个CacheManager的实现，在autoconfigure.cache包下。在Spring Boot 环境下，使用缓存技术只需在项目中导入相关的依赖包即可。

在 RedisCacheConfiguration 里配置了默认的 CacheManager；SpringBoot提供了默认的redis配置，RedisAutoConfiguration 是Redis的自动化配置，比如创建连接池、初始化RedisTemplate等。
##### 2.Redis配置及声明式缓存支持
Redis 默认配置了 RedisTemplate 和 StringRedisTemplate ，其使用的序列化规则是 JdkSerializationRedisSerializer，缓存到redis后，数据都变成了下面这种样式，非常不易于阅读。
```
x00\x00\x00\...
```
因此，重新配置RedisTemplate，使用 Jackson2JsonRedisSerializer 来序列化 Key 和 Value。同时，增加HashOperations、ValueOperations等Redis数据结构相关的操作，这样比较方便使用。

同时，使用@EnableCaching开启声明式缓存支持，这样就可以使用基于注解的缓存技术。注解缓存是一个对缓存使用的抽象，通过在代码中添加下面的一些注解，达到缓存的效果。

* @Cacheable：在方法执行前Spring先查看缓存中是否有数据，如果有数据，则直接返回缓存数据；没有则调用方法并将方法返回值放进缓存。

* @CachePut：将方法的返回值放到缓存中。

* @CacheEvict：删除缓存中的数据。

demo:
```
@Cacheable(value = SysConstants.REDIS_KEY_MENU, key = "#menuId")
@Override
public Menu selectById(Long menuId) {
    return this.get(menuId)
}
@CacheEvict(value = SysConstants.REDIS_KEY_MENU, key = "#menuId")
@Override
public Menu deleteById(Long menuId) {
    return this.delete(menuId)
}
@CachePut(value = SysConstants.REDIS_KEY_MENU, key = "#menuId")
@Override
public Menu save(Menu dto) {
    return this.persistSelective(dto)
}
```

Redis服务器相关的一些配置可在application.properties中进行配置：


##### 3.Redis工具类
添加一个Redis的统一操作工具，主要是对redis的常用数据类型操作类做了一个归集。

ValueOperations用于操作String类型，HashOperations用于操作hash数据，ListOperations操作List集合，SetOperations操作Set集合，ZSetOperations操作有序集合。

关于redis的key命令和数据类型可参考:
http://www.cnblogs.com/chiangchou/p/redis-1.html
http://www.cnblogs.com/chiangchou/p/redis-2.html

创建Redis操作工具参考文件：

### Swagger支持API文档
#### 1.Swagger
做前后端分离，前端和后端的唯一联系，变成了API接口；API文档变成了前后端开发人员联系的纽带，变得越来越重要，swagger就是一款让你更好的书写API文档的框架。

Swagger是一个简单又强大的能为你的Restful风格的Api生成文档的工具。在项目中集成这个工具，根据我们自己的配置信息能够自动为我们生成一个api文档展示页，可以在浏览器中直接访问查看项目中的接口信息，同时也可以测试每个api接口。
#### 2.配置
使用一个大佬整合好的swagger-spring-boot-starter，快速方便。

GitHub项目地址：https://github.com/SpringForAll/spring-boot-starter-swagger。

新建一个sunny-starter-swagger模块，做到可插拔。
根据文档，一般只需要做些简单的配置即可，但如果想要显示swagger-ui.html文档展示页，还必须注入swagger资源：

### 实现热部署
在实际开发过程中，每次修改代码就得将项目重启，重新部署，对于一些大型应用来说，重启时间需要花费大量的时间成本。对于一个后端开发者来说，重启过程确实很难受啊。在 Java 开发领域，热部署一直是一个难以解决的问题，目前的 Java 虚拟机只能实现方法体的修改热部署，对于整个类的结构修改，仍然需要重启虚拟机，对类重新加载才能完成更新操作。
#### 1.原理
使用了两个ClassLoader，一个Classloader加载那些不会改变的类（第三方Jar包），另一个ClassLoader加载会更改的类，称为restart ClassLoader,这样在有代码更改的时候，原来的restart ClassLoader 被丢弃，重新创建一个restart ClassLoader，由于需要加载的类相比较少，所以实现了较快的重启时间。
#### 2.方式
springboot有三种热部署方式：
* 使用springloaded配置pom.xml文件，使用mvn spring-boot:run启动
* 使用springloaded本地加载启动，配置jvm参数：-javaagent:<jar包地址> -noverify
* 使用devtools工具包，操作简单，但是每次需要重新部署

建议使用devtools工具包这种方式，操作简单快捷。
#### 3.开始配置
在pom.xml中添加依赖
```
<dependencies>
    ...
    <!--devtools热部署-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <optional>true</optional>
        <scope>true</scope>
    </dependency>
    ...
</dependencies>
...
<build>
    <plugins>
        <plugin>
            ...
            <configuration>
                <fork>true</fork>
            </configuration>
        </plugin>
    </plugins>
</build>
```
说明
* devtools可以实现页面热部署（即页面修改后会立即生效，这个可以直接在application.properties文件中配置spring.thymeleaf.cache=false来实现），
实现类文件热部署（类文件修改后不会立即生效），实现对属性文件的热部署。
即devtools会监听classpath下的文件变动，并且会立即重启应用（发生在保存时机），注意：因为其采用的虚拟机机制，该项重启是很快的
* 配置了true后在修改java文件后也就支持了热启动，不过这种方式是属于项目重启（速度比较快的项目重启），会清空session中的值，也就是如果有用户登陆的话，项目重启后需要重新登陆。
* 默认情况下，/META-INF/maven，/META-INF/resources，/resources，/static，/templates，/public这些文件夹下的文件修改不会使应用重启，但是会重新加载（devtools内嵌了一个LiveReload server，当资源发生改变时，浏览器刷新）。

#### 4.devtools的配置
在application.properties中配置devtools
```
#设置开启热部署
spring.devtools.restart.enabled=true
#重启目录
spring.devtools.restart.additional-paths=src/main/java
spring.devtools.restart.exclude=WEB-INF/**
#页面不加载缓存，修改即时生效
spring.freemarker.cache=false
```

#### 5.IDEA中配置
当我们修改了类文件后，idea不会自动编译，得修改idea设置。
* File-Settings-Compiler-Build Project automatically
* ctrl + shift + alt + / ,选择Registry,勾上 Compiler autoMake allow when app running

（备注：暂时还是存在问题，按照教程配置好后）
### SpringSecurity
SpringSecurity 是专门针对基于Spring项目的安全框架，充分利用了AOP和Filter来实现安全功能。它提供全面的安全性解决方案，同时在 Web 请求级和方法调用级处理身份确认和授权。他提供了强大的企业安全服务，如：认证授权机制、Web资源访问控制、业务方法调用访问控制、领域对象访问控制Access Control List（ACL）、单点登录（SSO）等等。

**核心功能**：认证（你是谁）、授权（你能干什么）、攻击防护（防止伪造身份）。

**基本原理**：SpringSecurity的核心实质是一个过滤器链，即一组Filter，所有的请求都会经过这些过滤器，然后响应返回。每个过滤器都有特定的职责，可通过配置添加、删除过滤器。过滤器的排序很重要，因为它们之间有依赖关系。有些过滤器也不能删除，如处在过滤器链最后几环的ExceptionTranslationFilter(处理后者抛出的异常)，FilterSecurityInterceptor(最后一环，根据配置决定请求能不能访问服务)。

#### 标准登录
使用 用户名+密码 的方式来登录，用户名、密码存储在数据库，并且支持密码输入错误三次后开启验证码，通过这样一个过程来熟悉 spring security 的认证流程，掌握 spring security 的原理。

1. 基础环境
创建 sunny-cloud-security 模块，端口号设置为 8010，在sunny-cloud-security模块引入security支持以及sunny-starter-core：




参考文档：
https://www.cnblogs.com/chiangchou/p/sunny-1.html
https://www.cnblogs.com/chiangchou/p/springboot-2.html