package com.gksl.mapper;

import com.gksl.entity.*;
import com.gksl.util.UtilResult;
import org.apache.ibatis.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;


@Mapper
/*
* 开启二级缓存的标注
* */
@CacheNamespace
public interface TestSqlMapper {



    /*
    * 测试ResponseEntity
    * */
    @Select("select * from testcityanny")
    public List<TestCityAnny> selectTestCityAnnyEntity();



    /*TestCityAnny 部分*/

    /*查询所有的城市*/
    @Select("select * from testcityanny")
    public List<TestCityAnny> selectTestCityAnny();



    /*根据城市进行查询*/
    @Select("select * from testcityanny where Tity=#{Tity}")
    public List<TestCityAnny> selectTestCityAnnybyTity(String Tity);


    /*增加城市信息*/
    @Insert("insert into testcityanny (tity,district,towns) values(#{tity},#{district},#{towns})")
    public int insertTity(TestCityAnny testCityAnny);

    /*删除城市*/
    @Delete({"delete  from testcityanny where Tity=#{Tity}"})
    public int deletetity(String Tity);


    /*修改城市信息 首先是根据城市城市 查询城市*/
    @Update("update TestCityAnny set Towns=#{Towns}, District=#{District} where id=#{id}")
    public int updatetity(TestCityAnny testCityAnny);



    /*关联查询 和根据城是查询的进行一个关联 一对一查询*/
    @Select("select * from testcityannyone where Tity=#{Tity}")
    @Results(
            @Result(property = "testCityAnny",javaType = List.class,column = "Tity",
            one = @One(select = "com.gksl.mapper.TestSqlMapper.selectTestCityAnnybyTity"))
    )
    public List<TestCityAnnyOne> selecttestmabatis(String Tity);


    /*三表的关联查询 一对多的关联  和根据城市查询的进行关联起来 selecttestmabatis selecttestmabatis 又和selectTestCityAnnybyTity 进行关联
    * property 实体类的属性名称   因为是一个我关联查询 多个实体类是放在一块的 在一个实体类里面
    * javaType 实体类的返回类型
    * column  数据库的关联字段 也就是关联的条件
    * */
    @Select("select * from TestCityAnnyTwo where Tity=#{Tity}")
    @Results({
            @Result(property = "testCityAnnyOnes",javaType = List.class,column = "Tity",
                    many =@Many(select = "com.gksl.mapper.TestSqlMapper.selecttestmabatis") )
    })
    public List<TestCityAnnyTwo> selectall(String Tity);



    /*关联查询  和 根据城市和乡镇查询的进行关联 一对一*/
    @Select("select * from testcityannyone where Tity=#{Tity}")
    @Results(
            @Result(property = "testCityAnny", javaType = List.class,column = "Tity",
                    one = @One(select = "com.gksl.mapper.TestSqlMapper.testcityannyoneBycityandTowns"))
    )
    List<TestCityAnnyOne> testcityannyone(String Tity);


    @Select("select * from testcityanny where Tity=#{Tity} and Towns=#{Towns}")
    public List<TestCityAnny> testcityannyoneBycityandTowns(String Tity,String Towns);



    /*TestCityAnnyOne 部分*/


    /*增加数据*/
    @Insert("insert into TestCityAnnyOne (tity,gdp,pcgdp) values(#{tity},#{gdp},#{pcgdp})")
    public int inserttesttityannyone(TestCityAnnyOne testCityAnnyOneMap);


    /*删除数据*/

    @Delete("delete  from testcityannyone where Tity=#{Tity}")
    public int deletetesttityannyone(String Tity);


    /*查询所有*/

    @Select("select * from testcityannyone")
    public List<TestCityAnnyOne> selecttesttityannyone();


    /*根据tity 进行查询*/
    @Select("select * from testcityannyone  where Tity=#{Tity}")
    public List<TestCityAnnyOne> selecttesttityannyonebyTity(String Tity);


    /*修改 更新*/
    @Update("update testcityannyone set Gdp=#{Gdp},Pcgdp=#{Pcgdp} where id=#{id}")
    public int updatetestcityannyone(TestCityAnnyOne testCityAnnyOne);





    /*TestCityAnnyTwo 部分*/

    /*数据的查询*/
    @Select("select * from testcityannytwo where Tity=#{Tity}")
    public List<TestCityAnnyTwo> selectTwo(String Tity);


    /*数据的更改*/
    @Update("update testcityannytwo set Boy=#{Boy},Girl=#{Girl} where id=#{id}")
    public int updateTwo(TestCityAnnyTwo testCityAnnyTwo);



    /*增加*/
    @Insert("insert into testcityannytwo (tity,boy,girl) values (#{tity},#{boy},#{girl})")
    public int insertTwo(TestCityAnnyTwo testCityAnnyTwo);


    /*删除*/
    @Delete("delete  from testcityannytwo where Tity=#{Tity}")
    public int deleteTwo(String Tity);



    /*
    Redis的测试
    * */

    /*
    * 增加
    * */

    @Insert("insert into testcityanny (tity,district,towns) values(#{tity},#{district},#{towns})" )
    public int InsertRedisTest(TestCityAnny testCityAnny);


    /*
    * 查询*/
    @Select("select * from testcityanny where Towns=#{Towns}")
    public List<TestCityAnny> SelectRedisTest(String Towns);


    /*TestCityAnnyThree 部分*/


    @Select("select * from  TestCityAnnyThree  where Name=#{Name}")
    public List<TestCityAnnyThree> SelectThree(String Name);



    @Update("update TestCityAnnyThree set Nunber=#{Nunber} where Name=#{Name}")
    public int updateThree(TestCityAnnyThree testCityAnnyThree);


    @Select("select * from user where id=#{id}")
    public TestUser selectByid(String  id);


    @Select("select * from user where username=#{username}")
    public TestUser findByUsername(String username);


}
