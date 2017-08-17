### Spring Cloud简介

​	Spring Cloud为开发者提供了快速构建分布式系统的工具（比如：配置中心、服务发现、熔断器、智能路由、微代理、控制总线等）。

​	相较于Dubbo，Spring Cloud下的众多子项目几乎涵盖了微服务架构的方方面面，而Dubbo只是实现了服务治理，相当于Spring Cloud Netflix中的一个子集。

### RPC还是Rest？

- 性能 ：一般来说，在性能上RPC的调用方式也会更胜一筹。


- 便捷性 ：在微服务架构中，相较于RPC的调用方式，Rest更加轻量化，服务方和调用方之间的依赖是API上的定义，并没有代码上的依赖。


- 复用性 ：RPC服务对平台敏感，复用性较差，Rest方式的调用可以做到跨平台、跨语言。  


在当当网维护的Dobbox中增加了对Rest的支持。

### 服务注册与发现

​	Spring Cloud针对服务治理，做了一层抽象，因此我们也可以使用不同的服务治理框架，比如：Netflix Eureka、Consul、Zookeeper。而服务提供者在切换不同的服务治理框架时只需修改配置文件即可。

​	在Spring Cloud中，Eureka是应用最为广泛的服务治理模块。

### 搭建Eureka注册中心

​	新建一个SpringBoot工程，在pom中添加Eureka对应的依赖：

```xml
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-eureka-server</artifactId>
</dependency>

<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-dependencies</artifactId>
      <version>${spring.cloud.version}</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>
```

通过`@EnableEurekaServer`注解来启动注册中心，如：

```java
@EnableEurekaServer
@SpringBootApplication
public class ToriyamaApplication {

  public static void main(String[] args) {
    SpringApplication.run(ToriyamaApplication.class, args);
  }
}
```

默认情况下Eureka会将自身作为客户端来注册，在单节点模式下，我们可以关闭Eureka的自我注册。在配置文件`application.yml`中添加如下配置：

```yaml
eureka:
  instance:
    hostname: www.dragon.com
  client:
    register-with-eureka: false
    fetch-registry: false
    service-url:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
```

至此，启动工程，打开`http://${host}:${port}/`即可查看注册中心，以及其中注册的服务。

### 创建服务提供者

创建一个SpringBoot工程，添加对应的依赖

```xml
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-eureka</artifactId>
</dependency>

<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-dependencies</artifactId>
      <version>${spring.cloud.version}</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>
```

在配置文件`application.yml`中添加注册中心的配置

```yaml
eureka:
  instance:
    hostname: www.dragon.com
    status-page-url-path: /frieza/info
  client:
    service-url:
      defaultZone: http://${eureka.host}:${eureka.port}/eureka/
```

通过`@EnableEurekaClient`或`@EnableDiscoveryClient`注解来将服务注册到注册中心，如：

```java
@EnableEurekaClient
@SpringBootApplication
public class FriezaApplication {

  public static void main(String[] args) {
    SpringApplication.run(FriezaApplication.class, args);
  }
}
```

启动工程，在刚刚的注册中心页面，即可看到服务已经注册了。