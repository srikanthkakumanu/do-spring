package api.core.dependency.pull;

import static java.lang.System.out;

/**
 * IoC is of two types:
 * Dependency Lookup and Dependency Injection.
 *
 * Dependency Lookup is of two types:
 * Dependency Pull and Contextualized Dependency Lookup (CDL).
 *
 * Dependency Pull is just a JNDI lookup for an object from a centralized
 * registry (like JNDI registry).
 *
 * In CDL, the lookup is performed against the container that is managing the
 * resource, not from some central registry, and it is usually performed at
 * some set point.
 */

interface Container {
    Object getDependency(String key);
}

interface ManagedComponent {
    void performLookup(Container container);
}

class DefaultContainer implements Container {
    @Override
    public Object getDependency(String key) {
        if("provider".equals(key)) {
            return new HelloWorldMessageProvider();
        }

        throw new RuntimeException("Unknown dependency: " + key);
    }
}

interface MessageProvider {
    String getMessage();
}

class HelloWorldMessageProvider implements MessageProvider {

    public HelloWorldMessageProvider(){
        out.println(" --> HelloWorldMessageProvider: constructor called");
    }

    @Override
    public String getMessage() {
        return "Hello World!";
    }
}

interface MessageRenderer extends ManagedComponent {
    void render();
}

class StandardOutMessageRenderer implements MessageRenderer {

    private MessageProvider messageProvider;

    public StandardOutMessageRenderer(){
        out.println(" --> StandardOutMessageRenderer: constructor called");
    }

    @Override
    public void performLookup(Container container) {
        this.messageProvider = (MessageProvider) container.getDependency("provider");
    }

    @Override
    public String toString() {
        return messageProvider.toString();
    }

    @Override
    public void render() {
        if (messageProvider == null) {
            throw new RuntimeException(
                    "You must set the property messageProvider of class:"
                            + StandardOutMessageRenderer.class.getName());
        }
        out.println(messageProvider.getMessage());
    }
}
public class CDLDemo {
    public static void main(String[] args) {
        Container container = new DefaultContainer();

        MessageRenderer renderer = new StandardOutMessageRenderer();
        renderer.performLookup(container);

        renderer.render();
    }
}
