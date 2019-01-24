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

    @RequestMapping(value = "/animal_card_ui",method = RequestMethod.GET)
    public String getAnimalCardUI(){
        return "animal_card_ui";
    }

    @RequestMapping(value = "/multi_card_ui",method = RequestMethod.GET)
    public String getMultiCardUI(){
        return "multi_card_ui";
    }

}