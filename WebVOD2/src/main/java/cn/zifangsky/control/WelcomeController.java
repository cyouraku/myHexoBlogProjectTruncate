package cn.zifangsky.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/welcome")
public class WelcomeController {

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String getHello(){
        return "Hello";
    }
}