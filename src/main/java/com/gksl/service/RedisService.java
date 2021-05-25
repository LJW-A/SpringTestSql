package com.gksl.service;

import com.gksl.entity.TestCityAnnyThree;
import com.gksl.util.Uuid;

import java.util.concurrent.TimeUnit;

public interface RedisService {

    void set(String key, TestCityAnnyThree testCityAnnyThree, long l, TimeUnit seconds);//带过期时间,单位是秒,可以配

    // String del (String key,String uuid);//删除锁

    Object get(String key);



}
