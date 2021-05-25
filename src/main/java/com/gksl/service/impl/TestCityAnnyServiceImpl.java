package com.gksl.service.impl;


import com.gksl.entity.TestCityAnny;
import com.gksl.entity.TestCityAnnyOne;
import com.gksl.entity.TestCityAnnyThree;
import com.gksl.entity.TestCityAnnyTwo;
import com.gksl.mapper.TestSqlMapper;
import com.gksl.service.selectTestCityAnnyService;
import com.gksl.util.UtilResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestCityAnnyServiceImpl implements selectTestCityAnnyService {

    @Autowired
    private TestSqlMapper testSqlMapper;


    @Autowired
    public RedisTemplate redisTemplate;



    /*
    * 测试ResponseEntity
    * */

    @Override
    public List<TestCityAnny> selectTestCityAnnyEntity() {
        return testSqlMapper.selectTestCityAnnyEntity();
    }

    /*TestCityAnny 部分*/

    @Override
    public List<TestCityAnny> selectTestCityAnnyService() {
        return testSqlMapper.selectTestCityAnny();
    }

    @Override
    public List<TestCityAnny> selectTestCityAnnybyTity(String Tity) {
        return testSqlMapper.selectTestCityAnnybyTity(Tity);
    }

    @Override
    public int insertTityservice(TestCityAnny testCityAnny) {
        return testSqlMapper.insertTity(testCityAnny);
    }

    @Override
    public int deletetityservice(String Tity) {
        return testSqlMapper.deletetity(Tity);
    }

    @Override
    public int updatetityservice(TestCityAnny testCityAnny) {
        return testSqlMapper.updatetity(testCityAnny);
    }


    @Override
    public List<TestCityAnnyOne> selecttestmabatisservice(String Tity) {
        return testSqlMapper.selecttestmabatis(Tity);
    }

    @Override
    public List<TestCityAnnyTwo> selectallservice(String Tity) {
        return testSqlMapper.selectall(Tity);
    }

    @Override
    public List<TestCityAnnyOne> testcityannyoneservice(String Tity) {
        return testSqlMapper.testcityannyone(Tity);
    }

    @Override
    public List<TestCityAnny> testcityannyoneBycityandTownsservice(String Tity, String Towns) {

        return testSqlMapper.testcityannyoneBycityandTowns(Tity,Towns);
    }

    /*TestCityAnnyOne 部分*/

    @Override
    public int insertoneservice(TestCityAnnyOne testCityAnnyOneMap) {
        return testSqlMapper.inserttesttityannyone(testCityAnnyOneMap);
    }

    /*删除*/
    @Override
    public int deleteoneservice(String Tity) {
        return testSqlMapper.deletetesttityannyone(Tity);
    }

    /*查询*/
    @Override
    public List<TestCityAnnyOne> selectoneservice() {
        return testSqlMapper.selecttesttityannyone();
    }

    /*根据Tity查询*/

    @Override
    public List<TestCityAnnyOne> selectoneservicebyTity(String Tity) {
        return testSqlMapper.selecttesttityannyonebyTity(Tity);
    }

    /*更新数据*/

    @Override
    public UtilResult updateone(TestCityAnnyOne testCityAnnyOne) {
        UtilResult utilResult=new UtilResult();
        int updatetestcityannyone = testSqlMapper.updatetestcityannyone(testCityAnnyOne);
        utilResult.setCode(updatetestcityannyone);
        return utilResult;
    }



    /*TestCityAnnyTwo 部分*/

    /*根城市查询*/

    @Override
    public UtilResult selectTwoService(String Tity) {
        UtilResult utilResult=new UtilResult();
        List<TestCityAnnyTwo> testCityAnnyTwos = testSqlMapper.selectTwo(Tity);
        if(testCityAnnyTwos!=null){
            utilResult.setCode(44);
            utilResult.setDate(testCityAnnyTwos);
            return utilResult;
        }
        utilResult.setCode(555);
        return utilResult;
    }


    /*修改 更新*/

    @Override
    public UtilResult updateTwoService(TestCityAnnyTwo testCityAnnyTwo) {
        UtilResult utilResult=new UtilResult();
        List<TestCityAnnyTwo> testCityAnnyTwos = testSqlMapper.selectTwo(testCityAnnyTwo.getTity());
        if(testCityAnnyTwos.isEmpty()){
            utilResult.setCode(1234);
            utilResult.setMsg("参数有误");
            return utilResult;
        }

        int i = testSqlMapper.updateTwo(testCityAnnyTwo);
        utilResult.setCode(1111);
        utilResult.setMsg("修改成功");
        utilResult.setDate(i);
        return utilResult;
    }

    /*增加*/

    @Override
    public UtilResult insertTwoService(TestCityAnnyTwo testCityAnnyTwo) {
        UtilResult utilResult=new UtilResult();
        int testCityAnnyTwos = testSqlMapper.insertTwo(testCityAnnyTwo);
        utilResult.setCode(7777);
        utilResult.setMsg("增加数据成功");
        utilResult.setDate(testCityAnnyTwos);

        return utilResult;
    }


    /*删除*/
    @Override
    public UtilResult deleteTwoService(String Tity) {
        UtilResult utilResult=new UtilResult();
        int i = testSqlMapper.deleteTwo(Tity);
        utilResult.setCode(8888);
        utilResult.setMsg("删除成功");
        utilResult.setDate(i);
        return utilResult;
    }

    /*
    *
    * Redis测试 增加
    * */

    @Override
    /*
    * 开启的二级缓存 它的存储方式和opsForValue().set("City",testCityAnny) 的存储方式是不一样的 它是一次行展示完
    * 而 opsForValue().set("City",testCityAnny) 的存储方式 是以Key value 的形式进行展示的 获取key 就能换取到Value
    *  当然二级缓存的好处就是简单方便
    * */
    @Cacheable(value = "CityA")
    public int InsertRedisTestService(TestCityAnny testCityAnny) {
        //redisTemplate.opsForValue().set("City",testCityAnny);
        int i = testSqlMapper.InsertRedisTest(testCityAnny);
        return i;
    }

    /*
    * 查询*/
    @Override
    public List<TestCityAnny> SelectRedisTestService(TestCityAnny testCityAnny) {
        //查询的时候  获取Redis里面的数据  没有的话 在去查数据库 目前这个City和二级缓存的那个CityA 是一样的内容
        Object city = redisTemplate.opsForValue().get("City");
        if(city==null){
            throw new Error("50552");
        }
        return testSqlMapper.SelectRedisTest(testCityAnny.getTowns());
    }




    /*TestCityAnnyThree 部分*/

    @Override
    public List<TestCityAnnyThree> SelectThreeService(String Name) {
        return testSqlMapper.SelectThree(Name);
    }


    @Override
    public int updateThreeService(TestCityAnnyThree testCityAnnyThree) {
        return testSqlMapper.updateThree(testCityAnnyThree);
    }
}
