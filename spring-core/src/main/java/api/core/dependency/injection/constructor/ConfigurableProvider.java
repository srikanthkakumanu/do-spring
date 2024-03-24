package api.core.dependency.injection.constructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import api.core.decoupled.MessageProvider;
import api.core.decoupled.MessageRenderer;

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

