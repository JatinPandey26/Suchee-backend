package com.suchee.app.config;

import com.suchee.app.security.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {


    @Value("${auth.token.generator-type:basic}")
    private String generatorType;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CookieManagementFilter cookieManagementFilter;


    /**
     * Configures the application's HTTP security settings.
     *
     * <p>This method sets up the security filter chain using Spring Security.
     * It applies authentication and authorization rules based on the configured
     * {@code generatorType}, which can be either {@code "jwt"} or {@code "basic"}.</p>
     *
     * @param http the {@link HttpSecurity} object used to configure HTTP security
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception if an error occurs during configuration
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults()).csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login","/api/userAccount/create","/api/utility/public/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated()
                );

        if(generatorType.equals("jwt"))
        {
            http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
            http.addFilterBefore(cookieManagementFilter,jwtAuthenticationFilter.getClass());
        }
        if(generatorType.equals("basic")){
            http.httpBasic(Customizer.withDefaults());
        }

        return http.build();
    }

/**
 * Provides the appropriate {@link AuthenticationTokenGenerator} bean based on the configuration.
 *
 * @return a concrete implementation of {@link AuthenticationTokenGenerator}
 * @throws IllegalArgumentException if an invalid generator type is provided
 */
    @Bean
    public AuthenticationTokenGenerator authenticationTokenGenerator() {
        switch (generatorType.toLowerCase()) {
            case "jwt":
                return new JwtService();
            case "basic":
                return new BasicAuthenticationTokenGenerator();
            default:
                throw new IllegalArgumentException("Invalid token generator type: " + generatorType);
        }
    }

}
