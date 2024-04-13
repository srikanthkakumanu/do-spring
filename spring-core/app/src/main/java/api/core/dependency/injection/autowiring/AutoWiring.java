package api.core.dependency.injection.autowiring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;

import java.util.UUID;

public class AutoWiring {

    public static void main(String[] args) {

        var ctx = new AnnotationConfigApplicationContext(AutowiringConfiguration.class);
        var target = ctx.getBean(Target.class);
        System.out.println("\n<-----Autowiring->Constructor----->");
        System.out.println("Created target? " + (target != null));
        System.out.println("Injected bar? " + (target.bar != null));
        System.out.println("Injected fooOne? " + (target.fooOne != null ? target.fooOne.id: ""));
        System.out.println("Injected fooTwo? {}" + (target.fooTwo != null ? target.fooTwo.id : ""));

        System.out.println("\n<-----Autowiring->byType----->");
        var anotherTarget = ctx.getBean(AnotherTarget.class);
        System.out.println("anotherTarget: Created anotherTarget? " + (anotherTarget != null));
        System.out.println("anotherTarget: Injected bar? " + (anotherTarget.bar != null));
        System.out.println("anotherTarget: Injected fooOne? " + (anotherTarget.fooOne != null ? anotherTarget.fooOne.id: ""));
        System.out.println("anotherTarget: Injected fooTwo? " + (anotherTarget.fooTwo != null ? anotherTarget.fooTwo.id : ""));

        System.out.println("\n<-----Autowiring->byName----->");
        var fieldTarget = ctx.getBean(FieldTarget.class);
        System.out.println("fieldTarget: Created fieldTarget? " + (fieldTarget != null));
        System.out.println("fieldTarget: Injected bar? " + (fieldTarget.bar != null));
        System.out.println("fieldTarget: Injected fooOne? " + (fieldTarget.fooOne != null ? fieldTarget.fooOne.id: ""));
        System.out.println("fieldTarget: Injected fooTwo? " + (fieldTarget.fooTwo != null ? fieldTarget.fooTwo.id : ""));
    }
}


@Configuration
@ComponentScan
class AutowiringConfiguration {
    @Bean
    Foo anotherFoo() {
        return new Foo();
    }
}

// Used for byType autowiring
@Component
@Lazy
class AnotherTarget {

    Foo fooOne;
    Foo fooTwo;
    Bar bar;

    @Autowired
    public void setFooOne(@Qualifier("foo")Foo fooOne) {
        System.out.println(" --> AnotherTarget#setFooOne(Foo) called");
        this.fooOne = fooOne;
    }

    @Autowired
    public void setFooTwo(@Qualifier("anotherFoo")Foo fooTwo) {
        System.out.println(" --> AnotherTarget#setFooTwo(Foo) called");
        this.fooTwo = fooTwo;
    }

    @Autowired
    public void setBar(Bar bar) {
        System.out.println(" --> AnotherTarget#setBar(Bar) called");
        this.bar = bar;
    }
}

@Component
@Lazy
class FieldTarget {

    @Autowired @Qualifier("foo") Foo fooOne;
    @Autowired @Qualifier("anotherFoo") Foo fooTwo;
    @Autowired Bar bar;

}


// used for constructor autowiring
@Component
@Lazy
class Target {

    Foo fooOne;
    Foo fooTwo;
    Bar bar;

    public Target() {
        System.out.println(" --> Target() called");
    }

    public Target(@Qualifier("foo") Foo foo) {
        this.fooOne = foo;
        System.out.println(" --> Target(Foo) called");
    }

    @Autowired
    public Target(@Qualifier("foo") Foo foo, Bar bar) {
        this.fooOne = foo;
        this.bar = bar;
        System.out.println(" --> Target(Foo, Bar) called");
    }
}

@Component
class Foo {
    String id = UUID.randomUUID()
            .toString()
            .replace("-","")
            .substring(0,8);
}
@Component
class Bar {}
