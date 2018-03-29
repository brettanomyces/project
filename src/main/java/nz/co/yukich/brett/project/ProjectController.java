package nz.co.yukich.brett.project;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {

    @RequestMapping(method = RequestMethod.GET, value = "/greet")
    public String greet()
    {
        return "Hello World";
    }
}
