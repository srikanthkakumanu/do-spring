package spring.core.dependency.injection.constructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import spring.core.decoupled.MessageProvider;
import spring.core.decoupled.MessageRenderer;

import static java.lang.System.out;

public class ConfigurableProvider {

    public static void main(String... args) {
        ApplicationContext ctx =
                new AnnotationConfigApplicationContext
                        (HelloWorldConfiguration.class);
        MessageRenderer mr = ctx.getBean("renderer", MessageRenderer.class);
        mr.render();
    }
}

//  --- bean definitions using @Component ---
//simple bean
@Component("provider")
class ConfigurableMessageProvider implements MessageProvider {

    private String message;

    public ConfigurableMessageProvider
            (@Value("Configurable message") String message) {
        out.println("~~ Injecting '" + message+ "' value into constructor ~~");
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

