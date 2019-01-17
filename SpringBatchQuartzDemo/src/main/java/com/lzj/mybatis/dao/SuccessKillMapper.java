package com.lzj.mybatis.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.lzj.springbatch.model.SuccessKilled;

/**
 * Created by Administrator on 2016/7/25.
 */
public interface SuccessKillMapper {

//    insert ignore into success_killed(seckill_id,user_phone,state)
//    VALUES (${seckillId},${userPhone},0)
    /**
     * 插入购买明细，可过滤重复
     * @param seckillId
     * @param userPhone
     * @return
     */
	@Insert(value = { "${seckillId}","${userPhone}","0"})
    int insertSuccessKilled(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);


//    SELECT
//    sk.seckill_id,
//    sk.user_phone,
//    sk.create_time,
//    sk.seckill_state,
//    s.seckill_id "seckill.seckill_id",
//    s.seckill_name "seckill.name",
//    s.seckill_number "seckill.number",
//    s.start_time "seckill.start_time",
//    s.end_time "seckill.end_time",
//    s.create_time "seckill.create_time"
//  from success_killed sk INNER JOIN seckill s on sk.seckill_id =s.seckill_id
//  where sk.seckill_id=1
    /**
     * 根据id查询sucesskilld并携带秒杀产品对象实体
     * @param seckillId
     * @return
     */
    @Select("SELECT sk.seckill_id, sk.user_phone, sk.create_time, sk.seckill_,s.seckill_id, "
    		+ "s.seckill_name, s.seckill_number,s.start_time, "
            + "s.end_time, s.create_time "
    		+ "from success_killed sk INNER JOIN seckill s on sk.seckill_id =s.seckill_id where sk.seckill_id=${seckillId}")
    @Results({
        @Result(property = "seckilled",  column = "seckill_id"),
        @Result(property = "userPhone", column = "user_phone"),
        @Result(property = "createTime", column = "create_time"),
        @Result(property = "state", column = "seckill_state"),
        @Result(property = "seckillId",  column = "seckill_id"),
        @Result(property = "name",  column = "seckill_name"),
        @Result(property = "number",  column = "seckill_number"),
        @Result(property = "startTime", column = "start_time"),
        @Result(property = "endTime", column = "end_time"),
        @Result(property = "createTime", column = "create_time")
    })
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);
}
