package nz.co.yukich.brett.project;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TemplateController {

    @RequestMapping(method = RequestMethod.GET, value = "/login")
    public String login(Model model) {
        return "login";
    }
}