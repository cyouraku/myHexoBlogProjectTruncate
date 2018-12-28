package org.seckill.dao;

import java.sql.Timestamp;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.seckill.entity.SuccessKilled;

/**
 * Created by Administrator on 2016/7/25.
 */
public interface SuccessKillMapper {

    /**
     * 插入购买明细，可过滤重复
     * @param seckillId
     * @param userPhone
     * @param killTime
     * @return
     */
	@Insert(value = { "insert into success_killed values(${seckillId},${userPhone},1,'${killTime}')"})
    int insertSuccessKilled(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone,@Param("killTime") Timestamp killTime);

	/**
	 * Update seckill_date if even is closed.
	 * @param seckillId
	 * @param userPhone
	 * @param seckillState
	 * @return
	 */
	@Update(value = {"update success_killed set seckill_state = ${seckillState} where seckill_id = ${seckillId} and user_phone = ${userPhone}"})
	int updateSuccessKilled(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone,@Param("seckillState") int seckillState);

    /**
     * 根据id查询sucesskilld并携带秒杀产品对象实体
     * @param seckillId
     * @return
     */
    @Select("SELECT sk.seckill_id, sk.user_phone, sk.create_time, sk.seckill_state, sk.create_time "
    		+ "from success_killed sk INNER JOIN seckill s on sk.seckill_id =s.seckill_id where sk.seckill_id=${seckillId} and sk.user_phone=${userPhone}")
    @ResultType(SuccessKilled.class)
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);
}
