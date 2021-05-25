package com.gksl.service.impl;

import com.gksl.entity.TestUser;
import com.gksl.mapper.TestSqlMapper;
import com.gksl.service.TestUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestUserServiceImpl implements TestUserService {


    @Autowired
    private TestSqlMapper testSqlMapper;


    @Override
    public TestUser selectByidservice(String id) {
        return testSqlMapper.selectByid(id);
    }


    @Override
    public TestUser findByUsernameservice(TestUser testUser) {
        return testSqlMapper.findByUsername(testUser.getUsername());
    }
}
