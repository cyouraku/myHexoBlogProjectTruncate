package org.seckill.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.seckill.entity.SuccessKilled;

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


    /**
     * 根据id查询sucesskilld并携带秒杀产品对象实体
     * @param seckillId
     * @return
     */
    @Select("SELECT sk.seckill_id, sk.user_phone, sk.create_time, sk.seckill_state, sk.create_time "
    		+ "from success_killed sk INNER JOIN seckill s on sk.seckill_id =s.seckill_id where sk.seckill_id=${seckillId} and sk.user_phone=${userPhone}")
//    @Results({
//        @Result(property = "seckilled",  column = "seckill_id"),
//        @Result(property = "userPhone", column = "user_phone"),
//        @Result(property = "createTime", column = "create_time"),
//        @Result(property = "state", column = "seckill_state"),
//        @Result(property = "seckillId",  column = "seckill_id"),
//        @Result(property = "name",  column = "seckill_name"),
//        @Result(property = "number",  column = "seckill_number"),
//        @Result(property = "startTime", column = "start_time"),
//        @Result(property = "endTime", column = "end_time"),
//        @Result(property = "createTime", column = "create_time")
//    })
    @ResultType(SuccessKilled.class)
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);
}
