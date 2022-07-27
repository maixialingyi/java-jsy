package com.red.packet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DependentRedisImplTest {

    @Autowired
    DependentRedisImpl dependentRedisImpl;
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void contextLoads() {
        // 初始化总金额
        redisTemplate.opsForHash().put("redPacket","totalAcount",1000000);
        // 拆包
        dependentRedisImpl.unpack("1","redPacket","totalAcount",100);
    }

    @Test
    public void redisHash(){
        redisTemplate.opsForHash().put("redPacket","totalAcount",1000000);
        Object o = redisTemplate.opsForHash().get("redPacket", "totalAcount");
        System.out.println(o);
    }

    @Test
    public void redisLua(){
        redisTemplate.opsForHash().put("redPacket","totalAcount",100);
        System.out.println(redisTemplate.opsForHash().get("redPacket", "totalAcount"));

        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setResultType(Long.class);//返回类型是Long
        //lua文件存放在resources目录下的redis文件夹内
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("amount.lua")));
        System.out.println(redisTemplate.execute(redisScript, Arrays.asList("101")));

        /*Long result = null;
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setResultType(Long.class);//返回类型是Long
        //lua文件存放在resources目录下的redis文件夹内
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lock.lua")));
        result = (Long) redisTemplate.execute(redisScript, Arrays.asList("prize_stock"), 100, 300);
        System.out.println("lock==" + result);*/
    }
}
