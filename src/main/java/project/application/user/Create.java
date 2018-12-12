package project.application.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.domain.user.User;
import project.domain.user.UserRepository;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
public class Create {

  private PasswordEncoder passwordEncoder;
  private UserRepository userRepository;

  @NoArgsConstructor
  @AllArgsConstructor
  @Data
  public static class CreateRequest {

    @NotNull
    @Size(min = 4, message = "{register.username.size}")
    private String username;

    @NonNull
    @Size(min = 8, message = "{register.username.size}")
    private String password;

    @NonNull
    @Email(message = "{register.email}")
    private String email;
  }

  public User execute(CreateRequest request) {
    userRepository.findByUsername(request.username).ifPresent(user -> {
      throw new RuntimeException();
    });

    User user = User.builder()
        .username(request.username)
        .password(passwordEncoder.encode(request.password))
        .email(request.email)
        .build();

    userRepository.create(user);

    return user;
  }
}
