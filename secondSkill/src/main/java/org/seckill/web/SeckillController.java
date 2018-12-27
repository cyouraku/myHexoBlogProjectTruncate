package org.seckill.web;

import java.util.Date;
import java.util.List;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.dto.SeckillResult;
import org.seckill.entity.Seckill;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Administrator on 2016/8/6.
 */
@Controller
public class SeckillController {

    private final Logger logger = LoggerFactory.getLogger(SeckillController.class);

    @Autowired
    private SeckillService seckillService;

    @RequestMapping("/")
    public String home(Model model){
    	logger.info("user.dir = " + System.getProperty("user.dir"));
        model.addAttribute("pageTitle","Welcome to my Awesome Dynamic Application");
        return "index";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String getIndex(Model model){
    	logger.info("This is getIndex.");
    	model.addAttribute("pageTitle","Welcome to my Awesome Dynamic Application");
        return "index";
    }

    @RequestMapping(value="/seckill/list",method = RequestMethod.GET)
    public String list(Model model){
        List<Seckill> list = seckillService.getSeckillList();
        model.addAttribute("list",list);
        return "list";
    }


    @RequestMapping(value = "/seckill/{seckillId}/detail",method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId,Model model){
    	logger.info("request /detail, seckillId = " + seckillId);
        if (seckillId == null){
            return "redirect:/seckill/list";
        }
        Seckill seckill= seckillService.getById(seckillId);
        if (seckill == null){
            return "forward:/seckill/list";
        }
        model.addAttribute("seckill",seckill);
        return "detail";
    }

    @RequestMapping(value = "/seckill/{seckillId}/exposer",method =RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId) {
    	logger.info("request  /seckill/{seckillId}/exposer, seckillId = " + seckillId);
        SeckillResult<Exposer> result = null;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true, exposer);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            result = new SeckillResult<Exposer>(false, ex.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/seckill/{seckillId}/{md5}/execution",method = RequestMethod.POST ,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                                   @PathVariable("md5") String md5,
                                                   @CookieValue(value= "killPhone",required= false) Long phone){
       if (phone == null){
           return new SeckillResult<SeckillExecution>(false,"未注册");
       }

        try{
            SeckillExecution execution = seckillService.excuteSeckillProducer(seckillId,phone,md5);
            return new SeckillResult<SeckillExecution>(true,execution);
        }catch (SeckillCloseException e1){
            return new SeckillResult<SeckillExecution>(true,
                    new SeckillExecution(seckillId, SeckillStatEnum.END));
        }
        catch (RepeatKillException e2){
            return new SeckillResult<SeckillExecution>(true,
                    new SeckillExecution(seckillId, SeckillStatEnum.REPEAT_KILL));
        }
        catch (Exception ex){
            logger.error(ex.getMessage(),ex);
            return new SeckillResult<SeckillExecution>(false,
                    new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR));
        }

    }

    @RequestMapping(value = "/seckill/time/now",method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Long> time(){
        Date now = new Date();
        logger.info("request /time/now, now = " + now.getTime());
        return new SeckillResult<Long>(true,now.getTime());
    }

	@RequestMapping("/fail")
	public String fail() {
		throw new MyException("Oh dear!");
	}

	@ExceptionHandler(MyException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody MyRestResponse handleMyRuntimeException(MyException exception) {
		return new MyRestResponse("Some data I want to send back to the client for 400 reason.");
	}

	@ExceptionHandler(MyException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public @ResponseBody MyRestResponse handleMyRuntimeException404(MyException exception) {
		return new MyRestResponse("Some data I want to send back to the client for 404 reason.");
	}
}
