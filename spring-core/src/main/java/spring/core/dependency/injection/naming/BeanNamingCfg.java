package spring.core.dependency.injection.naming;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

@Configuration
// @ComponentScan // default bean naming follows
// below stmt customizes the bean naming style default behavior
@ComponentScan(nameGenerator = SimpleNameGenerator.class)
public class BeanNamingCfg {

    @Bean
    public SimpleBean anotherSimpleBean () {
        return new SimpleBean ();
    }
}

class SimpleNameGenerator extends AnnotationBeanNameGenerator {
    @Override
    protected String buildDefaultBeanName(BeanDefinition definition,
                                          BeanDefinitionRegistry registry) {

        var beanName =
                Objects.requireNonNull(definition.getBeanClassName())
                .substring(definition
                        .getBeanClassName()
                        .lastIndexOf(".") + 1)
                .toLowerCase(Locale.ROOT);
        System.out.println("beanName: " + beanName);

        var uid = UUID.randomUUID()
                .toString()
                .replace("-","")
                .substring(0,8);
        System.out.println("uid: " + uid);

        return beanName + "-" + uid;
    }
}