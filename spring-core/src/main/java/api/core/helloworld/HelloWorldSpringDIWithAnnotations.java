package api.core.helloworld;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import api.core.decoupled.HelloWorldConfiguration;
import api.core.decoupled.MessageRenderer;
import api.core.decoupled.StandardOutMessageRenderer;

public class HelloWorldSpringDIWithAnnotations {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(HelloWorldConfiguration.class);
        MessageRenderer renderer = ctx.getBean("renderer", StandardOutMessageRenderer.class);
        renderer.render();
    }
}
