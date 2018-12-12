package project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.application.user.Create;
import project.domain.user.UserRepository;
import project.infrastructure.user.InMemoryUserRepository;

@Configuration
public class ProjectConfig {

  @Bean
  public UserRepository userRepository() {
    return new InMemoryUserRepository();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public Create create(PasswordEncoder passwordEncoder, UserRepository userRepository) {
    return new Create(passwordEncoder, userRepository);
  }
}
