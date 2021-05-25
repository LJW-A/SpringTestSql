package com.gksl.controller;

import com.gksl.entity.TestUser;
import com.gksl.service.TestUserService;
import com.gksl.token.CreatToken;
import com.gksl.token.UserLoginToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("api")
public class TokenController {

    @Autowired
    private TestUserService testUserService;

    @Autowired
    private CreatToken creatToken;

    @Autowired
    private RedisTemplate redisTemplate;


    //登录
    @PostMapping("/login")
    public Object login(HttpServletRequest request , @RequestBody TestUser testUser) {

        Map jsonObject=new HashMap();
        HttpSession session = request.getSession();
        session.setAttribute("session",jsonObject);

        TestUser userForBase=testUserService.findByUsernameservice(testUser);
        if(userForBase==null){
            jsonObject.put("message","登录失败,用户不存在");
            return jsonObject;
        }else {
            if (!userForBase.getPassword().equals(testUser.getPassword())){
                jsonObject.put("message","登录失败,密码错误");
                return jsonObject;
            }else {


                String token = creatToken.getToken(userForBase);

                //给这个token设置过期时间 过去后 就的重新登陆 20秒需要重新的登陆
                redisTemplate.opsForValue().set("token",token,20, TimeUnit.SECONDS);

                jsonObject.put("token", token);
                jsonObject.put("user", userForBase);
                return jsonObject;
            }
        }
    }

    @UserLoginToken
    @GetMapping("/getMessage")
    public String getMessage(){
        return "你已通过验证";
    }



}
