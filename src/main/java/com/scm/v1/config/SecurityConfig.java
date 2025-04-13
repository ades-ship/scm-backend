package com.scm.v1.config;

import com.scm.v1.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance(); // ⚠️ Don't use in production
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userService;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
         DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
         authProvider.setUserDetailsService(userService);
         authProvider.setPasswordEncoder(passwordEncoder());
         return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // disable CSRF for Postman testing
            .authorizeHttpRequests(auth -> auth
                // .requestMatchers("/api/contact/**").hasRole("USER")
                .anyRequest().permitAll()
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}


// @Configuration
// public class SecurityConfig {

//     private final UserService userService;

//     public SecurityConfig(UserService userService) {
//         this.userService = userService;
//     }
    
//     // Expose your custom UserDetailsService
//     @Bean
//     public UserDetailsService userDetailsService() {
//         return userService;
//     }

//     // Define a PasswordEncoder (here for testing, using plain text; do not use in production)
//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return NoOpPasswordEncoder.getInstance();
//     }

  //      // Register the DaoAuthenticationProvider that uses your UserDetailsService and PasswordEncoder
        // @Bean
        // public DaoAuthenticationProvider authenticationProvider() {
        //      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        //      authProvider.setUserDetailsService(userService);
        //      authProvider.setPasswordEncoder(passwordEncoder());
        //      return authProvider;
        // }

//     // Expose the AuthenticationManager bean
//     @Bean
//     public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//          return config.getAuthenticationManager();
//     }

//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//          http
//              .csrf(csrf -> csrf.disable())  // Disable CSRF for testing with Postman
//              // Register the authenticationProvider with the HttpSecurity builder
//              .authenticationProvider(authenticationProvider())
//              .authorizeHttpRequests(auth -> auth
//                  .requestMatchers("/api/**").hasRole("USER")
//                  .anyRequest().denyAll()
//              )
//              .httpBasic(Customizer.withDefaults());  // Enable HTTP Basic authentication

//          return http.build();
//     }
// }
