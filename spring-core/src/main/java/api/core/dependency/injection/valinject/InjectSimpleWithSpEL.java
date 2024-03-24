package api.core.dependency.injection.valinject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import static java.lang.System.out;

@Component("injectSimpleSpEL")
public class InjectSimpleWithSpEL {
    @Value("#{injectSimpleConfig.name.toUpperCase()}")
    private String name;
    @Value("#{injectSimpleConfig.age + 1}")
    private int age;
    @Value("#{injectSimpleConfig.height}")
    private float height;
    @Value("#{injectSimpleConfig.developer}")
    private boolean developer;
    @Value("#{injectSimpleConfig.ageInSeconds}")
    private Long ageInSeconds;

    public static void main(String[] args) {

        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(InjectSimpleConfig.class, InjectSimpleWithSpEL.class);
        ctx.refresh();
        InjectSimpleWithSpEL simple = (InjectSimpleWithSpEL) ctx.getBean("injectSimpleSpEL");
        out.println(simple);
    }

    public String toString() {
        return "Name: " + name + "\n"
                + "Age: " + age + "\n"
                + "Age in Seconds: " + ageInSeconds + "\n"
                + "Height: " + height + "\n"
                + "Is Developer?: " + developer;
    }
}

@Component("injectSimpleConfig")
class InjectSimpleConfig {
    private String name = "James Bond";
    private int age = 40;
    private float height = 1.92f;
    private boolean developer = false;
    private Long ageInSeconds = 1_241_401_112L;
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public float getHeight() {
        return height;
    }
    public boolean isDeveloper() {
        return developer;
    }
    public Long getAgeInSeconds() {
        return ageInSeconds;
    }
}