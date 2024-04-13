package api.core.dependency.injection.setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import api.core.decoupled.MessageProvider;
import api.core.decoupled.MessageRenderer;

import java.util.Objects;

import static java.lang.System.out;

public class SetterInjection {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(HelloWorldConfiguration.class);
        MessageRenderer mr = ctx.getBean("renderer", MessageRenderer.class);
        mr.render();
    }
}

/**
 * By annotating a configuration class with @ComponentScan,
 * when bootstrapping the ApplicationContext, Spring will
 * seek out these classes, also called components, and instantiate
 * the beans with the specified names.
 */
@Configuration
@ComponentScan
class HelloWorldConfiguration {}

@Component("provider")
class HelloWorldMessageProvider implements MessageProvider {

    @Override
    public String getMessage() {
        return "Hello, World!";
    }
}

@Component("renderer")
class StandardOutMessageRenderer implements MessageRenderer {

    private MessageProvider messageProvider;

    /**
     * @Autowired to tell the Spring IoC container to look for a bean
     * OF THAT TYPE and use it as an argument when calling this method.
     * @param messageProvider Message provider
     */
    @Autowired
    @Override
    public void setProvider(MessageProvider messageProvider) {
        out.println(" ~~ Injecting dependency using setter ~~");
        this.messageProvider = messageProvider;
    }

    @Override
    public void render() {
        if(Objects.isNull(messageProvider))
            throw new RuntimeException(
                    "You must set the property messageProvider of class:"
                    + this.getClass().getName());

        out.println(messageProvider.getMessage());
    }

    @Override
    public MessageProvider getProvider() {
        return this.messageProvider;
    }
}



