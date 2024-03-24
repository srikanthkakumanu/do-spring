package api.apps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@ComponentScan("api") // base
public class App {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String... args) {

        SpringApplication.run(App.class, args);

        /* -- Advanced Configuration combinations -- */

//        var app = new SpringApplication(App.class);
//        app.run(args);

        // Change the banner via configuration
//        var app = new SpringApplication(App.class);
//        app.setBanner((e, c, o) -> o.print("\n\n\tThis is my own banner!\n\n".toUpperCase()));
//        app.run(args);

        // Disable/Enable the banner via programmatic approach
//        var app = new SpringApplication(App.class);
//        app.setBannerMode(Banner.Mode.OFF);
//        app.run(args);

        // SpringApplicationBuilder Fluent API builder for
        // SpringApplication and ApplicationContext

//        new SpringApplicationBuilder()
//                .bannerMode(Banner.Mode.OFF)
//                .sources(App.class)
//                .run(args);

        // Can have a hierarchy
        // If you have a web configuration, make sure that it’s being declared as a child. Also
        //parent and children must share the same org.springframework.core.env.Environment
        //interface (this represents the environment in which the current application is running; it
        //is related to profiles and properties declarations).
//        new SpringApplicationBuilder(App.class)
//                //.child(OtherConfig.class) // can define hierarchy
//                //.logStartupInfo(false) // startup log info can be disabled/enabled
//                .profiles("prod", "cloud") // can activate profiles
//                .run(args);

        // can attach listeners for some of the ApplicationEvent events
        // add the necessary logic to handle those events.
        // can also have additional events:
        // ApplicationStartedEvent -- this is sent at the start
        // ApplicationEnvironmentPreparedEvent -- this is sent when the environment is known
        // ApplicationPreparedEvent -- this is sent after the bean definitions
        // ApplicationReadyEvent -- this is sent when the application is ready
        // ApplicationFailedEvent -- in case of exception during the startup
//        new SpringApplicationBuilder(App.class)
//                .listeners(event -> log.info("#### > " + event.
//                        getClass().getCanonicalName()))
//                .run(args);

        /*
        Can remove any web environment autoconfiguration from happening
        Remember that Spring Boot guesses which kind of app you are running based on the
        classpath. For a web app, the algorithm is very simple; but imagine that you are using
        libraries that actually run without a web environment, and your app is not a web app, but
        Spring Boot configures it as such.
        The WebApplicationType is an enum. Your app can be configured as
        WebApplicationType.NONE,
        WebApplicationType.SERVLET, and WebApplicationType.REACTIVE.
        */
//        new SpringApplicationBuilder(App.class)
//                .web(WebApplicationType.NONE)
//                .run(args);
    }
}

@Configuration
class OtherConfig {
    @Bean
    String text() { return "Hi There!"; }
}
