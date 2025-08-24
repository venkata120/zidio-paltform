package com.zidio.SecurityConf;

import com.zidio.Security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth

                // === Public Static Resources ===
                .requestMatchers("/css/**", "/js/**", "/images/**", "/fonts/**", "/favicon.ico").permitAll()

                .requestMatchers(
                        "/api/auth/**",
                        "/api/auth/digilocker/**",
                        "/api/digilocker/**",  
                        "/api/auth/digilocker/start",
                        "/api/auth/digilocker/callback",
                        "/api/job/all",
                        "/api/job/{jobId}"
                    ).permitAll()

                    // === Public View Pages ===
                    .requestMatchers(
                        "/ui/**",
                        "/Dashboard",                     
                        "/student/profile/form",
                        "/student/job/view/**",
                        "/student/job/view",       
                        "/student/profile/edit",
                        "/student/jobs", 
                        "/admin/**",
                        "/student/courses",
                        "/student/courses/**",
                        "/api/notifications/**",
                        "/api/file/files/**",
                        "/notifications",
                        "/api/notifications/latest",
                        "/api/notifications/**",
                        "/recruiter/profile/form",
                        "/recruiter/job/post",
                        "/recruiter/my-jobs",
                        "/recruiter/job/edit/**",
                        "/jobRecommendations",
                        "/student/resume/upload",         
                        "/Login.jsp", "/Register.jsp"     // for direct JSP calls if needed
                    ).permitAll()

                // === Student-Only Access ===
                    .requestMatchers("/student/applications").permitAll()
                    .requestMatchers("/student/my-applications").permitAll()    
                .requestMatchers("/api/student/**").hasAuthority("ROLE_STUDENT")
                .requestMatchers("/student/**").hasAuthority("ROLE_STUDENT")
                .requestMatchers("/api/file/**").hasAuthority("ROLE_STUDENT")
                .requestMatchers("/api/courses/**").hasAuthority("ROLE_STUDENT")
                .requestMatchers("/api/job/apply/**").hasAuthority("ROLE_STUDENT")
                .requestMatchers("/api/job/recommend").hasAuthority("ROLE_STUDENT")

                // === Recruiter-Only Access ===
                .requestMatchers("/api/recruiter/**").hasAuthority("ROLE_RECRUITER")
                .requestMatchers("/api/job/post", "/api/job/my-posts").hasAuthority("ROLE_RECRUITER")
                .requestMatchers("/recruiter/**").hasAuthority("ROLE_RECRUITER")

                // === Admin Access ===
                .requestMatchers("/api/auth/users/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")

                // === General Authenticated API ===
                .requestMatchers("/api/notifications/latest").permitAll()
                .requestMatchers("/api/notifications/send").authenticated()
                .requestMatchers("/api/notifications/**").hasAnyAuthority("ROLE_STUDENT", "ROLE_ADMIN", "ROLE_RECRUITER")


                // === Catch-All Rule ===
                .anyRequest().authenticated()
            );

        // Add JWT authentication filter
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
            "/webjars/**",
            "/resources/**",
            "/static/**",
            "/templates/**",
            "/public/**",
            "/error",
            "/WEB-INF/**"
        );
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

