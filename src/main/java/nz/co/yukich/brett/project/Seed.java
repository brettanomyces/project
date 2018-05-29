package nz.co.yukich.brett.project;

import nz.co.yukich.brett.project.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Seed implements ApplicationRunner {

  private final Log logger = LogFactory.getLog(Seed.class);

  private Environment env;

  private UserService userService;

  public Seed(
      @Value("${application.env}") Environment env,
      @Autowired UserService userService
  ) {
    this.env = env;
    this.userService = userService;
  }

  @Override
  public void run(ApplicationArguments args) {
    if (Environment.DEV.equals(this.env)) {
      userService.createUser("dummy", "password");
      logger.info("Created user 'dummy' with password 'password'");
    }
  }
}
