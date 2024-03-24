<div style="text-align: justify">

# **1 — Basics**

## **1.1 — Autoconfiguration**

Spring Boot comes with most well-known  and one of the important feature 
called _**autoconfiguration**_. Because it configures a Spring boot application 
according to the **_classpath_, _annotations_, and any other configuration declarations 
such as _JavaConfig_ classes or _XML_**.

In short, autoconfiguration registers key beans in the application context. When a Spring Boot application starts up, it examines many parts of 
our application, including _**classpath**_. Based on what the application sees, 
it automatically adds additional **Spring beans** to the **application context**.

`@SpringBootApplication` annotation automatically inherits `@EnableAutoConfiguration`, 
`@Configuration`, and `@ComponentScan`. We can exclude some of the auto-configurations 
like shown below.

`@SpringBootApplication(exclude={ActiveMQAutoConfiguration.class,DataSourceA
utoConfiguration.class})`


## **1.2 — Application Context**

Whenever a Spring Framework application starts up, whether Spring Boot is 
involved or not, it creates a container of sorts. Various Java beans that 
are registered with Spring Framework’s application context are known as 
**Spring beans**.

Spring Framework has a deep-seated concept known as **dependency injection (DI)**, 
where a Spring bean can express its need for a bean of some other type.

The application context will ensure all Spring beans needed by the application are 
created and properly injected into each other. This is known as **wiring**.

### **1.2.1 — SpringApplicationBuilder**

The `SpringApplicationBuilder` class provides **a fluent API** and is **a builder** for
the `SpringApplication` and `ApplicationContext` instances and provides hierarchy support as well.


## **1.3 — Autoconfiguration Policies**


Spring Boot comes with a fistful of autoconfiguration policies. **These are classes 
that contain @Bean definitions that are only registered based on certain 
conditional circumstances**.

For example, If Spring Boot detects the class definition of `DataSource` somewhere 
on the **classpath**, a class found inside any Java Database Connectivity (JDBC) 
driver, it will activate its `DataSourceAutoConfiguration`. This policy will 
fashion some version of a `DataSource` bean. This is driven by the 
`@ConditionalOnClass({ DataSource.class })` annotation found on that policy.

Spring Boot autoconfiguration also has smart ordering built in, ensuring beans 
are added properly.

## **1.4 — Add portfolio components using Spring Boot starters**

Spring Framework has **three artifacts involving web applications**: 
_**Spring Web, Spring MVC, and Spring WebFlux**_. 

1. **Spring MVC —** is servlet-specific bits. 
2. **Spring WebFlux  —** is for reactive web app development and is not tied to any 
servlet-based contracts. 
3. **Spring Web —** contains **common elements shared between Spring MVC and Spring WebFlux**. 
This mostly includes the annotation-based programming model Spring MVC has had for 
years. This means that the day you want to start writing reactive web apps, 
you don’t have to learn a whole new paradigm to build web controllers.

If we added api-boot-starter-web, this is what it contains:
- Spring MVC and the associated annotations found in Spring Web. 
These are the Spring Framework bits that support servlet-based web apps.

**Portfolio Components** 

- Jackson Databind for serialization and deserialization 
(including JSR 310 support) to and from JSON.
- An embedded Apache Tomcat servlet container.
- Core Spring Boot starter.
- Spring Boot.
- Spring Boot Autoconfiguration.
- Spring Boot Logging.
- Jakarta annotations.
- Spring Framework Core.
- SnakeYAML to handle YAML Ain’t Markup Language (YAML)-based property files.

## **1.5 — Application Configuration Properties**

Spring Boot introduces **configuration properties** as a way to plug property 
settings into any Spring bean by using **_application.properties_ file** 
(follows Java configuration aka key-value pair) this file should be under
src/main/resources location.

### **1.5.1 — Custom Configuration Properties**

Configuration properties can be applied to any Spring bean. This applies not just 
to Spring Boot’s autoconfigured beans (via _application.properties_), but to our 
own Spring beans as well. 

- Custom configuration properties can also be set 
by using a`@ConfigurationProperties(prefix="my.app")` and a Java Bean for the 
properties with _getter_ and _setter_ methods. `@ConfigurationProperties` is a 
Spring Boot annotation that labels this Spring bean as a source of configuration 
properties. It indicates that the prefix of such properties will be my.app.

- A application properties file under resources directory.
```
  application.properties:
  my.app.header=Learning Spring Boot 3
  my.app.footer=Hello Spring Boot 3
```

These above two lines will be read by Spring Boot and injected into the 
MyCustomProperties Spring bean before it gets injected into the application 
context. 

We can then inject that bean into any relevant component in our app.

**Spring Boot also supports profiles**. We can create profile-specific 
property overrides such as **application-dev.properties, application-test.properties
and application.properties**.

To activate this override, simply include `-Dspring.profiles.active=test` as an 
extra argument to the Java command to run our app.

## **2 — Controllers**

Spring has two types of controllers: MVC controller and REST controller.

**`@Controller` annotation** — refers to an MVC controller, means it is template based.


**`@RestController` annotation** — refers to pure HTTP controller that adheres to the REST principles.
`@RestController` is similar to the `@Controller` annotation. 
It signals to Spring Boot that this class should be automatically picked up for 
component scanning as a Spring bean. This bean will be registered in the 
application context and also with Spring MVC as a controller class, so it can 
route web calls.

But it has one additional property — it switches every web method from being 
template-based to JSON-based. In other words, instead of a web method returning 
the name of a template that Spring MVC renders through a templating engine, 
it instead serializes the results using Jackson.

## **3 — ApplicationRunner and CommandLineRunner**

`ApplicationRunner` and `CommandLineRunner` used to run the logic during application startup. 

**`ApplicationRunner` —** It is a Functional Interface with a `run()` method.
`run()` will get executed, just after `ApplicationContext` is created and before 
api boot application startup. It takes `ApplicationArgument` which has 
convenient methods like `getOptionNames()`, `getOptionValues()` and `getSourceArgs()`.

**`CommandLineRunner` —** It is also a Functional Interface with a `run()` method.
`run()` will get executed, just after `ApplicationContext` is created and before
api boot application startup. It accepts the argument, which can be passed at the 
time of server startup.

Both of them provide the same functionality and the only difference between 
`CommandLineRunner` and `ApplicationRunner` is that 
`CommandLineRunner.run()` accepts `String array[]` 
whereas `ApplicationRunner.run()` accepts `ApplicationArguments` as an argument.
They both are practically the same. It is not necessary to implement both at
the same time. We can add as many methods that return 
`ApplicationRunner` or `CommandLineRunner` as we want.
If we want them to execute in a certain order, we can use the `@Order` annotation.



Ref Examples: https://github.com/PacktPublishing/Learning-Spring-Boot-3.0-Third-Edition
</div>