package nz.co.yukich.brett.project;

import nz.co.yukich.brett.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Seed implements ApplicationRunner {

  @Value("${application.env}")
  private String env;

  private UserService userService;

  public Seed(@Autowired UserService userService) {
    this.userService = userService;
  }

  @Override
  public void run(ApplicationArguments args) {
    if (ProjectApplication.ENVIROMENT_DEV.equals(env)) {
      userService.createUser("dummy", "password");
    }
  }
}
