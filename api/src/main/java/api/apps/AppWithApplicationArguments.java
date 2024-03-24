package api.apps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * To Pass arguments to Spring Boot Application
 * Maven:-
 * $ ./mvnw spring-boot:run -Dspring-boot.run.arguments="--enable"
 * You should see ## > You are enabled.
 * Also, you can run it like this:
 * $ ./mvnw spring-boot:run -Dspring-boot.run.arguments="arg1,arg2"
 * Or,
 * mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8085
 *
 * Gradle:-
 * Gradle has an issue when to pass arguments like below.
 * ./gradlew bootRun --args='--enable,--customArgument=custom'
 * Hence, use below.
 * java -jar build/libs/api.jar --enable one two
 */
@SpringBootApplication
@ComponentScan("api")
public class AppWithApplicationArguments {
    public static void main(String[] args) {
        SpringApplication.run(AppWithApplicationArguments.class, args);

        /*
        Command-line arguments take precedence over
        application.properties values.
        If necessary, we can stop our application from converting
        command-line arguments to properties like below.

        We can also use short command-line arguments,
        –port=8085 instead of –server.port=8085,
        by using a placeholder in our application.properties:
        server.port=${port:8080}
        */

//        var app = new SpringApplication(AppWithApplicationArguments.class);
//        app.setAddCommandLineProperties(false);
//        app.run(args);

//        new SpringApplicationBuilder(AppWithApplicationArguments.class)
//                .addCommandLineProperties(false);

    }
}

@Component
class MyComponent {

    private static final Logger log =
            LoggerFactory.getLogger(MyComponent.class);

    @Autowired
    public MyComponent(ApplicationArguments args) {
        boolean enable = args.containsOption("enable");

        if(enable)
            log.info("## > You are enabled!");

        List<String> _args = args.getNonOptionArgs();
        log.info("## > extra args ...");
        if(!_args.isEmpty())
            _args.forEach(log::info);
    }
}