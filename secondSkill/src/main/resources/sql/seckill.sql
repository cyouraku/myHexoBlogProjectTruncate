

--定义存储过程,postgreSql version 2018/7/20
--row_count():返回上一条修改类型sql的影响行数
--row_count: 0未修改数据 >0 表示修改的行数：<0 sql错误/未执行修改sql

--Drop stored procedure
drop FUNCTION public.execute_seckill(in v_seckill_id bigint,
                    in v_phone bigint,
                    in v_kill_time TIMESTAMP(0) ,out r_result int);

--Create stored procedure
create or replace FUNCTION public.execute_seckill(in v_seckill_id bigint,
                    in v_phone bigint,
                    in v_kill_time TIMESTAMP(0) ,out r_result int) returns integer
    AS $$
      DECLARE  insert_count int DEFAULT 0;
      DECLARE  update_count int DEFAULT 0;
      DECLARE  skill_state int DEFAULT 1;
      DECLARE  phone_found bigint DEFAULT 0;
    BEGIN
      /* check douplicated phone*/
      SELECT * FROM success_killed
      where seckill_id = v_seckill_id
        and user_phone = v_phone into phone_found;
      IF (phone_found>0) THEN
        /* ROLLBACK ; */
        r_result:=-1;
      ELSE
        /* main process start */
        WITH rows AS (
      		insert into success_killed values(v_seckill_id,v_phone,skill_state,v_kill_time)
      		RETURNING 1
      	)
      	SELECT count(*) FROM rows into insert_count;
      	IF (insert_count=0)THEN
        	/* ROLLBACK ; */
        	r_result:=-1;
      	ELSEIF (insert_count<0) THEN
        	/* ROLLBACK ; */
        	r_result :=-2;
      	ELSE
        	WITH rows AS (
        	update seckill set seckill_number =seckill_number -1
        	where seckill_id = v_seckill_id
              and end_time> v_kill_time
              and start_time < v_kill_time
              and seckill_number >0
              RETURNING 1
      		)
      		SELECT count(*) FROM rows into update_count;
        	IF (update_count<=0) THEN
        		/* update seckill_state as 0*/
        		update success_killed set seckill_sate = 0
        		where seckill_id = v_seckill_id
        		  and user_phone = v_phone;
          		/* ROLLBACK ; */
          		r_result:=0;
        	ELSEIF (update_count<0) THEN
          		/* ROLLBACK ; */
          		r_result:=-1;
        	ELSE
          		/* commit; */
          		r_result:=1;
        	END IF;
      	END IF;
       /* main process end */
      END IF;
   END;
$$ language plpgsql;

--定义存储过程结束

delimiter;
@r_result:=-3;
--执行存储过程
call execute_seckill(1003,1585691594,now(),@r_result);
--获取结果
select @r_result;

-- 1、存储过程优化：事务行级锁持有的时间
-- 2 、不要过度依赖存储过程
-- 3、简单的逻辑可以使用存储过程
-- 4、qps：一个秒杀单6000/qps











