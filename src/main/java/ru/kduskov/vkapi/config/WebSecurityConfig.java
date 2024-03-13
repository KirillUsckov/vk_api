package ru.kduskov.vkapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import ru.kduskov.vkapi.components.CustomAccessDeniedHandler;
import ru.kduskov.vkapi.service.auth.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/albums/*").hasAnyRole("ALBUMS_VIEWER", "ADMIN", "ALBUMS", "POSTS_ALBUMS", "VIEWER")
                        .requestMatchers(HttpMethod.GET, "/posts/*").hasAnyRole("ADMIN", "POSTS", "POSTS_ALBUMS", "VIEWER")
                        .requestMatchers(HttpMethod.GET, "/users/*").hasAnyRole("ADMIN", "USERS", "VIEWER")
                        .requestMatchers("/users/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USERS")
                        .requestMatchers("/posts/**").hasAnyRole("ADMIN", "POSTS", "POSTS_ALBUMS")
                        .requestMatchers("/albums/**").hasAnyRole("ADMIN", "ALBUMS", "POSTS_ALBUMS")
                        .requestMatchers("/auth/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .httpBasic();
        http.exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler);

        return http.build();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }
}
