package com.vinci.livraison.app.config;

import com.vinci.livraison.app.security.jwt.filter.JwtAuthenticationFilter;
import com.vinci.livraison.app.security.providers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    AdminAuthProvider adminAuthProvider;
    ClientAuthProvider clientAuthProvider;
    RestaurateurAuthProvider restaurateurAuthProvider;
    LivreurAuthProvider livreurAuthProvider;
    EnseigneAuthProvider enseigneAuthProvider;
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public void setEnseigneAuthProvider(EnseigneAuthProvider enseigneAuthProvider) {
        this.enseigneAuthProvider = enseigneAuthProvider;
    }

    @Autowired
    public void setAdminAuthProvider(AdminAuthProvider adminAuthProvider) {
        this.adminAuthProvider = adminAuthProvider;
    }
    @Autowired
    public void setClientAuthProvider(ClientAuthProvider clientAuthProvider) {
        this.clientAuthProvider = clientAuthProvider;
    }

    @Autowired
    public void setRestaurateurAuthProvider(RestaurateurAuthProvider restaurateurAuthProvider) {
        this.restaurateurAuthProvider = restaurateurAuthProvider;
    }

    @Autowired
    public void setLivreurAuthProvider(LivreurAuthProvider livreurAuthProvider) {
        this.livreurAuthProvider = livreurAuthProvider;
    }

    @Autowired
    public void setJwtAuthenticationFilter(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // disable csrf
        http.csrf(AbstractHttpConfigurer::disable);

        http.exceptionHandling().authenticationEntryPoint((request, response, e) -> response.sendError(401,e.getMessage())).accessDeniedHandler((request, response, e) -> response.sendError(403, e.getMessage()));

        //
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeRequests(authorize ->
                authorize
                        .antMatchers("/api/test/**").permitAll()
                        .antMatchers("/api/*/login-exists", "/api/auth/tokens/**").permitAll()
                        .anyRequest().authenticated()

        );

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {

        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }


    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(adminAuthProvider)
                .authenticationProvider(clientAuthProvider)
                .authenticationProvider(restaurateurAuthProvider)
                .authenticationProvider(livreurAuthProvider);
    }
}
