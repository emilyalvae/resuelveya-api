package com.resuelveya.resuelve_api.security.config;

import com.resuelveya.resuelve_api.security.domain.service.CustomUserDetailsService;
import com.resuelveya.resuelve_api.security.filter.JwtAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAccessDeniedHandler accessDeniedHandler;
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            CustomUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder,
            JwtAuthenticationEntryPoint authenticationEntryPoint,
            JwtAccessDeniedHandler accessDeniedHandler
    ) {
        this.jwtAuthenticationFilter =
                jwtAuthenticationFilter;

        this.userDetailsService =
                userDetailsService;

        this.passwordEncoder =
                passwordEncoder;

        this.authenticationEntryPoint =
                authenticationEntryPoint;

        this.accessDeniedHandler =
                accessDeniedHandler;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(
                        userDetailsService
                );

        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception{
        logger.info("=== CONFIGURANDO SECURITY FILTER CHAIN ===");
        logger.info("Configurando reglas de autorización:");
        logger.info("  - /api/v1/auth/** → permitAll()");
        logger.info("  - GET /api/especialidades/** → permitAll()");
        logger.info("  - /api/v1/usuarios/** → hasRole('ADMIN')");
        logger.info("  - /error → permitAll()");
        logger.info("  - Cualquier otra petición → authenticated()");
        
        return  http
                .csrf(csrf->csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        ))
                .exceptionHandling(exceptions ->
                        exceptions
                                .authenticationEntryPoint(
                                        authenticationEntryPoint
                                )
                                .accessDeniedHandler(
                                        accessDeniedHandler
                                )
                )

                .authorizeHttpRequests(auth->auth
                        .requestMatchers(
                                "/api/v1/auth/**"
                        )
                        .permitAll()

                        .requestMatchers(
                                HttpMethod.GET,"/api/especialidades/**"
                        ).permitAll()

                        .requestMatchers("/api/v1/usuarios/**")
                        .hasRole("ADMIN")

                        .requestMatchers(
                                "/error"
                        )
                        .permitAll()

                        .anyRequest()
                        .authenticated()



                ).authenticationProvider(
                authenticationProvider()
        )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }
}
