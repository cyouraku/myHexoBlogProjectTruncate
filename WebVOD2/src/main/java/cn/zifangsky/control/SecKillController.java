package cn.zifangsky.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/seckill")
public class SecKillController {

    @RequestMapping(value = "/secList",method = RequestMethod.GET)
    public String getSecList(){
        return "secList";
    }
}