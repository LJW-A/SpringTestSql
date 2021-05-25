package com.gksl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.xml.ws.Action;
import java.util.List;

@Controller
public class TestController {


    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/testCityActiveMq")
    @ResponseBody
    public Object testCityActiveMq(){
        String url="http://ACTIVEMQ:8089/ActiveMqCity";
        List forObject = restTemplate.getForObject(url, List.class);
        return forObject;

    }
}
