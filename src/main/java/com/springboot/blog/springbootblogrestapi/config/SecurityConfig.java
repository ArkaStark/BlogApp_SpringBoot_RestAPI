package com.springboot.blog.springbootblogrestapi.config;

import com.springboot.blog.springbootblogrestapi.security.CustomUsersDetailsService;
import com.springboot.blog.springbootblogrestapi.security.JwtAuthFilter;
import com.springboot.blog.springbootblogrestapi.security.JwtAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {


    @Autowired
    private CustomUsersDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public JwtAuthFilter jwtAuthFilter(){
        return  new JwtAuthFilter();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

//                .authorizeRequests()
//                .antMatchers(HttpMethod.GET,"/api/**").permitAll()
//                .antMatchers("/api/auth/**").permitAll()
//                .anyRequest()
//                .authenticated();

                .authorizeRequests((authorize) -> authorize
                                .antMatchers(HttpMethod.GET,"/api/v1**").permitAll()
                                .antMatchers("/api/v1/auth/**").permitAll()

                );

//                .and()
//                .httpBasic();

//                .authorizeRequests((authorize) -> authorize
//                        .antMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
//                        .antMatchers("/api/v1/auth/**").permitAll()
//                        .antMatchers("/v2/api-docs/**").permitAll()
//                        .antMatchers("/swagger-ui/**").permitAll()
//                        .antMatchers("/swagger-resources/**").permitAll()
//                        .antMatchers("/swagger-ui.html").permitAll()
//                        .antMatchers("/webjars/**").permitAll()
//                );
        http.addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

//    In memory Authentication
//    @Bean
//    public InMemoryUserDetailsManager userDetailsService(){
//        UserDetails arka = User.builder()
//                .username("arka")
//                .password(passwordEncoder().encode("@rk@"))
//                .roles("USER")
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("@dmin"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(arka, admin);
//    }



    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}