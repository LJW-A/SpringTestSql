package com.gksl.service.impl;

import com.gksl.entity.TestCityAnnyThree;
import com.gksl.service.RedisService;
import com.gksl.util.Uuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {


    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void set(String key, TestCityAnnyThree testCityAnnyThree, long l,TimeUnit seconds) {
        redisTemplate.opsForValue().set(key,testCityAnnyThree,l,TimeUnit.SECONDS);


    }

    @Override
    public Object get(String key) {
        ValueOperations  valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);

    }



}
