package com.intranet.portal.config;

import com.intranet.portal.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final org.springframework.security.authentication.AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> {})
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/employees/birthdays/this-month").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/employees/me").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/employees").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/employees/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/employees").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/employees/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/employees/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/employees/net-salary").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/departments").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/departments/{id}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/api/departments").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/departments/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/departments/{id}").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/positions").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/positions/{id}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/api/positions").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/positions/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/positions/{id}").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/attendances/check-in/me").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/api/attendances/check-in").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/attendances").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/attendances/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/attendances").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/attendances/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/attendances/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/attendances/late").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/work-permits").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/api/work-permits/me").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/work-permits/my").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/work-permits").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/work-permits/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/work-permits/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/work-permits/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/work-permits/{id}/approve").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/work-permits/monthly-duration").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/dashboard/summary").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}