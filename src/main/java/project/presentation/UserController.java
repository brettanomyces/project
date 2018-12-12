package project.presentation;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import project.application.user.Create;
import project.domain.user.User;
import project.domain.user.UserRepository;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class UserController {

  private UserRepository userRepository;
  private Create create;

  @RequestMapping(method = RequestMethod.POST, value = "/auth/register")
  public User register(@Valid @RequestBody Create.CreateRequest request) {
    return create.execute(request);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/api/user/current")
  @ResponseBody
  public User retrieveUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    org.springframework.security.core.userdetails.User securityUser = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
    return userRepository.findByUsername(securityUser.getUsername())
      .orElseThrow(RuntimeException::new);
  }
}
