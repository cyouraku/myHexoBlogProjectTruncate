
-- 创建秒杀库存表,postgreSql version
CREATE SEQUENCE seckill_seq;

CREATE TABLE seckill(
  seckill_id bigint NOT  NULL  DEFAULT nextval ('seckill_seq') ,
  seckill_name VARCHAR(120) not NULL  ,
  seckill_number int not NULL  ,
  start_time TIMESTAMP(0) NOT NULL ,
  end_time TIMESTAMP(0) NOT NULL  ,
  create_time TIMESTAMP(0)  NULL DEFAULT CURRENT_TIMESTAMP ,
  PRIMARY  KEY (seckill_id)
);

ALTER SEQUENCE seckill_seq Restart with 1000;
CREATE INDEX idx_start_time on seckill(start_time);
CREATE INDEX idx_end_time on seckill(end_time);
CREATE INDEX idx_create_time on seckill(create_time);

-- 初始化数据,postgreSql version
insert into seckill(seckill_name,seckill_number,start_time,end_time)
VALUES
('1000元秒杀iphone6s',1000,'2018-07-20 00:00:00','2018-07-26 00:00:00'),
('500元秒杀ipad2',200,'2018-07-20 00:00:00','2018-07-26 00:00:00'),
('300元秒杀小米4',300,'2018-07-20 00:00:00','2018-07-26 00:00:00'),
('200元秒杀红米note',400 ,'2016-07-20 00:00:00','2018-07-26 00:00:00');

-- UPDATE数据,postgreSql version
update seckill set start_time='2018-08-01 00:00:00',end_time='2018-08-01 23:59:59' where seckill_id = 1;
update seckill set start_time='2018-08-01 00:00:00',end_time='2018-08-01 23:59:59' where seckill_id = 2;
update seckill set start_time='2018-08-01 00:00:00',end_time='2018-08-01 23:59:59' where seckill_id = 3;
update seckill set start_time='2018-08-01 00:00:00',end_time='2018-08-01 23:59:59' where seckill_id = 4;

-- 秒杀成功明细表
-- 用户登录认证相关的信息,postgreSql version
CREATE TABLE success_killed(
  seckill_id bigint  NOT  NULL  DEFAULT nextval ('success_killed_pkey') ,
  user_phone bigint NOT NULL ,
  seckill_state smallint NOT NULL DEFAULT -1 ,
  create_time TIMESTAMP(0) not NULL  ,
  PRIMARY KEY(seckill_id,user_phone)
);

CREATE INDEX idx_create_time ON success_killed(create_time);









