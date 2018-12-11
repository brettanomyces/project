package project.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import project.authentication.*;
import project.domain.user.UserRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  private ObjectMapper objectMapper = new ObjectMapper();

  @Bean
  public UserDetailsService userDetailsService(UserRepository userRepository) {
    return new UserDetailsServiceImpl(userRepository);
  }

  private AbstractAuthenticationProcessingFilter authenticationFilter() throws Exception {
    AuthenticationProcessingFilterImpl filter = new AuthenticationProcessingFilterImpl(objectMapper);
    filter.setFilterProcessesUrl("/auth/login");
    filter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandlerImpl(objectMapper));
    filter.setAuthenticationFailureHandler(new AuthenticationFailureHandlerImpl(objectMapper));
    filter.setAuthenticationManager(authenticationManagerBean());
    return filter;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http
      .csrf().disable()
      .exceptionHandling()
      .authenticationEntryPoint(new AuthenticationEntryPointImpl(objectMapper))
      .and().authorizeRequests()
      .antMatchers(HttpMethod.POST, "/auth/*").permitAll()
      .antMatchers(HttpMethod.GET, "/", "/js/*.js", "/css/*.css").permitAll()
      .anyRequest().authenticated()
      .and().addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
      .logout().logoutUrl("/auth/logout").logoutSuccessHandler(new LogoutSuccessHandlerImpl(objectMapper))
      .and().requiresChannel().anyRequest().requiresSecure();
  }
}
