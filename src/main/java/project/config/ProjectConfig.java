package project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.application.user.Create;
import project.domain.user.User;
import project.domain.user.UserRepository;
import project.infrastructure.user.InMemoryUserRepository;

@Configuration
public class ProjectConfig {

  @Bean
  public UserRepository userRepository() {
    InMemoryUserRepository userRepository = new InMemoryUserRepository();

    userRepository.create(
        User.builder()
            .username("brett")
            .password(new BCryptPasswordEncoder().encode("password"))
            .email("email@example.com")
            .build()
    );

    return userRepository;
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
