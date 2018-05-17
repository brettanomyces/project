package nz.co.yukich.brett.project;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DummyController {

  @RequestMapping("/api/dummy")
  @ResponseBody
  public Dummy dummy(Authentication authentication)
  {
    User user = (User) authentication.getPrincipal();
    return new Dummy(user.getUsername());
  }

  private class Dummy {
    private String greeting = "Hello World";

    public Dummy(){
    }

    public Dummy(String name) {
      this.greeting = "Hello " + name;
    }

    public String getGreeting() {
      return greeting;
    }
  }

}
