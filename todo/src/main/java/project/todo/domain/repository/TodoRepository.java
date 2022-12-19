package project.todo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.todo.domain.entity.Todo;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, String> {

    @Query("select t from Todo t where t.userId=:userId")
    List<Todo> findByUserId(@Param("userId") String userId);
}
