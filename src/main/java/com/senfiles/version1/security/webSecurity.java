package com.senfiles.version1.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;




@EnableWebSecurity
@Configuration
public class webSecurity {

    @Autowired
    private UserDetailsService userDetailsService;
   
    @Autowired
    private CustomLoginSuccessHandler sucessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable().authorizeHttpRequests((requests) -> requests
                .requestMatchers("/registration/**").permitAll()
                .requestMatchers("/delete/**").hasAnyAuthority("ADMIN")
                .requestMatchers("/blocked/**").permitAll()
                .requestMatchers("/user/**").hasAnyAuthority("USER")
                .requestMatchers("/admin/**").hasAnyAuthority("ADMIN")
                .requestMatchers("/logs/**").hasAnyAuthority("ADMIN")
                .requestMatchers("/login/**").permitAll()
                .anyRequest().authenticated())
                .formLogin((form) -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .failureHandler(authenticationFailureHandler())
                        // .defaultSuccessUrl("/admin")
                        .successHandler(sucessHandler)
                        .permitAll())
                .logout((logout) -> logout.permitAll())

                .exceptionHandling(handling -> handling.accessDeniedPage("/access-denied"));
        http.authenticationProvider(authenticationProvider());
        http.headers().frameOptions().sameOrigin();
        return http.build();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

}

