package spring.core.helloworld;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.core.decoupled.HelloWorldConfiguration;
import spring.core.decoupled.MessageRenderer;
import spring.core.decoupled.StandardOutMessageRenderer;

public class HelloWorldSpringDIWithAnnotations {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(HelloWorldConfiguration.class);
        MessageRenderer renderer = ctx.getBean("renderer", StandardOutMessageRenderer.class);
        renderer.render();
    }
}
