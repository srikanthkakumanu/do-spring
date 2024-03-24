package api.core.dependency.injection.constructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import api.core.decoupled.MessageProvider;
import api.core.decoupled.MessageRenderer;

import static java.lang.System.out;

//complex bean requiring a dependency
@Component("renderer")
class StandardOutMessageRenderer implements MessageRenderer {
    private MessageProvider provider;

    @Autowired
    public StandardOutMessageRenderer(MessageProvider provider) {
        out.println(" ~~ Injecting dependency using constructor ~~");
        this.provider = provider;
    }

    @Override
    public void setProvider(MessageProvider provider) {
        this.provider = provider;
    }

    @Override
    public MessageProvider getProvider() {
        return this.provider;
    }

    @Override
    public void render() {
        if (provider == null) {
            throw new RuntimeException(
                    "You must set the property messageProvider of class:"
                            + StandardOutMessageRenderer.class.getName());
        }
        out.println(provider.getMessage());
    }
}