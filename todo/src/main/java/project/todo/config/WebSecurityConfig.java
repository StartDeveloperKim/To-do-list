package project.todo.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;
import project.todo.security.JwtAuthenticationFilter;

@RequiredArgsConstructor
@Slf4j
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

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
                .antMatchers("/", "/auth/**").permitAll()
                .anyRequest().authenticated()

                .and()
                .addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);

        return http.build();
    }
}
