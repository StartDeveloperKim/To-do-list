package project.todo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import project.todo.domain.entity.User;
import project.todo.domain.repository.UserRepository;
import project.todo.security.ApplicationOAuth2User;

@Slf4j
@Service
public class OAuthUserServiceImpl extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public OAuthUserServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 기존 loadUser를 호출한다.
        final OAuth2User oAuth2User = super.loadUser(userRequest);

        final String username = (String) oAuth2User.getAttributes().get("login");
        final String authProvider = userRequest.getClientRegistration().getClientName();

        User user = null;
        if (!userRepository.existsByUsername(username)) {
            user = User.builder()
                    .username(username)
                    .authProvider(authProvider)
                    .build();
            user = userRepository.save(user);
        } else {
            user = userRepository.findByUsername(username);
        }

        log.info("Successfully pulled user info username {} authProvider {}", username, authProvider);
        return new ApplicationOAuth2User(user.getId(), oAuth2User.getAttributes());
    }
}
