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
import project.authentication.*;
import project.domain.user.UserRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Bean
  public UserDetailsService userDetailsService(UserRepository userRepository) {
    return new UserDetailsServiceImpl(userRepository);
  }

  private AbstractAuthenticationProcessingFilter authenticationFilter() throws Exception {
    AuthenticationProcessingFilterImpl filter = new AuthenticationProcessingFilterImpl();
    filter.setFilterProcessesUrl("/auth/login");
    filter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandlerImpl());
    filter.setAuthenticationFailureHandler(new AuthenticationFailureHandlerImpl());
    filter.setAuthenticationManager(authenticationManagerBean());
    return filter;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http
      .csrf().disable()
      .exceptionHandling()
      .authenticationEntryPoint(new AuthenticationEntryPointImpl())
      .and().authorizeRequests()
      .antMatchers(HttpMethod.POST, "/auth/*").permitAll()
      .antMatchers(HttpMethod.GET, "/", "/js/*.js", "/css/*.css", "/favicon.ico").permitAll()
      .anyRequest().authenticated()
      .and().addFilter(authenticationFilter())
      .logout().logoutUrl("/auth/logout").logoutSuccessHandler(new LogoutSuccessHandlerImpl())
      .and().requiresChannel().anyRequest().requiresSecure();
  }
}
