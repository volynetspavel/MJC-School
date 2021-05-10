package com.epam.esm.config;

import com.epam.esm.constant.RoleValue;
import com.epam.esm.exception.security.CustomAccessDeniedHandler;
import com.epam.esm.filter.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Defines the configuration for security of application.
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final int BCRYPT_STRENGTH = 12;

    private final JwtTokenFilter jwtTokenFilter;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Autowired
    public SecurityConfig(JwtTokenFilter jwtTokenFilter, CustomAccessDeniedHandler accessDeniedHandler) {
        this.jwtTokenFilter = jwtTokenFilter;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/auth").permitAll()
                .antMatchers(HttpMethod.GET, "/tags", "/certificates").permitAll()
                .antMatchers(HttpMethod.POST, "/purchases")
                .hasAuthority(RoleValue.ROLE_USER)
                .antMatchers(HttpMethod.POST, "/tags", "/certificates")
                .hasAuthority(RoleValue.ROLE_ADMIN)
                .antMatchers(HttpMethod.DELETE, "/tags/**", "/certificates/**")
                .hasAuthority(RoleValue.ROLE_ADMIN)
                .antMatchers(HttpMethod.PUT, "/tags/**", "/certificates/**")
                .hasAuthority(RoleValue.ROLE_ADMIN)
                .antMatchers(HttpMethod.GET, "/users/**", "/tags/user/**", "/purchases/user/**")
                .hasAuthority(RoleValue.ROLE_ADMIN)
                .and()
                .exceptionHandling().authenticationEntryPoint(accessDeniedHandler)
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .addFilterBefore(jwtTokenFilter, BasicAuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCRYPT_STRENGTH);
    }
}
