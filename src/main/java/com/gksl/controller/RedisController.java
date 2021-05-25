package com.gksl.controller;

import com.gksl.entity.TestCityAnny;
import com.gksl.entity.TestCityAnnyThree;
import com.gksl.service.RedisService;
import com.gksl.service.selectTestCityAnnyService;
import com.gksl.util.Base64ImageUtils;
import com.gksl.util.Uuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Controller
public class RedisController {


    @Autowired
    private RedisService redisService;

    @Autowired
    private selectTestCityAnnyService selectTestCityAnnyService;

    @Autowired
    public RedisTemplate redisTemplate;


    @GetMapping("/testRedis")
    @ResponseBody
    public Object testRedis(){

        /*
        * 先将数据存储到 redis里面  然后在存储到书库里面
        * 查询的时候 先查Redis里面 在查数据库
        * 注册的时候 进行此系列操作 数据量不大
        * */
        TestCityAnny testCityAnny=new TestCityAnny();
        testCityAnny.setId(1);
        testCityAnny.setTity("宠物");
        testCityAnny.setDistrict("bb");
        testCityAnny.setTowns("1uihh");
        redisTemplate.opsForValue().set("TestCityAnny",testCityAnny);
        Object name = redisTemplate.opsForValue().get("TestCityAnny");
        System.out.println(name);
        return name;
    }



    /*
    *
    * 插入放入是一个集合*/

    @GetMapping("/testRedisA")
    @ResponseBody
    public Object testRedisA(){
        redisTemplate.opsForSet().add("bbb",88888888);

        return "插入成功 :"+"插入放入数据为"+redisTemplate.opsForSet().members("bbb");
    }


    /*
    *
    * 存储Hash的数据
    * */
    @GetMapping("/RedisHashTest")
    @ResponseBody
    public String RedisHashTest(){
        redisTemplate.opsForHash().put("hashValue","map1","map1-1");
        redisTemplate.opsForHash().put("hashValue","map2","map2-2");
        //获取他的键值对
        Map<Object,Object> map=redisTemplate.opsForHash().entries("hashValue");
        return "增加成功:"+"获取的数据为"+map;

    }

    /*
    * 获取hash数据
    * */

    @GetMapping("/GetRedisHashTest")
    @ResponseBody
    public String GetRedisHashTest(){
        List<Object> hashList = redisTemplate.opsForHash().values("hashValue");
        System.out.println("通过values(H key)方法获取变量中的hashMap值:" + hashList);
        return "查询成功"+hashList;

    }



    /*
    * Redis 测试 增加
    * */

    @PostMapping("/insertRedisController")
    @ResponseBody
    public int insertRedisController(@RequestBody TestCityAnny testCityAnny){
        int i = selectTestCityAnnyService.InsertRedisTestService(testCityAnny);
        return i;
    }


    /*
    * Redis 测试 查询
    *
    * */

    @PostMapping("/SelectRedisController")
    @ResponseBody
    public List<TestCityAnny> SelectRedisController(@RequestBody TestCityAnny testCityAnny){

        List<TestCityAnny> testCityAnnies = selectTestCityAnnyService.SelectRedisTestService(testCityAnny);
        return testCityAnnies;

    }


    /*
    * 限时业务的运用  应用于限时的抽奖或者活动之类的
    *  使用的是redisTemplate.expire("TestCity",20,TimeUnit.SECONDS);进行时间的设置
    *
    * */

    @GetMapping("/TestRedisXianShi")
    @ResponseBody
    public TestCityAnny TestRedisXianShi(){
        TestCityAnny testCityAnny=new TestCityAnny();
        testCityAnny.setTity("西安");
        testCityAnny.setTowns("暴击");
        testCityAnny.setDistrict("咸阳");
        redisTemplate.opsForValue().set("TestCity", testCityAnny);
        //设置这个键的过期时间  多数用于限时抽奖的应用
        redisTemplate.expire("TestCity",20,TimeUnit.SECONDS);
        return  testCityAnny;

    }

    @GetMapping("/TestRedisXianShiTest")
    @ResponseBody
    public String TestRedisXianShiTest(){
        try {
            Thread.sleep(2000);
            Object testCity = redisTemplate.opsForValue().get("TestCity");
            if(testCity==null){
                throw new Error("232121");
            }
        }catch (InterruptedException e){
            e.getCause();
        }finally {
            System.out.println("你说我该不该执行");
        }
        return "未查到数据";
    }


    /*
    *
    * 效仿 确定预约 数量减一
    *
    * Redis 和 数据库保持一致
    *
    * 这个的好处是 可以设置 比如说 一个医生上午的时间只有一个小时  则可以通过Redis 设置这个key的时间为一小时 一小时过后 这个key 小时 则这个key对应的Value 消失
    * */


    @PostMapping("/TestRedisThree")
    @ResponseBody
    public int TestRedisThree(@RequestBody TestCityAnnyThree testCityAnnyThree){
        //查询
        List<TestCityAnnyThree> testCityAnnyThrees = selectTestCityAnnyService.SelectThreeService(testCityAnnyThree.getName());
        for(int i=0;i<testCityAnnyThrees.size();i++){
            TestCityAnnyThree testCityAnnyThree1 = testCityAnnyThrees.get(i);
            int nunber = testCityAnnyThree1.getNunber();
            redisTemplate.opsForValue().set("TestCityAnnyThree",nunber);
        }
        //减一
        redisTemplate.boundValueOps("TestCityAnnyThree").increment(-1);

        //修改(修改到数据库)
        Integer testCityAnnyThree1 = (Integer) redisTemplate.opsForValue().get("TestCityAnnyThree");
        System.out.println(testCityAnnyThree1);
        TestCityAnnyThree testCityAnnyThree2=new TestCityAnnyThree();
        testCityAnnyThree2.setNunber(testCityAnnyThree1);
        testCityAnnyThree2.setName(testCityAnnyThree.getName());
        int i = selectTestCityAnnyService.updateThreeService(testCityAnnyThree2);
        return i;

    }


    /*
     *
     * 效仿 取消预约 数量加一
     *
     * Redis 和 数据库保持一致
     *
     * 和 确定预约的相比的话 取消预约的话 是给这个数据加一  但是必须是在这个数据的使用时间之内的前两个小时
     *  可以进行一个展示 过了这个时间的话 能取消 能退款  不展示
     *
     *
     * */

    @PostMapping("/TestRedisThreeB")
    @ResponseBody
    public int TestRedisThreeB(@RequestBody TestCityAnnyThree testCityAnnyThree){

        //加一
        redisTemplate.boundValueOps("TestCityAnnyThree").increment(1);
        //获取它的对象
        Integer testCityAnnyThree1 = (Integer)redisTemplate.opsForValue().get("TestCityAnnyThree");
        testCityAnnyThree.setNunber(testCityAnnyThree1);
        testCityAnnyThree.setName(testCityAnnyThree.getName());
        int i = selectTestCityAnnyService.updateThreeService(testCityAnnyThree);
        return i;

    }


    /*
    * 获取随机的一个Key的值
    * */

    @GetMapping("/RandomKey")
    @ResponseBody
    public boolean RandomKey(){
        Object o = redisTemplate.randomKey();
        System.out.println(o);
        return true;
    }




    /*
    * 查看这个key 是否存在
    * */
    @GetMapping("/SelectKey")
    @ResponseBody
    public boolean SelectKey (){
        Object o=redisTemplate.hasKey("TestCityAnnyThree");
        if(o!=null){
            System.out.println(o);
            return true;
        }
        return false;

    }



    /*
    * 插入多个值
    * */
    @GetMapping("/TestAddListRedis")
    @ResponseBody
    public String TestAddListRedis(){
        redisTemplate.opsForSet().add("setValue","A","B","C","B","D","E","F");
        //获取所有的值
        Set set=redisTemplate.opsForSet().members("setValue");
        //查看单个值是否存在
        Object oo=redisTemplate.opsForSet().isMember("setValue", "A");
        return "数据是否存在"+oo+"插入放入数据为"+set;

    }




    /*
    * Redis分布式锁
    *
    * 加锁: 其中的意思就是 给这个key设置一个时间 在这个时间里面 只有我一个人使用 过了这个时间别人再使用
    * 解锁: 这一步其实没有必要取些 因为加锁的时候 已经给这个key设置时间了 过了这个时间 这个锁会自动删除
    *  1 但是问题是 在这个时间段 A 这个锁的时间还没有过期 B 过来也要这个锁 这个时候就出现问题了
    *    1.1 解决的办法就是 在B 需要锁的时候 先让去执行这个删除锁 这个删除锁 里面有个判断 如果获取到的key有内容 则说明这个锁还没有过期 这个key
    *    不能给B 如果这个内容为空 则说明这个锁的时间已经到了 B 可以去获取这个锁
    *  2 如果出现A 在准备删除这个锁的时候 突然因为某一个方法的时间大于这个锁的时间 没有及时的删除 锁的时间按到了 已经释放了
    *    此时B 获取到了这个锁 在 B执行的时候 这个 A的某一个方法的执行完了 到了删除锁的哪一个步骤把这个key 删除  此时 B需要这个key 的内容时 找不到这个 Key 对应的内容
    *    2.1  解决办法:  在这个方法的 set方法前设置一个自己的标识符  确保它的唯一性 再删除这个key 之前先判断这个锁是不是自己的 是就删除
    *    不是 就不要删除
    *
    *
    * */


    /*
    *
    * 加锁
    * */

    @GetMapping("/RedisSuo")
    @ResponseBody
    public String RedisSuo(@RequestParam String key){

        TestCityAnnyThree testCityAnnyThree=new TestCityAnnyThree();
        testCityAnnyThree.setName("李华");
        testCityAnnyThree.setNunber(20);
        redisService.set(key,testCityAnnyThree,10L,TimeUnit.SECONDS);
        Object o = redisService.get(key);
        System.out.println(o);
        return "等待十秒 这个锁就会打开:"+"这个锁插入的内容是"+o;
    }


    /*
    * 解锁
    * */
    @GetMapping("/RedisJieSuo/{key}")
    @ResponseBody
    public String RedisJieSuo(@PathVariable String key){
        try {
            Thread.sleep(1000);
            Object o = redisService.get(key);
            if(o==null){
                return "这个锁已经被删除 现在可以请另一个需要锁的人来执行上面的任务 ";
            }
            System.out.println(o);
            return "这个锁的时间还没有过期 您不能使用:"+"这个锁获取的内容是"+o;
        }catch ( Exception e){
            e.printStackTrace();
        }
        return "这个锁有问题";

    }

    /*
    * 存储图片 Redis
    * 1 获取图片 将图片转换为base64
    * 2 redisTemplate.opsForValue().set("",转换的base64)
    *
    * 展示图片
    *  redisTemplate.opsForValue().get("key");
    * 1 解析 base64 图片
    * 2 展示图片
    *
    * */

    @GetMapping("/testBase64")
    @ResponseBody
    public String testBase64(){
        URL url = null;
        try {
            url = new URL("https://img0.baidu.com/it/u=1685320185,1992788721&fm=26&fmt=auto&gp=0.jpg");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Base64ImageUtils base64ImageUtils=new Base64ImageUtils();
        //转换为base64
        String s = base64ImageUtils.encodeImgageToBase64(url);
        redisTemplate.opsForValue().set("IMG",s);
        return "插入成功";
    }


    /*
    * 获取图片 前端进行base64的转换 目前是存在E:/ 当中
    * */
    @GetMapping("/huoquIMG")
    @ResponseBody
    public String  huoquIMG(){
        Base64ImageUtils base64ImageUtils=new Base64ImageUtils();
        String  img =(String) redisTemplate.opsForValue().get("IMG");
        //解析为图片
        base64ImageUtils.decodeBase64ToImage(img, "E:/", "football.jpg");
        return img;
    }


}
