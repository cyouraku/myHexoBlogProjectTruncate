package cn.zifangsky.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/echarts")
public class EChartsDemoController {

    @RequestMapping(value = "/bar",method = RequestMethod.GET)
    public String getEchartsBarDemo(){
        return "eChartsBarDemo";
    }
}