package videos.apps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.Order;

/**
 * ApplicationRunner and CommandLineRunner used to run the logic during
 * application startup.
 *
 * ApplicationRunner — It is a Functional Interface with a run() method.
 * run() will get executed, just after ApplicationContext is created and
 * before spring boot application startup. It takes ApplicationArgument
 * which has convenient methods like getOptionNames(), getOptionValues()
 * and getSourceArgs().
 *
 * CommandLineRunner — It is also a Functional Interface with a run() method.
 * run() will get executed, just after ApplicationContext is created and before
 * spring boot application startup. It accepts the argument, which can be passed
 * at the time of server startup.
 *
 * Both of them provide the same functionality and the only difference between
 * CommandLineRunner and ApplicationRunner is that CommandLineRunner.run()
 * accepts String array[] whereas ApplicationRunner.run() accepts
 * ApplicationArguments as an argument.
 *
 * They both are practically the same. It is not necessary to implement both
 * at the same time. We can add as many methods that return ApplicationRunner
 * or CommandLineRunner as we want.
 *
 * If we want them to execute in a certain order, we can use the @Order annotation.
 *
 * java -jar build/libs/videos.jar --enable one two
 *
 */
@SpringBootApplication
@ComponentScan("videos")
public class AppWithRunners {

    private static final Logger log =
            LoggerFactory.getLogger(AppWithRunners.class);

    public static void main(String[] args) {
        SpringApplication.run(AppWithRunners.class, args);
    }

    @Autowired
    String info;

    @Bean
    String info() { return "This is just a String bean"; }

    @Bean
    @Order(3)
    ApplicationRunner appRunner() {
        return args -> {
            log.info("## > ApplicationRunner Implementation...");
            log.info("Accessing the Info bean: " + info);
            args.getNonOptionArgs().forEach(log::info);
        };
    }

    @Bean
    @Order(1)
    CommandLineRunner cmdLineRunner() {
        return args -> {
            log.info("## > CommandLineRunner Implementation...");
            log.info("Accessing the Info bean: " + info);
            for(String arg:args)
                log.info(arg);
        };
    }

    @Bean
    @Order(2)
    CommandLineRunner otherCmdLineRunner() {
        return args -> {
            log.info("## > OtherCommandLineRunner Implementation...");
            log.info("Accessing the Info bean: " + info);
            for(String arg:args)
                log.info(arg);
        };
    }
}
