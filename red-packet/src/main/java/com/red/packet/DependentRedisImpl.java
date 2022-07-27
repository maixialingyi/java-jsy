package com.red.packet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class DependentRedisImpl {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 固定总金额,红包金额最大N元
     */
    public void unpack(String userId,String hkey, String hfield,int mixAcount){
        /*Long count = redisTemplate.opsForSet().add("redPacketUser", userId);
        if(count == 0){
            System.out.println("已领取红包");
        }*/

        int total = (int) redisTemplate.opsForHash().get("redPacket", "totalAcount");
        System.out.println(total);
        //小红包金额
        int amount = new Random().nextInt(mixAcount);
        Long increment = redisTemplate.opsForHash().increment("redPacket", "totalAcount", -amount);

        System.out.println("拆包金额: "+amount +"  剩余金额:"+increment);
    }

}
