package nz.co.yukich.brett.project.api;

import nz.co.yukich.brett.project.WebSecurityConfig;
import nz.co.yukich.brett.project.model.RegisterRequest;
import nz.co.yukich.brett.project.model.User;
import nz.co.yukich.brett.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@RestController
public class UserController {

  private UserService userService;

  public UserController(@Autowired UserService userService) {
    this.userService = userService;
  }

  @RequestMapping(
      method = RequestMethod.POST,
      value = WebSecurityConfig.API_REGISTER_ENDPOINT
  )
  public User register(@Valid @RequestBody RegisterRequest registration) throws BadRequestException {
    try {
      return userService.createUser(registration.getUsername(), registration.getPassword());
    } catch (ConstraintViolationException ex) {
      throw new BadRequestException("Username is not available");
    }
  }

  @RequestMapping(method = RequestMethod.GET, value = "/api/user/current")
  @ResponseBody
  public User retrieveUser(Authentication authentication) throws NotFoundException {
    org.springframework.security.core.userdetails.User securityUser = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
    User user = userService.retrieveUser(securityUser.getUsername());

    if (user == null) {
      throw new NotFoundException();
    }

    return user;
  }
}
