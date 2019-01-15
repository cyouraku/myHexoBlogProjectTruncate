package org.seckill.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.seckill.dao.SeckillMapper;
import org.seckill.dao.SuccessKillMapper;
import org.seckill.dao.cache.RedisDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

/**
 * Created by Administrator on 2016/8/1.
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private SeckillMapper seckillMapper;

    @Resource
    private SuccessKillMapper successKillMapper;

    @Autowired
    private RedisDao redisDao;

    private final String slat="345533dddeet";


    public List<Seckill> getSeckillList() {
    	List<Seckill> resultList = new ArrayList<Seckill>();
    	resultList = seckillMapper.queryAll(0,4);
    	logger.info("resultList.size = " + resultList.size());
        return resultList;
    }

    public Seckill getById(long seckillId) {
        return seckillMapper.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {

        //优化点：缓存优化
        Seckill seckill = redisDao.getSeckill(seckillId);
        if (seckill == null){
            seckill=seckillMapper.queryById(seckillId);
            if (seckill == null){
                return new Exposer(seckillId,false);
            }else {
                redisDao.putSeckill(seckill);
            }
        }
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		Date nowTime = new Date();
		if (nowTime.getTime() < startTime.getTime()
				|| nowTime.getTime() > endTime.getTime()) {
			return new Exposer(seckillId, false, nowTime.getTime(),
					startTime.getTime(), endTime.getTime());
		}
		// 转化特定字符串的过程
		String md5 = getMd5(seckillId);
		return new Exposer(true, md5, seckillId);
    }

    @Transactional
    /**
     * 使用注解控制事务的优点：
     * 1、开发团队达成一致约定，明确标注事务方法的编码风格
     * 2、保证事务方法的执行时间尽可能短，不要穿插其他网络操作，rpc/http请求或者剥离到事务方法外部
     * 3、不是所有方法都需要事务，
     */
    public SeckillExecution excuteSeckill(long seckillId, long userPhone, String md5) {

    	int resultCode = 0;

        if (md5==null || !md5.equals(getMd5(seckillId))){
        	resultCode = SeckillStatEnum.DATA_REWRITE.getState();
            throw new SeckillException("seckill data rewrite");
        }
        try {
        	Timestamp killTime = new Timestamp(new Date().getTime());
            //记录购买行为
            int insertCount = successKillMapper.insertSuccessKilled(seckillId, userPhone,killTime );
            //唯一
            if (insertCount <= 0) {
                //重复秒杀
            	resultCode = SeckillStatEnum.REPEAT_KILL.getState();
                throw new RepeatKillException("seckill repeated");
            } else {
                //执行秒杀逻辑：减库存+记录购买行为

                //减库存，热点商品竞争
                int updateCount = seckillMapper.reduceNumber(seckillId, killTime);
                if (updateCount <= 0) {
                	resultCode = SeckillStatEnum.END.getState();
                	//update seckill_date = 0
                	successKillMapper.updateSuccessKilled(seckillId, userPhone, resultCode);
                    //没有更新记录,秒杀结束
                    throw new SeckillCloseException("seckill is close");
                } else {
                    //秒杀成功
                    SuccessKilled successKilled = successKillMapper.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
                }
            }

        }catch (SeckillCloseException e1){
//            throw e1;
        	logger.error(e1.getMessage(),e1);
            return new SeckillExecution(seckillId,SeckillStatEnum.END);
        }catch (RepeatKillException e2){
//            throw e2;
        	logger.error(e2.getMessage(),e2);
            return new SeckillExecution(seckillId,SeckillStatEnum.REPEAT_KILL);
        } catch (Exception ex){
            logger.error(ex.getMessage(),ex);
//            throw new SeckillException("secjill inner error:"+ex.getMessage());
            if(ex.getMessage().contains("重複キー") || ex.getMessage().contains("repeated")){
            	resultCode = SeckillStatEnum.REPEAT_KILL.getState();
            }else if( ex.getMessage().contains("rewrite")){
            	resultCode = SeckillStatEnum.DATA_REWRITE.getState();
            }else{
            	resultCode = SeckillStatEnum.INNER_ERROR.getState();
            }
            return new SeckillExecution(seckillId,SeckillStatEnum.stateOf(resultCode));
        }

    }

    @Override
    public SeckillExecution excuteSeckillProducer(long seckillId, long userPhone, String md5) {
        if (md5==null || !md5.equals(getMd5(seckillId))){
            return new SeckillExecution(seckillId,SeckillStatEnum.DATA_REWRITE);
        }

        Date killTime = new Date();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("seckillId",seckillId);
        map.put("phone",userPhone);
        map.put("killTime",killTime);
        map.put("result",null);
        //执行存储过程
        try{
            seckillMapper.killByProcedure(map);
            int result =MapUtils.getInteger(map,"result",-2);
            if (result ==1){
                //秒杀成功
                SuccessKilled successKilled = successKillMapper.queryByIdWithSeckill(seckillId, userPhone);
                return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
            }else {
                return new SeckillExecution(seckillId,SeckillStatEnum.stateOf(result));
            }
        }catch (Exception ex){
            logger.error(ex.getMessage(),ex);
            return new SeckillExecution(seckillId,SeckillStatEnum.INNER_ERROR);
        }
    }

    private String getMd5(long seckillId){
        String base = seckillId+"/"+slat;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }
}
