package cn.zifangsky.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/video")
public class VideoController {
    @RequestMapping(value = "/video",method = RequestMethod.GET)
    public String getVideo(){
        return "video";
    }

    @RequestMapping(value = "/videoList",method = RequestMethod.GET)
    public String getVideoList(){
        return "videoList";
    }

    @RequestMapping(value = "/player",method = RequestMethod.GET)
    public String getPlayer(){
        return "player";
    }

    @RequestMapping(value = "/secList",method = RequestMethod.GET)
    public String getSecList(){
        return "secList";
    }

}