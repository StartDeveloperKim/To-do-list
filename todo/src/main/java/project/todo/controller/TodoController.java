package project.todo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.todo.domain.entity.Todo;
import project.todo.dto.ResponseDTO;
import project.todo.dto.TodoDTO;
import project.todo.service.TodoService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todoDTO) {
        try {
            String temporaryUserId = "temporary-user";

            Todo todo = TodoDTO.toEntity(todoDTO);
            todo.setId(null);
            todo.setUserId(temporaryUserId);

            List<Todo> todos = todoService.create(todo);
            List<TodoDTO> dtos = todos.stream().map(TodoDTO::new).collect(Collectors.toList());
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<?> retrieveTodoList() {
        String temporaryUserId = "temporary-user";
        List<Todo> entities = todoService.retrieve(temporaryUserId);
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<?> updateTodo(@RequestBody TodoDTO todoDTO) {
        String temporaryUserId = "temporary-user";

        Todo todo = TodoDTO.toEntity(todoDTO);
        todo.setUserId(temporaryUserId); // 수정 예정
        List<Todo> entities = todoService.update(todo);

        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO todoDTO) {
        try {
            String temporaryUserId = "temporary-user";

            Todo todo = TodoDTO.toEntity(todoDTO);

            todo.setUserId(temporaryUserId);

            List<Todo> entities = todoService.delete(todo);
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
