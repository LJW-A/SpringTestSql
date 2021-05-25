package com.gksl.service;

import com.gksl.entity.TestCityAnny;
import com.gksl.entity.TestCityAnnyOne;
import com.gksl.entity.TestCityAnnyThree;
import com.gksl.entity.TestCityAnnyTwo;
import com.gksl.util.UtilResult;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface selectTestCityAnnyService {



    /*测试ResponseEntity
    * */
    public List<TestCityAnny> selectTestCityAnnyEntity();


    /*TestCityAnny 部分*/

    public List<TestCityAnny> selectTestCityAnnyService();

    public List<TestCityAnny> selectTestCityAnnybyTity(String Tity);

    public int insertTityservice(TestCityAnny testCityAnny);

    public int deletetityservice(String Tity);


    public int updatetityservice(TestCityAnny testCityAnny);


    List<TestCityAnnyTwo> selectallservice(String Tity);

    List<TestCityAnnyOne> selecttestmabatisservice(String Tity);


    List<TestCityAnnyOne> testcityannyoneservice(String Tity);


    List<TestCityAnny> testcityannyoneBycityandTownsservice(String Tity,String Towns);



    /*TestCityAnnyOne 部分*/

    /*增加*/
    public int insertoneservice(TestCityAnnyOne testCityAnnyOneMap);

    /*删除*/
    public int deleteoneservice (String Tity);

    /*查询*/
    public List<TestCityAnnyOne> selectoneservice();


    /*查询 根据Tity进行数据的查询*/
    public List<TestCityAnnyOne> selectoneservicebyTity(String Tity);


    /* 更新数据*/
    public UtilResult updateone(TestCityAnnyOne testCityAnnyOne);


    /*TestCityAnnyTwo 部分*/

    /*查询  根据城市*/
    public UtilResult selectTwoService(String Tity);


    /*修改*/
    public UtilResult updateTwoService(TestCityAnnyTwo testCityAnnyTwo);


    /*增加*/
    public UtilResult insertTwoService(TestCityAnnyTwo testCityAnnyTwo);

    /*删除*/
    public UtilResult deleteTwoService(String Tity);



    /*
    * Redis测试
    * */

    //增加
    public int InsertRedisTestService(TestCityAnny testCityAnny);


    //查询
    public List<TestCityAnny> SelectRedisTestService(TestCityAnny testCityAnny);


    /*TestCityAnnyThree 部分*/

    public List<TestCityAnnyThree> SelectThreeService(String Name);

    public int updateThreeService(TestCityAnnyThree testCityAnnyThree);
}
