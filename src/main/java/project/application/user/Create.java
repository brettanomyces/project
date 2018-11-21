package project.application.user;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;
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

  @Value
  public class CreateRequest {

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
    User user = new User(
      request.getUsername(),
      passwordEncoder.encode(request.getPassword()),
      request.getEmail()
    );

    userRepository.create(user);

    return user;
  }
}
