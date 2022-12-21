package project.todo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.todo.domain.entity.User;
import project.todo.domain.repository.UserRepository;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    public User create(final User user) {
        if (user == null || user.getUsername() == null) {
            throw new RuntimeException("Invalid arguments");
        }
        final String username = user.getUsername();
        if (userRepository.existsByUsername(username)) {
            log.warn("Username already exists {}", username);
            throw new RuntimeException("Username already exists");
        }

        return userRepository.save(user);
    }

    public User getByCredentials(final String username, final String password, final PasswordEncoder encoder) {
        final User originalUser = userRepository.findByUsername(username);

        if (originalUser != null && encoder.matches(password, originalUser.getPassword())) {
            return originalUser;
        }
        return null;
    }
}
