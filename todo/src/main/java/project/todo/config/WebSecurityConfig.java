package project.todo.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.web.filter.CorsFilter;
import project.todo.security.JwtAuthenticationFilter;
import project.todo.security.OAuthSuccessHandler;
import project.todo.service.OAuthUserServiceImpl;

@RequiredArgsConstructor
@Slf4j
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final OAuthUserServiceImpl oAuthUserService;
    private final OAuthSuccessHandler oAuthSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors()
                .and()

                .csrf().disable()
                .httpBasic().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Session 기반이 아님을 선언

                .and()
                .authorizeRequests()
                .antMatchers("/", "/auth/**", "/oauth2/**").permitAll()
                .anyRequest().authenticated()

                .and()
                .addFilterAfter(jwtAuthenticationFilter, CorsFilter.class)
                .oauth2Login()
                .redirectionEndpoint()
                .baseUri("/oauth2/callback/*")

                .and()
                .authorizationEndpoint()
                .baseUri("/auth/authorize")
                
                .and()
                .userInfoEndpoint()
                .userService(oAuthUserService)

                .and()
                .successHandler(oAuthSuccessHandler)

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new Http403ForbiddenEntryPoint()); // 인증에 실패한 경우

        return http.build();
    }
}
