package cn.zifangsky.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/audio")
public class AudioController {
    @RequestMapping(value = "/animal",method = RequestMethod.GET)
    public String getAnimal(){
        return "animal";
    }

}