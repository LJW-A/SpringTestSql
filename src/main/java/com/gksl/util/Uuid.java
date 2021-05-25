package com.gksl.util;

import java.util.UUID;

public class Uuid {

    public String testuuid() {
        for (int i = 0; i < 1; i++) {
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            return uuid;
        }
        return "uuid不成立";
    }

}
