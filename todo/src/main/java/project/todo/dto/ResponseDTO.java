package project.todo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class ResponseDTO<T> {
    private String error;
    private List<T> data;

    @Builder
    public ResponseDTO(String error, List<T> data) {
        this.error = error;
        this.data = data;
    }
}
