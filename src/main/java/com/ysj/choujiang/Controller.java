package com.ysj.choujiang;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v0/choujiang")
public class Controller {

    @Autowired
    private RedisTemplate redisTemplate;

    private List<UUID> userList = new ArrayList<UUID>();
    private HashMap<String,Integer> jiangPingMap = new HashMap<String, Integer>();


    @GetMapping("/jiangpings")
    public HashMap<String,Integer> jiangPingList(){
        return (HashMap<String,Integer>) redisTemplate.opsForValue().get("jiangpingmap");
    }

    @PostMapping("/jiangpings")
    public void jiangPingStore(@RequestBody JiangPing jiangPing){
        jiangPingMap.put(jiangPing.name,jiangPing.restNum);
        redisTemplate.opsForValue().set("jiangpingmap",jiangPingMap);
    }

    @PostMapping("")
    public Integer chouJiang(){
        Integer num = new Random().nextInt(20);
        if(jiangPingMap.get("iphone 13")<=0){
            return -1;
        }
        if(num<10){

            synchronized (jiangPingMap){
                return jiangPingMap.compute("iphone 13",(key,value) -> {
                    if (value == 0){
                        userList.add(UUID.randomUUID());
                        return 0;
                    }else{
                        return value-1;
                    }
                });
            }
        }
        return -1;
    }

}
