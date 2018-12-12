package project.application.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import project.domain.user.User;
import project.domain.user.UserRepository;
import project.infrastructure.user.InMemoryUserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreateTest {

  private UserRepository userRepository;
  private Create create;

  @BeforeEach
  void setup() {
    userRepository = new InMemoryUserRepository();
    create = new Create(
        new BCryptPasswordEncoder(),
        userRepository
    );
  }

  @Test
  void itShouldCreateUser() {
    create.execute(new Create.CreateRequest(
        "username",
        "password",
        "email@example.com"
    ));

    User user = userRepository.findByUsername("username").orElseThrow(RuntimeException::new);

    assertEquals("username", user.getUsername());
    assertNotEquals("password", user.getPassword());
    assertEquals("email@example.com", user.getEmail());
  }

  @Test
  void isShouldNotAllowMultipleUsersWithSameUsername() {
    create.execute(new Create.CreateRequest(
        "username",
        "password",
        "email@example.com"
    ));

    assertThrows(RuntimeException.class, () -> {
      create.execute(new Create.CreateRequest(
          "username",
          "password",
          "email@example.com"
      ));
    });
  }
}
