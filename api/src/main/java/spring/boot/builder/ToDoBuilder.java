package spring.boot.builder;

import spring.boot.domain.ToDo;

import java.time.LocalDateTime;
import java.util.Objects;

public final class ToDoBuilder {

    private static ToDoBuilder instance = new ToDoBuilder();
    private String id = null;
    private String description = "";
    private LocalDateTime created;
    private LocalDateTime modified;
    private boolean completed;

    private ToDoBuilder() {}

    public static ToDoBuilder create() {
        return instance;
    }

    public ToDoBuilder withDescription(String description) {
        this.description = description;
        return instance;
    }

    public ToDoBuilder withId (String id) {
        this.id = id;
        return instance;
    }

    public ToDoBuilder withCreated(LocalDateTime created) {
        this.created = created;
        return instance;
    }

    public ToDoBuilder withModified(LocalDateTime modified) {
        this.modified = modified;
        return instance;
    }

    public ToDoBuilder withCompleted(boolean completed) {
        this.completed = completed;
        return instance;
    }

    public ToDo build() {
        ToDo result = new ToDo(this.description);
        if (Objects.nonNull(id)) result.setId(this.id);
        if (Objects.nonNull(created)) result.setCreated(this.created);
        if(Objects.nonNull(modified)) result.setModified(this.modified);
//        if(result.isCompleted() != this.completed)
//            result.setCompleted(this.completed);

        return result;
    }
}
