package project.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import project.application.user.Create;
import project.domain.user.User;
import project.domain.user.UserRepository;
import project.infrastructure.user.InMemoryUserRepository;

import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {UserControllerTest.Config.class})
@ExtendWith(SpringExtension.class)
@WebMvcTest
class UserControllerTest {

  private ObjectMapper objectMapper = new ObjectMapper();

  @Autowired
  private MockMvc mvc;

  @Autowired
  private UserRepository userRepository;

  @TestConfiguration
  static class Config {
    @Bean
    public UserRepository userRepository() {
      return new InMemoryUserRepository();
    }

    @Bean
    public Create create(UserRepository userRepository) {
      return new Create(new BCryptPasswordEncoder(), userRepository);
    }

    @Bean
    public UserController userController(Create create, UserRepository userRepository) {
      return new UserController(userRepository, create);
    }
  }

  @Test
  void itShouldCreateUser() throws Exception {
    Create.CreateRequest request = new Create.CreateRequest(
        "username",
        "password",
        "email@exapmle.com"
    );

    mvc.perform(
        post("/auth/register")
            .characterEncoding("UTF-8")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
    ).andExpect(
        status().isOk()
    );
  }

  @Test
  void itShouldReturnCurrentUser() throws Exception {
    User user = User.builder()
        .username("username")
        .password("password")
        .email("email@example.com")
        .build();

    userRepository.create(user);
    login(user);

    mvc.perform(get("/api/user/current").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username", equalTo("username")))
        .andExpect(jsonPath("$.email", equalTo("email@example.com")))
        .andExpect(jsonPath("$.password").doesNotExist());
  }

  private void login(User user) {
    org.springframework.security.core.userdetails.User principal =
        new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.emptySet());
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, null);
    SecurityContextHolder.getContext().setAuthentication(token);
  }
}
