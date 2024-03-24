package api.core.dependency.injection.dependson;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

public class DependsOnDemo {
    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(Singer.class, Guitar.class);
        ctx.refresh();

        Singer singer = ctx.getBean("singer", Singer.class);
        singer.sing();
    }
}

@Component("guitar")
class Guitar {
    public void sing() {
        System.out.println("Cm Eb Fm Ab Bb");
    }
}

@DependsOn("guitar")
@Component("singer")
class Singer implements ApplicationContextAware {

    private ApplicationContext context;

    public Singer() {}

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.context = context;
    }

    public void sing() {
        Guitar guitar = context.getBean("guitar", Guitar.class);
        guitar.sing();
    }
}
