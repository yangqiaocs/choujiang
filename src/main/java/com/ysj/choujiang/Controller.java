package com.ysj.choujiang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v0/choujiang")
public class Controller {

    @Autowired
    private RedisTemplate redisTemplate;

    private List<JiangPing> jiangPingList = new ArrayList<JiangPing>();

    @GetMapping("/jiangpings")
    public List<JiangPing> jiangPingList(){
        return (List<JiangPing>) redisTemplate.opsForValue().get("jiangpinglist");
    }

    @PostMapping("/jiangpings")
    public void jiangPingStore(@RequestBody JiangPing jiangPing){
        jiangPingList.add(jiangPing);
        redisTemplate.opsForValue().set("jiangpinglist",jiangPingList);
    }
}
