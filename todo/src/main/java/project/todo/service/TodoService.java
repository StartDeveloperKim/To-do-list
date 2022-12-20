package project.todo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.todo.domain.entity.Todo;
import project.todo.domain.repository.TodoRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class TodoService {

    private final TodoRepository todoRepository;

    public List<Todo> create(final Todo todo) {
        validate(todo);
        todoRepository.save(todo);

        log.info("Entity Id : {} is saved.", todo.getId());

        return todoRepository.findByUserId(todo.getUserId());
    }

    public List<Todo> retrieve(final String userId) {
        return todoRepository.findByUserId(userId);
    }

    public List<Todo> update(final Todo todo) {
        validate(todo);

        Todo findTodo = todoRepository.findById(todo.getId())
                .orElseThrow(() -> new EntityNotFoundException("엔티티가 없습니다."));

        findTodo.update(todo.getTitle(), todo.isDone());

        return retrieve(findTodo.getUserId());
    }

    public List<Todo> delete(final Todo todo) {
        validate(todo);

        try {
            todoRepository.delete(todo);
        } catch (Exception e) {
            log.error("error deleting entity ", todo.getId(), e);

            throw new RuntimeException("error deleting entity " + todo.getId());
        }

        return retrieve(todo.getUserId());
    }

    private void validate(Todo todo) {
        if (todo == null) {
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null");
        }
        if (todo.getUserId() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }

}
