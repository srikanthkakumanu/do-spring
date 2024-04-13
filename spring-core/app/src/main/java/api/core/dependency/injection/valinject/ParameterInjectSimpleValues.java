package api.core.dependency.injection.valinject;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import static java.lang.System.out;

@Component("injectSimple")
public class ParameterInjectSimpleValues {

    @Value("James Bond") private String name;
    @Value("32") private int age;
    @Value("1.94") private float height;
    @Value("true") private boolean developer;
    @Value("124232453432") private Long ageInSeconds;

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(ParameterInjectSimpleValues.class);
        ctx.refresh();

        ParameterInjectSimpleValues simple
                = (ParameterInjectSimpleValues) ctx.getBean("injectSimple");
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


