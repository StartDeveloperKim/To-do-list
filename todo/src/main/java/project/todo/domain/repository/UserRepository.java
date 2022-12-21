package project.todo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.todo.domain.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

    User findByUsername(String username);

    Boolean existsByUsername(String username);

    User findByUsernameAndPassword(String username, String password);
}
