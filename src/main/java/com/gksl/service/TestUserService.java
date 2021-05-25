package com.gksl.service;

import com.gksl.entity.TestUser;

public interface TestUserService {

   public TestUser selectByidservice(String id);

   public TestUser  findByUsernameservice(TestUser testUser);
}
