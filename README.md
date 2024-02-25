<div style="text-align: justify">

# Spring Framework Concepts

## Basics

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

## Inversion of Control (IoC) and Dependency Injection (DI)

**Inversion of Control (IoC)** is a common characteristic of frameworks that **facilitate injection of dependencies**. 
The basic idea of **Dependency Injection (DI) pattern** is to have **a separate object that injects dependencies** with the required behavior, based on interface contract. 

At its core, IoC aims to offer a simpler **mechanism for provisioning component dependencies** (often referred to as an object’s _collaborators_) and managing these dependencies throughout their life cycles. 
A component that requires certain dependencies is often referred to as the **_dependent object_** or, 
in the case of IoC, the **_target_**.

IoC can be decomposed into **two subtypes** — **dependency injection** and **dependency lookup**.

### **Dependency Lookup**

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

### **Dependency Injection**

Dependency injection is much more flexible and usable than dependency lookup. 
With dependency lookup–style IoC, 
a component must acquire a reference to a dependency, 
whereas with dependency injection, 
the dependencies are injected into the component by the IoC container.

Dependency Injection has two common flavors — **Constructor Injection** and **Setter Injection**.

**Constructor Injection** — The IoC container injects a component’s dependencies via its constructor (or constructors). An obvious consequence of using constructor injection is that an object cannot be created without its dependencies; thus, they are mandatory. Constructor injection also helps achieve the use of immutable objects.

**Setter Injection** — The IoC container injects a component’s dependencies via JavaBean-style setter methods. A component’s setters expose the dependencies that the IoC container can manage. An obvious consequence of using setter injection is that an object can be created without its dependencies, and they can be provided later by calling the setter. In practice, **setter injection is the most widely used injection mechanism**, and it is one of the simplest IoC mechanisms to implement. 

### DI Configuration

#### Configuration

To configure a stand-alone Spring application, a configuration class should be annotated with 
`@Configuration`. This annotation indicates that the class contains methods 
annotated with `@Bean`, which are bean declarations. 
It is equivalent to XML context configuration file.

`AnnotationConfigApplicationContext` (its web-capable variant, `AnnotationConfigWebApplicationContext`)
is used for Java annotation configuration. By instantiating this class, we are
creating an instance of a Spring IoC container that will read the bean
declarations, create the beans, add them to its registry, and manage them.
Using a reference to the container, beans can be retrieved and used.

#### Component

The same class can also be **configured to enable looking for existing bean 
declarations by annotating it with `@ComponentScan`**. **The discoverable bean 
declarations are classes annotated with `@Component`** and other stereotype 
annotations. The Spring container processes these classes to generate bean 
definitions and service requests for those beans at runtime. 

All these annotations are basically used to describe what objects should be 
created, the order they should be created in, initialization operations, and 
even operations to be executed before being discarded by the Garbage Collector, 
most simply referred to as **configuration metadata**.





</div>