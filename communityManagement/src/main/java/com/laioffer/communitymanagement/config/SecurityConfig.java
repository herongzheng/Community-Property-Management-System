package com.laioffer.communitymanagement.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth.antMatchers("/**").permitAll())
//                .antMatchers(HttpMethod.GET, "/issues/*").permitAll()
//                .antMatchers(HttpMethod.POST, "/issues/*").hasAuthority("ROLE_GUEST")
//                .anyRequest().authenticated()
//                .and()
                .csrf()
                .disable();
    }
}
