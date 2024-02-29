package spring.core.decoupled;

public interface MessageRenderer {
    void render();
    void setProvider(MessageProvider provider);
    MessageProvider getProvider();
}