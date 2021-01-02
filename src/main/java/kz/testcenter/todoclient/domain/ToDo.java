package kz.testcenter.todoclient.domain;

import lombok.Setter;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
public class ToDo {
    private String id;
    private String description;
    private LocalDateTime created;
    private LocalDateTime modified;
    private boolean completed;

    public ToDo() {
        LocalDateTime date = LocalDateTime.now();
        this.id = UUID.randomUUID().toString();
        this.created = date;
        this.modified = date;
        this.completed = false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (! (obj instanceof ToDo)) {
            return false;
        }

        ToDo toDo = (ToDo) obj;

        return this.description.equals(toDo.description)
                && (this.completed = toDo.completed)
                && (this.id.equals(toDo.getId()));
    }

    public ToDo(String description) {
        this();
        this.description = description;
    }
}
