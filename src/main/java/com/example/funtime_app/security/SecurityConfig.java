package com.example.funtime_app.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security, JwtFilter jwtFilter) throws Exception {

        security.authorizeHttpRequests(m->{
            m.requestMatchers("/v3/api-docs/**",
                            "/api/posts/**",
                            "/api/user/**",
                            "/swagger-ui/**",
                            "/swagger-ui.html",
                            "/api/auth/**",
                            "/api/user/edit/**",
                            "/api/posts/save"
                            ).permitAll()
//                    .requestMatchers("/api/posts/**").hasRole("ADMIN")
//                    .requestMatchers("/api/admin/**").hasRole("ADMIN")
                    .requestMatchers("/api/posts/popular").hasRole("USER")
                    .anyRequest()
                    .authenticated();
        });

        security.csrf(AbstractHttpConfigurer::disable);
        security.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        security.sessionManagement(m->{
            m.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });
        return security.build();

    }

    @Bean
    public AuthenticationProvider provider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        return new ProviderManager(provider());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
