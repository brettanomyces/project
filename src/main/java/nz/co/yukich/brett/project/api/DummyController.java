package nz.co.yukich.brett.project.api;

import nz.co.yukich.brett.project.model.BaseResponse;
import org.springframework.http.HttpStatus;
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
  public BaseResponse dummy(Authentication authentication)
  {
    User user = (User) authentication.getPrincipal();
    return new BaseResponse(
        HttpStatus.OK.value(),
        HttpStatus.OK.getReasonPhrase(),
        new Dummy(user.getUsername())
    );

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
