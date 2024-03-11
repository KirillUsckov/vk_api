package ru.kduskov.vkapi.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.kduskov.vkapi.enums.Permission;
import ru.kduskov.vkapi.enums.Role;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityConfiguration {

    @Autowired
    private UserDetailsService userDetailsService;

//    @Bean
//    AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(userDetailsService);
//        provider.setPasswordEncoder(new BCryptPasswordEncoder());
//        return provider;
//    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
//        log.info("START SECURITY");
//
//        return http
//                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
//                        .requestMatchers("/api/v1/user").hasAnyRole(Role.ADMIN.name(), Role.USERS.name())
//                        .requestMatchers("/api/v1/posts").hasAnyRole(Role.POSTS.name(), Role.ADMIN.name())
//                        .anyRequest().authenticated()) //доступ к апи только аутентифицированным пользователям//
//
//                .build();
//
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .anyRequest().anonymous()
                )
                .httpBasic(withDefaults());
        return http.build();
    }
//    @Override
//    SecurityFilterChain configure(HttpSecurity http) throws Exception {
//        return http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
//                .requestMatchers("/users")
//                .hasAnyRole("ADMIN", "USERS")
//                .requestMatchers("/posts")
//                .hasAuthority(Permission.POST_READ.getPermission())
//                .anyRequest()
//                .authenticated()).build();
//    }

}
