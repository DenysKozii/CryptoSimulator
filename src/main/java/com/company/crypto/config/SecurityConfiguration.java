package com.company.crypto.config;

import com.company.crypto.exception.ApplicationExceptionHandler;
import com.company.crypto.jwt.JwtFilter;
import com.company.crypto.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration {
    @Configuration
    @RequiredArgsConstructor
    public static class FormLoginAuthentication extends WebSecurityConfigurerAdapter {
        private final ApplicationExceptionHandler exceptionHandler;
        private final AuthorizationService userService;
        @Autowired
        private JwtFilter jwtFilter;

        private static final String[] SWAGGER_WHITELIST = {
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui.html"
        };

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        @Override
        protected UserDetailsService userDetailsService() {
            return new CustomUserDetailsService(userService);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .cors()
                    .and()
                    .httpBasic().disable()
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
                    .antMatchers("/", "/login", "/registration**","/googleLogin", "/api/v1/login").permitAll()
                    .antMatchers(SWAGGER_WHITELIST).permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .logout()
                    .permitAll()
                    .and()
                    .exceptionHandling()
                    .accessDeniedHandler((request, response, accessDeniedException) -> response.sendRedirect("/access-denied"))
                    .and()
                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
            http.headers().frameOptions().disable();
        }
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Order(1)
    @Configuration
    public static class HttpBasicAuthentication extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.requestMatchers()
                    .antMatchers(HttpMethod.GET,
                            "/api-docs/**",
                            "/v3/api-docs/**",
                            "/swagger-resources/**",
                            "/swagger-ui.html",
                            "/v2/api-docs",
                            "/js/**",
                            "/css/**",
                            "/webjars/springfox-swagger-ui/**"
                    )
                    .and().httpBasic()
                    .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and().csrf().disable();
        }
    }
}

