package nz.co.yukich.brett.project;

import nz.co.yukich.brett.project.auth.JsonAuthenticationFailureHandler;
import nz.co.yukich.brett.project.auth.JsonAuthenticationProcessingFilter;
import nz.co.yukich.brett.project.auth.JsonAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private static final String APP_ENDPOINT = "/app";

  private static final String API_LOGIN_ENDPOINT = "/api/login";
  private static final String API_LOGOUT_ENDPOINT = "/api/logout";

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Bean
  public AbstractAuthenticationProcessingFilter authenticationFilter() throws Exception {
    JsonAuthenticationProcessingFilter filter = new JsonAuthenticationProcessingFilter();
    filter.setFilterProcessesUrl(API_LOGIN_ENDPOINT);
    filter.setAuthenticationSuccessHandler(new JsonAuthenticationSuccessHandler());
    filter.setAuthenticationFailureHandler(new JsonAuthenticationFailureHandler());
    filter.setAuthenticationManager(authenticationManagerBean());
    return filter;
  }

  @Autowired
  public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.authorizeRequests()
        .antMatchers(HttpMethod.GET, APP_ENDPOINT).permitAll()
        .antMatchers(HttpMethod.POST, API_LOGIN_ENDPOINT).permitAll()
        .antMatchers(HttpMethod.GET, "/js/*.js").permitAll()
        .antMatchers(HttpMethod.GET, "/css/*.css").permitAll()
        .anyRequest().authenticated()
        .and()
        .addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        .logout()
        .logoutUrl(API_LOGOUT_ENDPOINT)
        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
        .and().exceptionHandling().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/app"));
  }
}
