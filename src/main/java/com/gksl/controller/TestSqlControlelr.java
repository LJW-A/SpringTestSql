package com.gksl.controller;

import com.gksl.entity.TestCityAnny;
import com.gksl.entity.TestCityAnnyOne;
import com.gksl.entity.TestCityAnnyTwo;
import com.gksl.service.selectTestCityAnnyService;
import com.gksl.util.ErrorExection;
import com.gksl.util.UtilResult;
import net.minidev.json.JSONArray;
import org.assertj.core.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@CrossOrigin(origins="*",methods= {RequestMethod.GET,RequestMethod.POST})
public class TestSqlControlelr {

    @Autowired
    private selectTestCityAnnyService selectTestCityAnnyService;


    /*TestCityAnny 部分*/


    /*查所有的  测试ResponseEntity
    * 这个和@ResponseBody 最大的区别 就是 一个只返回json数据 一个和响应头 和状态 都一起返回
    *
    * 响应头 和这个状态 是直接可以调用他自身的API
    *  调用他的API  return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    * */
    @GetMapping("/testSqlselectResponseEntity")
    // @ResponseBody
    private ResponseEntity<List<TestCityAnny>> testSqlselectResponseEntity(){
        List<TestCityAnny> categories = selectTestCityAnnyService.selectTestCityAnnyEntity();
        if (categories != null && 0!=categories.size()) {
            //返回数据就为http响应体内容
            return ResponseEntity.ok(categories);
        }
        //返回响应状态码204
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    /*查所有的*/
    @GetMapping("/testSqlselect")
    @ResponseBody
    private List<TestCityAnny> testSqlselect(){
        List<TestCityAnny> testCityAnnies = selectTestCityAnnyService.selectTestCityAnnyService();
        System.out.println(testCityAnnies);
        return testCityAnnies;

    }


    /*根据城市查询*/
    @GetMapping("/testSqlselectbytity")
    @ResponseBody
    public List<TestCityAnny> testSqlselectbytity(@RequestParam String Tity){
        List<TestCityAnny> testCityAnnies = selectTestCityAnnyService.selectTestCityAnnybyTity(Tity);
        System.out.println(testCityAnnies);
        return testCityAnnies;

    }


    /*增加*/
    @PostMapping("/testinserttity")
    @ResponseBody
    public int testinserttity(@RequestBody TestCityAnny testCityAnny){
        int i = selectTestCityAnnyService.insertTityservice(testCityAnny);
        System.out.println(i);
        return i;
    }



    /*删除*/
    @GetMapping("/testdeletetity")
    @ResponseBody
    public int testdeletetity(@RequestParam String Tity){
        int deletetityservice = selectTestCityAnnyService.deletetityservice(Tity);
        System.out.println(deletetityservice);
        return deletetityservice;
    }


    /*修改*/
    @PostMapping("/testupdatetity")
    @ResponseBody
    public int testupdatetity(@RequestBody TestCityAnny testCityAnny){
        int updatetityservice = selectTestCityAnnyService.updatetityservice(testCityAnny);
        System.out.println(updatetityservice);
        return updatetityservice;
    }


    /*第一个关联查询*/
    @GetMapping("/testselectmybatis")
    @ResponseBody
    public List<TestCityAnnyOne> testselectmybatis(String Tity){
        List<TestCityAnnyOne> selecttestmabatisservice = selectTestCityAnnyService.selecttestmabatisservice(Tity);
        System.out.println(selecttestmabatisservice);
        return selecttestmabatisservice;

    }


    /*关联查询一对多*/
    @GetMapping("/testselectall")
    @ResponseBody
    public List<TestCityAnnyTwo> testselectall(String Tity){
        List<TestCityAnnyTwo> selectallservice = selectTestCityAnnyService.selectallservice(Tity);
        System.out.println(selectallservice);
        return selectallservice;


    }


    /*关联查询*/
    @GetMapping("/testcityannyone")
    @ResponseBody
    public List<TestCityAnnyOne> testcityannyone(String Tity,String Towns){

        /*调用第一个*/
        /*根据城市和乡镇 查询出数据*/
        List<TestCityAnny> testCityAnnies = selectTestCityAnnyService.testcityannyoneBycityandTownsservice(Tity, Towns);
        for (int i=0;i<testCityAnnies.size();i++){
            TestCityAnny testCityAnny = testCityAnnies.get(i);
            String tity = testCityAnny.getTity();

            /*将查询出来的一个作为第二个sql语句的条件查询 这一步就是使用关联查询将整合业务查出来*/
            List<TestCityAnnyOne> testcityannyoneservice = selectTestCityAnnyService.testcityannyoneservice(tity);

            for(int j=0;j<testcityannyoneservice.size();j++){
                TestCityAnnyOne testCityAnnyOne = testcityannyoneservice.get(j);
                /*将这个查询出来的数据 饭到要返回的类中*/
                testCityAnnyOne.setTestCityAnny(testCityAnnies);

            }
            System.out.println(testcityannyoneservice);
            return testcityannyoneservice;

        }
         throw new Error("出现错误");

    }

    /*TestCityAnnyOne 部分*/

    /*增加*/

    @PostMapping("/inserttestTityone")
    @ResponseBody
    public int inserttestTityone(@RequestBody TestCityAnnyOne testCityAnnyOne){

        Map<String,Object> map=new HashMap();
        String tity = testCityAnnyOne.getTity();
        int gdp = testCityAnnyOne.getGdp();
        int pcgdp = testCityAnnyOne.getPcgdp();
        map.put("aaa",tity);
        map.put("bbb",gdp);
        map.put("ccc",pcgdp);
        System.out.println(map);
        System.out.println(map.get("aaa"));
        int insertoneservice = selectTestCityAnnyService.insertoneservice(testCityAnnyOne);
        System.out.println(insertoneservice);
        return insertoneservice;

    }



    /*删除*/
    @GetMapping("/deletetesttityone")
    @ResponseBody
    public int deletetesttityone(@RequestParam String Tity){
        int deleteoneservice = selectTestCityAnnyService.deleteoneservice(Tity);
        System.out.println(deleteoneservice);
        return deleteoneservice;

    }

    /*查询*/
    @PostMapping("/selecttestannyone")
    @ResponseBody
    public List<TestCityAnnyOne> selecttestannyone(){
        List<TestCityAnnyOne> selectoneservice = selectTestCityAnnyService.selectoneservice();
        return selectoneservice;

    }

    /*查询 根据tity 进行查询*/
    @GetMapping("/selecttestannyonebytytity")
    @ResponseBody
    public List<TestCityAnnyOne> selecttestannyonebytytity(@RequestParam String Tity){
        List<TestCityAnnyOne> testCityAnnyOnes = selectTestCityAnnyService.selectoneservicebyTity(Tity);
        return testCityAnnyOnes;

    }


    @PostMapping("/updateone")
    @ResponseBody
    public UtilResult updateone(@RequestBody TestCityAnnyOne testCityAnnyOne){
        UtilResult utilResult=new UtilResult();
        List<TestCityAnnyOne> testCityAnnyOnes = selectTestCityAnnyService.selectoneservicebyTity(testCityAnnyOne.getTity());
            if (testCityAnnyOnes.isEmpty()){
                //throw new Error("有问题"); //这个也是相当于他一个退出 有这个和就不需要 return
                utilResult.setCode(11);
                utilResult.setMsg("数据为空 检查或者更新数据");
                utilResult.setDate(testCityAnnyOnes);
                return utilResult;
            }
        UtilResult updateone = selectTestCityAnnyService.updateone(testCityAnnyOne);
        utilResult.setCode(22);
        utilResult.setMsg("修改成功");
        utilResult.setDate(updateone);

        return utilResult;

    }


    /*测试 Map*/
    @PostMapping("/testmap")
    @ResponseBody
    public String  testmap(@RequestBody Map map){
        map.get("tity");
        map.get("abc");
        System.out.println(map);
        List<Object> list=new ArrayList<>();
        list.add(map);
        for (int i=0;i<list.size();i++){
        System.out.println(list.get(i).toString());

        }

        return "获取成功";


    }



    /*TestCityAnnyTwo 部分*/


    /*查询*/
    @GetMapping("/seleteTwoByTity")
    @ResponseBody
    public UtilResult seleteTwoByTity(@RequestParam String Tity){
        UtilResult utilResulta=new UtilResult();
        UtilResult utilResult = selectTestCityAnnyService.selectTwoService(Tity);
        utilResulta.setCode(11);
        utilResulta.setDate(utilResult);
        return utilResulta;

    }


    /*修改*/

    @PostMapping("/updateTwo")
    @ResponseBody
    public UtilResult updateTwo(@RequestBody TestCityAnnyTwo testCityAnnyTwo){
        UtilResult utilResulta=new UtilResult();
        UtilResult utilResultb = selectTestCityAnnyService.updateTwoService(testCityAnnyTwo);
        int code = utilResultb.getCode();
        if(code==1111){
            utilResulta.setMsg("hahahahaah");


        }
        return utilResulta;
    }


    /*增加数据*/
    @PostMapping("/insertTwo")
    @ResponseBody
    private UtilResult insertTwo(@RequestBody TestCityAnnyTwo testCityAnnyTwo){
       /* UtilResult utilResult=new UtilResult();*/
        UtilResult utilResult1 = selectTestCityAnnyService.insertTwoService(testCityAnnyTwo);
        return utilResult1;
    }


    /*删除*/

    @GetMapping("/delteTwo")
    @ResponseBody
    public UtilResult delteTwo(@RequestParam String Tity){
        UtilResult utilResult = selectTestCityAnnyService.deleteTwoService(Tity);
        return utilResult;

    }
}
