package api.core.dependency.injection.nesting;

import java.util.Objects;

public class TitleProvider {
    private String title = "Gravity";

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public static TitleProvider instance(final String title) {
        var childProvider = new TitleProvider();
        if (Objects.nonNull(title))
            childProvider.setTitle(title);
        return childProvider;
    }
}
