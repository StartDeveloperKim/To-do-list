package project.todo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import project.todo.domain.entity.Todo;

@NoArgsConstructor
@Getter
public class TodoDTO {

    private String id;
    private String title;
    private boolean done;

    public TodoDTO(final Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.done = todo.isDone();
    }

    public static Todo toEntity(final TodoDTO dto) {
        return Todo.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .done(dto.isDone())
                .build();
    }


}
