package com.gksl.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.Thread.sleep;

@RestController
public class TestHystrix {


    /*出现问题调用下一个方法*/
    @HystrixCommand(fallbackMethod = "TestHtstrixBeitai")
    @GetMapping("/TestHtstrix")
    public String TestHtstrix() throws InterruptedException {
        sleep(1000);
        return "你调用的不是备胎";

    }

    @GetMapping("/TestHtstrixBeitai")
    public String TestHtstrixBeitai(){
        return "你调用的是备胎";

    }


}
