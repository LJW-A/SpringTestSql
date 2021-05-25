package com.gksl.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.gksl.entity.TestUser;
import org.springframework.stereotype.Component;

@Component
public class CreatToken {

    public String getToken(TestUser testUser) {

        String token=" ";
        token= JWT.create().withAudience(testUser.getId())
                .sign(Algorithm.HMAC256(testUser.getPassword()));
        return token;
    }


}
