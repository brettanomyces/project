package nz.co.yukich.brett.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import nz.co.yukich.brett.project.auth.*;
import nz.co.yukich.brett.project.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private static final String API_LOGIN_ENDPOINT = "/auth/login";
  private static final String API_LOGOUT_ENDPOINT = "/auth/logout";

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return new UserDetailsServiceImpl();
  }

  @Bean
  public AuthenticationSuccessHandler successHandler() {
    return new JsonAuthenticationSuccessHandler(objectMapper());
  }

  @Bean
  public AuthenticationFailureHandler failureHandler() {
    return new JsonAuthenticationFailureHandler(objectMapper());
  }

  @Bean
  JsonAuthenticationEntryPoint authenticationEntryPoint() {
    return new JsonAuthenticationEntryPoint(objectMapper());
  }

  @Bean
  public LogoutSuccessHandler logoutSuccessHandler() {
    return new JsonLogoutSuccessHandler(objectMapper());
  }

  @Bean
  public AbstractAuthenticationProcessingFilter authenticationFilter() throws Exception {
    JsonAuthenticationProcessingFilter filter = new JsonAuthenticationProcessingFilter(objectMapper());
    filter.setFilterProcessesUrl(API_LOGIN_ENDPOINT);
    filter.setAuthenticationSuccessHandler(successHandler());
    filter.setAuthenticationFailureHandler(failureHandler());
    filter.setAuthenticationManager(authenticationManagerBean());
    return filter;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http
        .csrf().disable()
        .exceptionHandling()
        .authenticationEntryPoint(authenticationEntryPoint())
        .and().authorizeRequests()
        .antMatchers(HttpMethod.POST, "/auth/*").permitAll()
        .antMatchers("/", "/js/*.js", "/css/*.css").permitAll()
        .antMatchers("/api/**").authenticated()
        .anyRequest().authenticated()
        .and().addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        .logout().logoutUrl(API_LOGOUT_ENDPOINT).logoutSuccessHandler(logoutSuccessHandler());

  }
}
