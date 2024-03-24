package api.core.dependency.injection.naming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class BeanNaming {

    private static final Logger logger = LoggerFactory.getLogger(BeanNaming.class);

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(BeanNamingCfg.class);
        Arrays.stream(ctx.getBeanDefinitionNames())
                .forEach(logger::debug);

        try { ctx.getBean(SimpleBean.class); }
        catch (Exception e) { logger.debug("More beans than expected found. ", e); }

        logger.info("\n... All beans of type: {} ", SimpleBean.class.getSimpleName());
        var beans = ctx.getBeansOfType(SimpleBean.class);
        beans.forEach((key, value) -> System.out.println(key));
    }
}


