package nz.co.yukich.brett.project;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WebController {

    @RequestMapping(method = RequestMethod.GET, value = "/app")
    public String login() {
        return "index";
    }
}
