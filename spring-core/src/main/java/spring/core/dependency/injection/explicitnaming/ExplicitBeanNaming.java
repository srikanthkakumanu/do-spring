package spring.core.dependency.injection.explicitnaming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Arrays;

public class ExplicitBeanNaming {
    private static Logger logger = LoggerFactory.getLogger(ExplicitBeanNaming.class);


    public static void main(String... args) {
        var ctx = new AnnotationConfigApplicationContext(BeanNamingCfg.class);
        Arrays.stream(ctx.getBeanDefinitionNames())
                .forEach(logger::debug);


        var simpleBeans = ctx.getBeansOfType(SimpleBean.class);
        simpleBeans.forEach((k,v) -> {
            var aliases = ctx.getAliases(k);
            if(aliases.length > 0) {
                logger.debug("Aliases for {} ", k);
                Arrays.stream(aliases).forEach(a -> logger.debug("\t {}", a));
            }
        });
    }
}

@Configuration
@ComponentScan
class BeanNamingCfg {

    //@Bean(name="simpleBeanTwo")
    //@Bean(value= "simpleBeanTwo")
    @Bean("simpleBeanTwo")
    public SimpleBean simpleBean2(){
        return new SimpleBean();
    }

    //@Bean(name= {"simpleBeanThree", "three", "numero_tres"})
    //@Bean(value= {"simpleBeanThree", "three", "numero_tres"})
    @Bean({"simpleBeanThree", "three", "moodu"})
    public SimpleBean simpleBean3(){
        return new SimpleBean();
    }
}

@Component("simpleBeanOne")
//@Component(value = "simpleBeanOne")
class SimpleBean { }