package com.bbm.fomezero.security;

import com.bbm.fomezero.exception.handler.CustomAccessDeniedHandler;
import com.bbm.fomezero.exception.handler.CustomUnauthorizedHandler;
import com.bbm.fomezero.model.enums.Permission;
import com.bbm.fomezero.model.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static com.bbm.fomezero.model.enums.Permission.ADMIN_READ;
import static com.bbm.fomezero.model.enums.Permission.DRIVER_READ;
import static com.bbm.fomezero.model.enums.Role.*;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final LogoutHandler logoutHandler;
    private final JWTAuthenticationFilter authenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final CustomUnauthorizedHandler customUnauthorizedHandler;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors((corsConfigurer) -> corsConfigurer
                        .configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(
                                "/api/v1/auth/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**"
                        ).permitAll()

                        .requestMatchers(GET, "/api/v1/drivers").hasRole(ADMIN.name())
                        .requestMatchers(POST, "/api/v1/users/", "/api/v1/drivers/").permitAll()
                        .requestMatchers("/api/v1/users", "/api/v1/users/**").hasRole(ADMIN.name())
                        .requestMatchers(POST, "/api/v1/restaurants/").hasRole(RESTAURANT_OWNER.name())
                        .requestMatchers(GET, "/api/v1/drivers/profile").hasAnyAuthority(DRIVER_READ.name(), ADMIN_READ.name())
                        .anyRequest()
                        .authenticated())
                .exceptionHandling((exception) -> exception
                        .accessDeniedHandler(customAccessDeniedHandler)
                        .authenticationEntryPoint(customUnauthorizedHandler))
                .sessionManagement((sessionManager) -> sessionManager
                        .sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout((logOutConfigurer) -> logOutConfigurer
                        .logoutUrl("/api/v1/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) ->
                                SecurityContextHolder.clearContext())
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));
        configuration.setExposedHeaders(Arrays.asList("Authorization", "X-Get-Header", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        configuration.setMaxAge(3600L);
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
