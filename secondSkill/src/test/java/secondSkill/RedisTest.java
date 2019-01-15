package secondSkill;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.seckill.dao.cache.RedisDao;
import org.seckill.dto.Exposer;
import org.seckill.entity.Seckill;
import org.seckill.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import redis.clients.jedis.Jedis;

@RunWith(SpringRunner.class)
@SpringBootTest
@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "org.seckill.dao, org.seckill.service, org.seckill.web")
@MapperScan("org.seckill.dao")
public class RedisTest {

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private RedisDao redisDao;

    @Test
    public void SeckillServiceTests(){
        try {
//            redisTemplate.opsForValue().set("name", "张三");
//            Object object = redisTemplate.opsForValue().get("name");
//            System.out.println(object);
        	Exposer exposer = seckillService.exportSeckillUrl(1);
        	exposer.getMd5();
        	System.out.println(String.format("md5 = %s", exposer.getMd5()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void RedisDaoTests(){
        try {
//        	RedisDao redisDao = new RedisDao();
        	Seckill seckill =  redisDao.getSeckill(1);
        	System.out.println(String.format("seckill name = %s", seckill.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void RedisTests(){
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("127.0.0.1",6379);
        System.out.println("连接成功");
        try{
        	String name = jedis.clientGetname();
        	 System.out.println("client name = " + name);
            //设置 redis 字符串数据
            jedis.set("runoobkey", "www.runoob.com");
            // 获取存储的数据并输出
            System.out.println("redis 存储的字符串为: "+ jedis.get("runoobkey"));
        }finally{
        	jedis.close();
        }
    }

}