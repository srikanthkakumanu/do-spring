package spring.basic.decoupled;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * A Spring configuration class
 */
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
