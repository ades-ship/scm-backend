package com.scm.v1.config;

// import com.scm.v1.services.UserService;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.Customizer;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.NoOpPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.crypto.password.PasswordEncoder;

// @Configuration
// public class SecurityConfig {

//     private final UserService userService;

//     public SecurityConfig(UserService userService) {
//         this.userService = userService;
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//     return new BCryptPasswordEncoder();// ⚠️ Don't use in production
//     }

//     @Bean
//     public UserDetailsService userDetailsService() {
//         return (UserDetailsService) userService;
//     }

//     @Bean
//     public DaoAuthenticationProvider authenticationProvider() {
//          DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//          authProvider.setUserDetailsService((UserDetailsService) userService);
//          authProvider.setPasswordEncoder(passwordEncoder());
//          return authProvider;
//     }

//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//         http
//             .csrf(csrf -> csrf.disable()) // disable CSRF for Postman testing
//             .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/api/contact/**").hasRole("USER")
//                 .anyRequest().permitAll()
//             )
//             .httpBasic(Customizer.withDefaults());

//         return http.build();
//     }
// }

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {

//     private final UserService userService;

//     public SecurityConfig(UserService userService) {
//         this.userService = userService;
//     }
    
//     // Expose your custom UserDetailsService
//     @Bean
//     public UserDetailsService userDetailsService() {
//         return (UserDetailsService) userService;
//     }

//     // Define a PasswordEncoder (here for testing, using plain text; do not use in production)
//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder(); // Use BCrypt for password encoding
//     }

//        // Register the DaoAuthenticationProvider that uses your UserDetailsService and PasswordEncoder
//         @Bean
//         public DaoAuthenticationProvider authenticationProvider() {
//              DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//              authProvider.setUserDetailsService((UserDetailsService) userService);
//              authProvider.setPasswordEncoder(passwordEncoder());
//              return authProvider;
//         }

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




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;

@Configuration
public class SecurityConfig {

    // user create and login using java code with in memory service

    // @Bean
    // public UserDetailsService userDetailsService() {

    // UserDetails user1 = User
    // .withDefaultPasswordEncoder()
    // .username("admin123")
    // .password("admin123")
    // .roles("ADMIN", "USER")
    // .build();

    // UserDetails user2 = User
    // .withDefaultPasswordEncoder()
    // .username("user123")
    // .password("password")
    // .roles("USER")
    // .build();

    // var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1,
    // user2);
    // return inMemoryUserDetailsManager;

    // }

@Autowired
    private UserDetailsServiceImpl userDetailService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        // user detail service ka object:
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        // password encoder ka object
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Use BCrypt for password encoding
    }
}
