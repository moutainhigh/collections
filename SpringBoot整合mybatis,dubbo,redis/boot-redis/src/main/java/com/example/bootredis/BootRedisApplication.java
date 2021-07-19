package com.example.bootredis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;

@SpringBootApplication
public class BootRedisApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext
                context = SpringApplication.run(BootRedisApplication.class, args);
        Map<String, RedisTemplate> beansOfType = context.getBeansOfType(RedisTemplate.class);
        System.out.println(beansOfType);
        System.out.println("-----------------");
        RedisTemplate redisTemplate = context.getBean("stringRedisTemplate", RedisTemplate.class);
        redisTemplate.opsForValue().set("test", "test-value");
        Order order = new Order();
        order.setOrderId(100001);
        order.setOrderName("商品订单");
        // redisTemplate.opsForValue().set("order", JSON.toJSONString(order));
        // 通过配置jackson的序列化器操作的
        /*RedisTemplate cRedisTemplate = context.getBean("redisTemplate", RedisTemplate.class);
        //cRedisTemplate.opsForValue().set("order-1", order);
        System.out.println(cRedisTemplate.opsForValue().get("order-1"));*/
        RedisTemplate cRedisTemplate = context.getBean("redisTemplate", RedisTemplate.class);
        cRedisTemplate.opsForValue().set("order-1", order);

        context.close();
    }
}
