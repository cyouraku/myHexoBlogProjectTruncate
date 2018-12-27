package org.seckill.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;
import org.seckill.entity.Seckill;

/**
 * Created by Administrator on 2016/7/25.
 */
public interface SeckillMapper {

    /**
     * 减库存
     * @param seckillId
     * @param killTime
     * @return
     */
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

    /**
     * 根据id查询秒杀对象
     * @param seckillId
     * @return
     */
    @Select("select seckill_id ,seckill_name,seckill_number,start_time,end_time,create_time from seckill where seckill_id=${seckillId}")
    @Results({
        @Result(property = "seckillId",  column = "seckill_id"),
        @Result(property = "name", column = "seckill_name"),
        @Result(property = "number", column = "seckill_number"),
        @Result(property = "startTime", column = "start_time"),
        @Result(property = "endTime", column = "end_time"),
        @Result(property = "createTime", column = "create_time")
    })
    Seckill queryById(@Param("seckillId")long seckillId);

    /**
     * 根据偏移量查询秒杀商品列表
     * @param offer
     * @param limit
     * @return
     */
    @Select("select seckill_id ,seckill_name,seckill_number,start_time,end_time,create_time from seckill ORDER by create_time DESC limit ${limit}")
    @Results({
        @Result(property = "seckillId",  column = "seckill_id"),
        @Result(property = "name", column = "seckill_name"),
        @Result(property = "number", column = "seckill_number"),
        @Result(property = "startTime", column = "start_time"),
        @Result(property = "endTime", column = "end_time"),
        @Result(property = "createTime", column = "create_time")
    })
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 存储过程执行秒杀
     * @param paramMap
     */
    @Select(value = "{call execute_seckill(#{seckillId,jdbcType=BIGINT,mode=IN},#{phone,jdbcType=BIGINT,mode=IN}, #{killTime,jdbcType=TIMESTAMP,mode=IN},#{result,jdbcType=INTEGER,mode=OUT})}")
    @Options(statementType = StatementType.CALLABLE)
    void killByProcedure(Map<String,Object> paramMap);


}
