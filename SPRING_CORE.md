<div style="text-align: justify">

# Spring Framework Concepts

# 1 — **Basics**

Starting from Spring 3, **XML configurations are no longer necessary** when developing a Spring application.
They can be replaced with **annotations** and **Java configuration classes**.

In a typical `ApplicationContext` configuration i.e. XML file or Java annotation,
Spring’s namespaces are declared, and the default namespace is `beans`.
The `beans` namespace is used to declare the beans that need to be managed
by Spring, and it will resolve and inject those dependencies.

The central point of Spring’s IoC container is the `org.springframework.beans.factory.BeanFactory`
interface. `ApplicationContext` is an extension to `BeanFactory`.
Spring implementations of this interface `BeanFactory` are responsible for managing
components, including their dependencies as well as their life cycles.

In Spring, the term _bean_ is used to refer to any object managed by the Spring
IoC container. The Spring IoC container creates, configures (assembles), and
manages beans throughout their life cycle.


**Using XML Configuration**

```java
        ApplicationContext ctx = 
                    new ClassPathXmlApplicationContext("xml/app-context.xml");
        MessageRenderer mr = ctx.getBean("renderer", MessageRenderer.class);
```

**Using Java Annotations**

Configuration classes are Java classes annotated with `@Configuration` that contain bean definitions (methods annotated with @Bean) or are configured
themselves to identify bean definitions in the application by annotating them with `@ComponentScanning`.
It is the equivalent of the `app-context.xml` file.
`AnnotationConfigApplicationContext` (its web-capable variant, `AnnotationConfigWebApplicationContext`)
is used for Java annotation configuration. By instantiating this class, we are
creating an instance of a Spring IoC container that will read the bean
declarations, create the beans, add them to its registry, and manage them.
Using a reference to the container, beans can be retrieved and used.

```java
// A Spring configuration class
@Configuration // equivalent to spring XML configuration file
public class HelloWorldConfiguration {

    @Bean // equivalent to <bean id="provider" class = "..."/>
    public MessageProvider provider() { return new HelloWorldMessageProvider(); }

    @Bean // equivalent to <bean id="renderer" class = "..."/>
    public MessageRenderer renderer() {
        MessageRenderer renderer = new StandardOutMessageRenderer();
        renderer.setMessageProvider(provider());
        return renderer;
    }
}

public class HelloWorldSpringDIWithAnnotations {
    public static void main(String[] args) {
        ApplicationContext ctx = 
                new AnnotationConfigApplicationContext(HelloWorldConfiguration.class);
        MessageRenderer renderer = ctx.getBean("renderer", 
                StandardOutMessageRenderer.class);
        renderer.render();
    }
}
```

# 2 — Inversion of Control (IoC) and Dependency Injection (DI)

**Inversion of Control (IoC)** is a common characteristic of frameworks that **facilitate injection of dependencies**.
The basic idea of **Dependency Injection (DI) pattern** is to have **a separate object that injects dependencies** with the required behavior, based on interface contract.

At its core, IoC aims to offer a simpler **mechanism for provisioning component dependencies** (often referred to as an object’s _collaborators_) and managing these dependencies throughout their life cycles.
A component that requires certain dependencies is often referred to as the **_dependent object_** or,
in the case of IoC, the **_target_**.

IoC can be decomposed into **two subtypes** — **dependency injection** and **dependency lookup**.

## 2.1 — **Dependency Lookup**

Dependency lookup is much more of a traditional approach.
With dependency lookup–style IoC,
a component must acquire a reference to a dependency,
whereas with dependency injection,
the dependencies are injected into the component by the IoC container.

Dependency lookup comes in two types — **Dependency Pull** and **Contextualized Dependency Lookup (CDL)**.

**Dependency Pull** — is the most familiar type of IoC.  In dependency pull, dependencies are pulled from a registry (JNDI) as required. Technology such as EJB has used dependency pull (via the JNDI API to look up an EJB component).
Spring also offers _dependency pull_ as a mechanism for retrieving the components that the framework manages in the above given `HelloWorldSpringDIWithAnnotations` example.

**Contextualized Dependency Lookup (CDL)** — In some aspects it is similar to dependency pull. However in CDL, lookup is performed against the container that is managing the resource, not from some central registry, and it is usually performed at some set point.
CDL works by having the component that requires a dependency implement an interface.

The drawback of dependency lookup is that your classes are always dependent on the classes and interfaces defined by the container. Another drawback with lookup is that testing your classes in isolation from the container becomes difficult.

## 2.2 — **Dependency Injection**

Dependency injection is much more flexible and usable than dependency lookup.
With dependency lookup–style IoC,
a component must acquire a reference to a dependency,
whereas with dependency injection,
the dependencies are injected into the component by the IoC container.

Dependency Injection has two **common** flavors — **Constructor Injection** and **Setter Injection**.

**Constructor Injection** — The IoC container injects a component’s dependencies via its constructor (or constructors). An obvious consequence of using constructor injection is that an object cannot be created without its dependencies; thus, they are mandatory. Constructor injection also helps achieve the use of immutable objects.

**Setter Injection** — The IoC container injects a component’s dependencies via JavaBean-style setter methods. A component’s setters expose the dependencies that the IoC container can manage. An obvious consequence of using setter injection is that an object can be created without its dependencies, and they can be provided later by calling the setter. In practice, **setter injection is the most widely used injection mechanism**, and it is one of the simplest IoC mechanisms to implement.

## 2.3 — Dependency Injection (DI) Configuration

### 2.3.1 — AnnotationConfigApplicationContext


`AnnotationConfigApplicationContext`
(its web-capable variant, `AnnotationConfigWebApplicationContext`)
is used for Java annotation configuration. By instantiating this class, we are
creating an instance of a Spring IoC container that will read the bean
declarations, create the beans, add them to its registry, and manage them.
Using a reference to the container, beans can be retrieved and used.

**Note —** A configuration class can import bean definitions from an XML file
(or more) using `@ImportResource`, and the same bootstrapping using
`AnnotationConfigApplicationContext` will work in this case as well.
Other bean definitions from Java configuration classes can be imported
using `@Import`.


### 2.3.2 — @Configuration


**@Configuration —** To configure a stand-alone Spring application, a configuration class should be
annotated with `@Configuration`. This annotation indicates that the class
contains methods annotated with `@Bean`, which are bean declarations.
It is equivalent to XML context configuration file. The `@Configuration` annotation
is annotated itself with `@Component`, and this means any configuration class is
in essence a bean definition.

**@Bean —** Indicates that the method is a bean declaration. This approach works
for any type of object, especially **types provided by third-party libraries**—code
that is not part of your project, and you cannot edit it to declare your beans.

**@ComponentScan —** The same class can also be configured to enable looking for
existing bean declarations by annotating it with `@ComponentScan`.

**@Component —** The discoverable bean declarations are classes annotated with
`@Component` and other stereotype annotations.

All these annotations are basically used to describe what objects should be
created, the order they should be created in, initialization operations, and
even operations to be executed before being discarded by the Garbage Collector,
most simply referred to as **configuration metadata**.

### 2.3.3 — **Stereotype Annotations**


Another way to declare beans is to annotate the classes directly
with **_stereotype annotations_**.
They define **the roles of types or methods** in the overall architecture
hence they are called _stereotype_.
Stereotype annotations such as `@Component`, `@Controller`, `@Indexed`,
`@Repository`, and `@Service` are part of `org.springframework.stereotype` package.
This package groups together above-mentioned annotations used to define beans and
they are relevant to the role of a bean.


### 2.3.4 — **@Component, @ComponentScan and @Autowired**


**@Component —** @Component is a meta-annotation that marks a class as a bean declaration.
The same class can also be **configured to enable looking for existing bean
declarations by annotating it with `@ComponentScan`**. **The discoverable bean
declarations are classes annotated with `@Component`** and other stereotype
annotations. The Spring container processes these classes to generate bean
definitions and service requests for those beans at runtime. They make the beans
to be candidates for auto-detection when using **annotation based configuration
and classpath scanning**. The `@Configuration` annotation is annotated itself
with `@Component`, and this means any configuration class is in essence a bean
definition.

**@ComponentScan —** By annotating a configuration class (meaning a class
annotated with `@Configuration`) with `@ComponentScan`,
when bootstrapping the `ApplicationContext`, Spring will seek out these classes,
also called **components**, and instantiate the beans with the specified names.
It can also be configured to scan specific packages. When no package is configured,
scanning occurs from the package of the class that declares this annotation,
regardless of whether the class is _public_ or _package-private_.

- `@ComponentScan` — without filters or includes, Spring scans/look for components in the current packages and its subpackages.
- `@ComponentScan(basePackages = "spring.core")` — Spring to look for components in _spring.core_ or its subpackages.
- `@ComponentScan(basePackages = { "spring.core.decoupled", "spring.core.dependency" })` — Spring to look for components in both packages and its subpackages.

Component scanning is a time-consuming operation. Hence, it is good programming
practice to try to limit the places where Spring will look for bean definitions
in the codebase. These below will help.

- `basePackageClasses` — A class or more can be configured; the package of each class specified will be scanned.
- `includeFilters` — Specifies which types are eligible for component scanning.
- `excludeFilters` — Specifies which types are not eligible for component scanning.

**@Autowired —** The bean classes must be annotated with the appropriate stereotype annotation,
and the setters or constructors used to inject **dependencies must be annotated
with `@Autowired` to tell the Spring IoC container to look for a bean OF THAT TYPE**
and use it as an argument when calling that method. When used along with `@Component`, `@Autowired` annotation by default enforces the injection
of a dependency, and thus the Spring application cannot be started if there is
a missing dependency.

## 3 — Dependency Injection Types

### 3.1 — **Setter Injection**

The IoC container injects a component’s dependencies via JavaBean-style setter methods.
A component’s setters expose the dependencies that the IoC container can manage.
An obvious consequence of using setter injection is that an object can be created
without its dependencies, and they can be provided later by calling the setter.
In practice, **setter injection is the most widely used injection mechanism**,
and it is one of the simplest IoC mechanisms to implement.

When @Component is used, To configure setter injection, the `@Autowired`
annotation **must be put on every setter** that is called by Spring to
inject a dependency. `@Autowired` annotation by default enforces the injection
of a dependency, and thus the Spring application cannot be started if there is
a missing dependency.

**Note —** Instead of `@Autowired`, you can use `@Resource(name="provider")` to achieve the same result.
`@Resource` is one of the annotations in the JSR-250 (“Common Annotations for the
Java Platform”) standard that defines a common set of Java annotations for use on
both JSE and JEE platforms. This annotation is currently part of the
_jakarta.annotation-api_ library. Different from `@Autowired`,
the `@Resource` annotation supports the name parameter for more fine-grained
DI requirements. Additionally, Spring supports use of the `@Inject` annotation
introduced as part of JSR-299 (“Contexts and Dependency Injection for the
Java EE Platform”), later moved to JSR-330 (“Dependency Injection for Java”).
`@Inject` is equivalent in behavior to Spring’s `@Autowired` annotation and
currently is part of the `jakarta.inject-api` library.



### 3.2 — **Constructor Injection**

The IoC container injects a component’s dependencies via its constructor
(or constructors). An obvious consequence of using constructor injection is
that an object cannot be created without its dependencies; thus, they are mandatory.
Constructor injection also helps achieve the use of immutable objects.

- When `@Component` used with `@Autowired` for constructor injection, tells Spring
  which constructor to use when instantiating this bean, in case there is more than
  one.
- The `@Autowired` annotation can be applied to only one of the constructors
  within a class. If we apply the annotation to more than one constructor method,
  Spring will complain while bootstrapping `ApplicationContext`.
    - We can also configure default value like below by using `@Value`. `@Value` annotation used to define the value to be injected into the constructor. This is how we inject values that are not beans into a Spring bean.
      ```java
      import org.springframework.beans.factory.annotation.Value;
      // import statements omitted
      @Component("provider")
      class ConfigurableMessageProvider implements MessageProvider {
          private String message;
          public ConfigurableMessageProvider(@Value("Configurable message") 
                                                              String message) {
              this.message = message;
          }
          @Override
          public String getMessage() {
              return message;
          }
      }
      ```


**Note —** In Spring 4.x, it was decided that if a bean declares a single
constructor that initializes all dependencies, the `@Autowired` annotation
was redundant. The Spring IoC was modified to call the only constructor present
to create the bean regardless of the presence/absence of the `@Autowired` annotation.

### 3.3 — **Field Injection**

As the name indicates, the dependency is injected directly into the field, with
no constructor or setter needed. This is done by annotating the class member with
the `@Autowired` annotation.

When the field is private, but the Spring IoC container it uses reflection to
populate the required dependency.

However, there are some **drawbacks with field injection** that are mentioned below.
**Thus, field injection is not recommended**.

- Violates the single responsibility principle.
- Dependency hiding.
- Dependency or tight coupling on Spring IoC container.
- It cannot be used for _final_ fields.
- Difficulty to write tests.

## 4 — Injection Parameters

**Injecting simple values**

Injecting simple values into a bean is possible with @Value annotation.

### 4.1 — Injecting Values using SpEL

Spring Expression Language (SpEL) enables you to evaluate an expression dynamically and then use it in Spring’s ApplicationContext.
You can use the result for injection into Spring beans.
Thus, we can avoid hard coded values in @Value injection
and use SpEL to inject properties from other beans.

### 4.2 — Injection and ApplicationContext Nesting

Spring supports a hierarchical structure for `ApplicationContext` so that one
context (and hence the associating BeanFactory) is considered the parent of
another. By allowing `ApplicationContext` instances to be nested, Spring
allows you to split your configuration into different files, which is a
very useful on larger projects with lots of beans.

### 4.3 — Injecting Collections

Spring allows you to inject a collection of objects such as
List, Set, Map, Properties into one of your beans.

For collection type injection, we have to explicitly instruct Spring to perform
injection by specifying the bean name, and this can be done by using the Spring
`@Qualifier` annotation, the one in the
`org.springframework.beans.factory.annotation`.
This means annotating a dependency with `@Autowired` `@Qualifier("list")`
ensures the expected behavior.


### 4.4 — Method Injection

Method injection comes in two forms — **Lookup method injection and Method replacement**.

**Lookup Method Injection —** provides another mechanism by which a bean can obtain one
of its dependencies.

**Method Replacement —** allows you to replace the implementation of any method
on a bean arbitrarily, without having to change the original source code.

To provide these two features, Spring uses the dynamic bytecode enhancement
capabilities of **CGLIB**. **CGLIB** is a powerful, high-performance, and high-quality
code generation library.

## 5 — Bean Naming


- **Every bean must have at least one name that is unique** within the containing `ApplicationContext`.

- When using XML configuration, if you give the <bean> tag an id attribute,
  the value of that attribute is used as the unique name within the application context.
  Example: `<bean id="provider" class="..."/>`

- When using Java configuration, unless explicitly configured, Spring generates
  bean names using a few strategies.

- When retrieving beans from the application, **bean names or bean types can be used,
  or both can be used**. If multiple beans of the same type without id or name are
  declared, Spring will throw an
  `org.springframework.beans.factory.NoSuchBeanDefinitionException` exception
  during `ApplicationContext` initialization.

- **Bean naming style can be customized** like below.
    ```java
    @Configuration
    @ComponentScan(nameGenerator = SimpleBeanNameGenerator.class)
    class BeanNamingCfg {
    }
    
    class SimpleBeanNameGenerator extends AnnotationBeanNameGenerator {
        @Override
        protected String buildDefaultBeanName(BeanDefinition definition, 
                                            BeanDefinitionRegistry registry) {
            var beanName = definition.getBeanClassName().substring(definition.getBeanClassName().lastIndexOf(".") + 1).toLowerCase(Locale.ROOT);
            var uid = UUID.randomUUID().toString().replace("-","").substring(0,8);
            return beanName + "-" + uid;
        }
    }
    ```
- **Explicit Bean Naming —** Configuring a bean explicitly is easy. When the bean
  is declared with `@Component` or any other stereotype annotation (`@Service`, `@Repository`, etc.),
  there is a default attribute `named` value that can be initialized with a value
  to be used as a name for the bean.


## 6 — Bean Instantiation Modes or Bean Scopes

### 6.1 — Singleton Vs. non-singleton (prototype)

- **singleton —** was used to describe beans that are created only once within the context.
  **By default, all beans in spring are singletons**. This means Spring maintains a
  single instance of the bean, all dependent objects use the same instance, and all
  calls to `ApplicationContext.getBean(..)` return the same instance.

- **non-singleton (_prototype_) —** was used to describe beans that are created everytime they are requested from the context.

- Spring is **instantiation mode agnostic**. If you start off with an object
  that is a singleton but then discover it is not really suited to multi-thread
  access, you can change it to a non-singleton (_prototype_) by using @Scope annotation without affecting any
  of your application code. Spring defaults the scope to the value `singleton`.
  **The `prototype` _scope_ instructs Spring to instantiate a new instance of the
  bean every time a bean instance is requested by the application**.

  ```java
  @Component("nonSingleton")
  @Scope(scopeName = "prototype") // changing the bean to non-singleton
  public class Singer {
    private String name = "unknown";
    public Singer(@Value("John Mayer") String name) {
        this.name = name;
    }
  }
  ```
- In general, _singletons_ should be considered in the following scenarios.
    - _Shared object with no state_
    - _Shared object with read-only state_
    - _Shared object with shared state_
    - _High-throughput objects with writable state_
- _non-singletons_ (aka _prototype_) should be considered in the following scenarios.
    - _Objects with writable state_
    - _Objects with private state_

### 6.2 — Bean Scopes

- **singleton —** The default singleton scope. Only one object will be created per Spring IoC container.

- **prototype —** A non-singleton scope. A new instance will be created by Spring when requested by the application.

- **request —** For web application use. When using Spring MVC for web applications, beans with request scope will be instantiated for every HTTP request and then destroyed when the request is completed.

- **session —** For web application use. When using Spring MVC for web applications, beans with session scope will be instantiated for every HTTP session and then destroyed when the session is over.

- **application —** Scopes a single bean definition to the life cycle of a ServletContext. The application scope is only valid in a Spring web application.

- **thread —** A new bean instance will be created by Spring when requested by a new thread, while for the same thread, the same bean instance will be returned. Note that this scope is not registered by default.

- **custom —** Custom bean scope that can be created by implementing the interface `org.springframework.beans.factory.config.Scope` and registering the custom scope in Spring’s configuration (for XML, use the class `org.springframework.beans.factory.config.CustomScopeConfigurer`).

- **websocket —** Scopes a single bean definition to the life cycle of a WebSocket. The websocket scope is only valid in a Spring web application.

## 7 — Bean Dependencies

Spring can resolve dependencies by looking at your configuration files or annotations
in your classes. But, Spring is not aware of any dependencies that exist between
beans in your code that are not specified in the configuration.

So, we can provide additional information about bean dependencies by using
`@DependsOn` annotation. Spring considers the bean to be instantiated first that is
mentioned in `@DependsOn("...")` before instantiating the second bean that is
dependent on first bean.

For the second bean, to retrieve the dependency on its own, it needs to access
`ApplicationContext`. Thus, we need to tell Spring to inject this reference,
so when the secondBean.sing() method will be called, it can be used to procure
the first bean. This is done by making the second class implement the
`ApplicationContextAware` interface. This is a Spring-specific interface that
forces an implementation of a setter for an ApplicationContext object.
It is automatically detected by the Spring IoC container,
and the `ApplicationContext` that the bean is created in is injected into it.
This is done after the constructor of the bean is called, so obviously using
`ApplicationContext` in the constructor will lead to a `NullPointerException`.

```java
@Component("first")
class First {
    public void some() {}
}

@DependsOn("first")
@Component("second")
class Second implements ApplicationContextAware {
    private ApplicationContext context;

    public Second() {}

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.context = context;
    }

    public void doSomething() {
        First first = context.getBean("first", First.class);
        first.some();
    }
}

public class DependsOnDemo {
    public static void main(String... args) {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(Second.class, First.class);
        ctx.refresh();

        Second second = ctx.getBean("Second", Second.class);
        second.doSomething();
    }
}
```

**Note —** When **developing your applications, avoid designing them to use this
feature. Instead, define your dependencies by means of _setter_ and _constructor_
injection contracts**. However, **if you are integrating Spring with legacy code**,
you may find that the dependencies defined in the code require you to provide
extra information to the Spring Framework.


## 8 — Bean Autowiring

**Autowiring is process of implicitly injecting beans into beans depending on them**.
Spring supports **five modes for autowiring**.

- **`byName` —** When using `byName` autowiring, Spring attempts to wire each
  property to a bean of the same name. So, if the target bean has a property
  named _foo_ and a _foo_ bean is defined in `ApplicationContext`, the _foo_ bean
  is assigned to the _foo_ property of the target.

- **`byType` —** Spring attempts to wire each of the properties on the target bean
  by automatically using a bean of the same type in `ApplicationContext`.

- **`constructor` —** This functions just like byType wiring, except that it uses
  constructors rather than setters to perform the injection. Spring attempts to
  match the greatest numbers of arguments it can in the constructor. So, if your
  bean has two constructors, one that accepts a String and one that accepts String
  and an Integer, and you have both a String and an Integer bean in your
  `ApplicationContext`, Spring uses the two-argument constructor.

- **`default` —** Spring will choose between the `constructor` and `byType` modes
  automatically. If your bean has a default (no-arguments) constructor, Spring uses
  `byType`; otherwise, it uses `constructor`.

- **`no` —** No autowiring, this is the default.


### 8.1 — Constructor Autowiring

When a dependency is provided using constructor injection, autowiring obviously is
done through the constructor. If the class has more than one constructor,
the constructor to be used is chosen based on a few conditions.

- If none of the constructors is annotated with `@Autowired`, the most suitable
  will be used.
- If more than one is suitable, Spring just uses the no-argument
  constructor if there is one.
- If there is none, a `BeanInstantiationException` is thrown.

### 8.2 — byType Autowiring

When there are no constructors declared, but there are setters annotated with
`@Autowired`, Spring will use them and will identify the beans to be injected
based on their type.

Spring injects dependencies by type when fields are directly annotated with
`@Autowired` too, **but field injection is discouraged**.

### 8.3 — byName Autowiring

The @Qualifier annotation can be used for autowiring `byName`.
If `@Autowired` applied on a constructor with arguments, `@Qualifier` should be
placed on the argument, not on the constructor.

### 8.4 — When to use Autowiring

With `byType` **we can have only one bean for each type** in your `ApplicationContext`
It is a restriction that is problematic when we need to maintain beans with
different configurations of the same type. The same argument applies to
the use **of constructor autowiring as well**.

Ref Examples: https://github.com/Apress/pro-spring-6 

</div>