package project.todo.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.web.filter.CorsFilter;
import project.todo.security.JwtAuthenticationFilter;
import project.todo.security.OAuthSuccessHandler;
import project.todo.security.RedirectUrlCookieFilter;
import project.todo.service.OAuthUserServiceImpl;

@RequiredArgsConstructor
@Slf4j
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final OAuthUserServiceImpl oAuthUserService;
    private final OAuthSuccessHandler oAuthSuccessHandler; // OAuth 인증절차가 모두 끝나고 성공하면 해당 핸들러가 작동한다.
    private final RedirectUrlCookieFilter redirectUrlCookieFilter;

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
                .oauth2Login()
                .redirectionEndpoint()
                .baseUri("/oauth2/callback/*")

                .and()
                .authorizationEndpoint()
                .baseUri("/auth/authorize") // OAuth 2.0 흐름 시작을 위한 엔드포인트

                .and()
                .userInfoEndpoint()
                .userService(oAuthUserService)

                .and()
                .successHandler(oAuthSuccessHandler)

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new Http403ForbiddenEntryPoint()) // 인증에 실패한 경우

                .and()
                .addFilterAfter(jwtAuthenticationFilter, CorsFilter.class)
                .addFilterBefore(redirectUrlCookieFilter, OAuth2AuthorizationRequestRedirectFilter.class);
        return http.build();
    }
}
