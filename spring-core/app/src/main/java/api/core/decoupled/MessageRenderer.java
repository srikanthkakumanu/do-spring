package api.core.decoupled;

public interface MessageRenderer {
    void render();
    void setProvider(MessageProvider provider);
    MessageProvider getProvider();
}