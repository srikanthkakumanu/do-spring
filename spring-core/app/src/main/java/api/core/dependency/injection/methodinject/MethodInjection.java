package api.core.dependency.injection.methodinject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import static java.lang.System.out;

public class MethodInjection {
    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(LookupConfig.class);

        var abstractLockOpener = ctx.getBean("abstractLockOpener", LockOpener.class);
        var standardLockOpener = ctx.getBean("standardLockOpener", LockOpener.class);

        displayInfo("abstractLockOpener", abstractLockOpener);
        displayInfo("standardLockOpener", standardLockOpener);
    }

    private static void displayInfo(String beanName, LockOpener lockOpener) {
        var keyHelperOne = lockOpener.getMyKeyOpener();
        var keyHelperTwo = lockOpener.getMyKeyOpener();

        out.println("[" + beanName + "]: KeyHelper Instances the Same?  " + (keyHelperOne == keyHelperTwo));

        var stopWatch = new StopWatch();
        stopWatch.start("lookupDemo");

        for (var x = 0; x < 100_000; x++) {
            var keyHelper = lockOpener.getMyKeyOpener();
            keyHelper.open();
        }
        stopWatch.stop();
        out.println("100000 gets took " + stopWatch.getTotalTimeMillis() + " ms");
    }
}

@Configuration
@ComponentScan
class LookupConfig {}

@Component("keyHelper")
@Scope("prototype")
class KeyHelper {
    public void open() {}
}

interface LockOpener {
    KeyHelper getMyKeyOpener();
    void openLock();
}

@Component("standardLockOpener")
class StandardLockOpener implements LockOpener {

    private KeyHelper keyHelper;

    @Autowired
    @Qualifier("keyHelper")
    public void setKeyHelper(KeyHelper keyHelper) { this.keyHelper = keyHelper; }
    @Override
    public KeyHelper getMyKeyOpener() {
        return keyHelper;
    }

    @Override
    public void openLock() {
        keyHelper.open();
    }
}

@Component("abstractLockOpener")
abstract class AbstractLockOpener implements LockOpener {

    @Lookup("keyHelper")
    @Override
    public abstract KeyHelper getMyKeyOpener() ;

    @Override
    public void openLock() {
        getMyKeyOpener().open();
    }
}