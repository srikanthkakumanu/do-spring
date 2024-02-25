package spring.basic.helloworld;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.basic.decoupled.HelloWorldConfiguration;
import spring.basic.decoupled.MessageRenderer;
import spring.basic.decoupled.StandardOutMessageRenderer;

public class HelloWorldSpringDIWithAnnotations {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(HelloWorldConfiguration.class);
        MessageRenderer renderer = ctx.getBean("renderer", StandardOutMessageRenderer.class);
        renderer.render();
    }
}
