package nz.co.yukich.brett.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import nz.co.yukich.brett.project.framework.*;
import nz.co.yukich.brett.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

  public static final String API_LOGIN_ENDPOINT = "/auth/login";
  public static final String API_LOGOUT_ENDPOINT = "/auth/logout";
  public static final String API_REGISTER_ENDPOINT = "/auth/register";

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
    return new AuthenticationSuccessHandlerImpl(objectMapper());
  }

  @Bean
  public AuthenticationFailureHandler failureHandler() {
    return new AuthenticationFailureHandlerImpl(objectMapper());
  }

  @Bean
  AuthenticationEntryPointImpl authenticationEntryPoint() {
    return new AuthenticationEntryPointImpl(objectMapper());
  }

  @Bean
  public LogoutSuccessHandler logoutSuccessHandler() {
    return new LogoutSuccessHandlerImpl(objectMapper());
  }

  @Bean
  public AbstractAuthenticationProcessingFilter authenticationFilter() throws Exception {
    AuthenticationProcessingFilterImpl filter = new AuthenticationProcessingFilterImpl(objectMapper());
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
        .anyRequest().authenticated()
        .and().addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        .logout().logoutUrl(API_LOGOUT_ENDPOINT).logoutSuccessHandler(logoutSuccessHandler());

  }
}
