package org.example.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.hutool.json.JSONUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.elasticsearch.action.index.IndexResponse;
import org.example.common.Result;
import org.example.common.annotation.Limit;
import org.example.common.aop.LimitType;
import org.example.domin.EsGoods;
import org.example.domin.Goods;
import org.example.domin.myenum.IntegerEnum;
import org.example.service.EsService;
import org.example.service.SendService;
import org.example.service.impl.ChannelFactory;
import org.example.service.impl.MyMailServiceImpl;
import org.example.service.impl.Server;
import org.example.service.impl.TestServiceImpl;
import org.example.utils.RedisUtil;
import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private SendService sendService;
    @Resource
    private TestServiceImpl testServiceImpl;
    @Resource
    private EsService esService;
    @Resource
    private ChannelFactory channelFactory;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private MyMailServiceImpl myMailServiceImpl;

    @ApiOperation(value = "Redis操作Set", notes = "Redis操作Set")
    @PostMapping("set")
    public String test(){
//        Long value = redisTemplate.opsForSet().add("cc", "1", "2", "3", "4", "5");
//        System.out.println("value--------->" + value);
//        Long value2 = redisTemplate.opsForSet().add("cc", "2");
//        System.out.println("value2--------->" + value2);
//        Long value3 = redisTemplate.opsForSet().add("cc", "2", "6");
//        System.out.println("value3--------->" + value3);
        List<Long> longList = new ArrayList<>();
        longList.add(1001L);
        longList.add(1002L);
        Long value4 = redisTemplate.opsForSet().add("cc", longList.toArray());
        System.out.println("value4--------->" + value4);
        return "ok";
    }

    @ApiOperation(value = "抽奖", notes = "抽奖")
    @PostMapping("lottery")
    public String lottery(){
        String pop = (String)redisTemplate.opsForSet().pop("cc");
        return pop;
    }

    @ApiOperation(value = "测试限制并发", notes = "测试限制并发")
    @PostMapping("concurrent")
    public String concurrent(){
        Long count = redisTemplate.opsForValue().increment("zuul:count");
        if (count == 1) {
            redisTemplate.expire("zuul:count",60, TimeUnit.SECONDS);
        }
        if (count <= 5) {
            return "ok";
        }
        return "error";
    }

    @ApiOperation(value = "购物车初始化", notes = "购物车初始化")
    @PostMapping("shoppingInit")
    public String shoppingInit(){
        HashOperations operations = redisTemplate.opsForHash();
        Goods goods = new Goods() {{
            setId(2001L);
            setName("小米平板");
            setNum(1);
        }};
        //key,商品id,商品详情
        operations.put("cart:1001", String.valueOf(goods.getId()), JSONUtil.parse(goods).toString());

        goods = new Goods() {{
            setId(2002L);
            setName("华为手机");
            setNum(2);
        }};
        //key,商品id,商品详情
        operations.put("cart:1001", String.valueOf(goods.getId()), JSONUtil.parse(goods).toString());

        Map<Integer,String> map = operations.entries("cart:1001");
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            //Map.entry<Integer,String> 映射项（键-值对）  有几个方法：用上面的名字entry
            //entry.getKey() ;entry.getValue(); entry.setValue();
            //map.entrySet()  返回此映射中包含的映射关系的 Set视图。
            System.out.println("商品id= " + entry.getKey() + " and 商品详情= " + entry.getValue());
        }
        return map.toString();
    }

    @ApiOperation(value = "购物车添加", notes = "购物车添加")
    @PostMapping("shoppingAdd")
    public String shoppingAdd(){
        HashOperations operations = redisTemplate.opsForHash();

        String goodsStr = (String) operations.get("cart:1001", "2001");
        Goods goods = new Goods();
        if (StringUtils.isEmpty(goodsStr)) {
            goods.setId(2001L).setNum(1).setName("小米平板");
        }else {
            goods = JSONUtil.toBean(goodsStr, Goods.class);
            if (goods != null) {
                System.out.println(goods.toString());
                goods.setNum(goods.getNum() + 1);
            }
        }
        operations.put("cart:1001", String.valueOf(goods.getId()), JSONUtil.parse(goods).toString());

        List<Goods> goodsList = new ArrayList<>();
        Map<Integer,String> map = operations.entries("cart:1001");
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            //Map.entry<Integer,String> 映射项（键-值对）  有几个方法：用上面的名字entry
            //entry.getKey() ;entry.getValue(); entry.setValue();
            //map.entrySet()  返回此映射中包含的映射关系的 Set视图。
            System.out.println("商品id= " + entry.getKey() + " and 商品详情= " + entry.getValue());
            Goods single = JSONUtil.toBean(entry.getValue(), Goods.class);
            goodsList.add(single);
        }
        System.out.println("购物车---->" + goodsList.toString());
        return map.toString();
    }

    @ApiOperation(value = "导出列表Excel")
    @RequestMapping(value = "/exportMemberList", method = RequestMethod.GET)
    public void exportMemberList(ModelMap map,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws IOException {
//        List<Goods> goodsList = new ArrayList<>();
//        goodsList.add(new Goods().setName("华为平板").setNum(10));
//        goodsList.add(new Goods().setName("小米11").setNum(500));
//        ExportParams params = new ExportParams();
//        params.setSheetName("商品列表");
//        params.setType(ExcelType.XSSF);
//        map.put(NormalExcelConstants.DATA_LIST, goodsList);
//        map.put(NormalExcelConstants.CLASS, Goods.class);
//        map.put(NormalExcelConstants.PARAMS, params);
//        map.put(NormalExcelConstants.FILE_NAME, "goods");  //文件名不能用中文
//        PoiBaseView.render(map, request, response, NormalExcelConstants.EASYPOI_EXCEL_VIEW);

        // 表头定义 可以将表头配置在数据库中，然后在代码里动态生成表头
        // 这里只是展示如何用代码生成表头
        List<ExcelExportEntity> columnList = new ArrayList<>();
        ExcelExportEntity colEntity1 = new ExcelExportEntity("序号", "id");
        colEntity1.setNeedMerge(true);
        columnList.add(colEntity1);

        ExcelExportEntity colEntity2 = new ExcelExportEntity("用户名称", "username");
        colEntity2.setNeedMerge(true);
        columnList.add(colEntity2);

        ExcelExportEntity yhxxGroup = new ExcelExportEntity("商品信息", "yhxx");
        List<ExcelExportEntity> yyxxList = new ArrayList<>();
        yyxxList.add(new ExcelExportEntity("商品名称", "name"));
        yyxxList.add(new ExcelExportEntity("商品数量", "num"));
        yhxxGroup.setList(yyxxList);
        columnList.add(yhxxGroup);

        // 数据拉取 一般需要从数据库中拉取
        // 这里是手动模拟数据
        List<Map<String, Object>> dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> values = new HashMap<>();
            values.put("id", i);
            values.put("username", "姓名" + i);

            List<Map<String, Object>> yhxxList = new ArrayList<>();
            Map<String, Object> yhxxMap = new HashMap<>();
            yhxxMap.put("name", "商品名称" + i);
            yhxxMap.put("num", "商品数量" + i);
            yhxxList.add(yhxxMap);

            values.put("yhxx", yhxxList);
            dataList.add(values);
        }

        // 定义标题和sheet名称
        ExportParams params = new ExportParams();
        params.setSheetName("商品列表");
        params.setType(ExcelType.XSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(params, columnList, dataList);
        export(response,workbook,"111.xlsx");
    }

    // Excel 导出 通过浏览器下载的形式
    public static void export(HttpServletResponse response, Workbook workbook, String fileName) throws IOException {
        response.setHeader("Content-Disposition",
                "attachment;filename=" + new String(fileName.getBytes("UTF-8"), "iso8859-1"));
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        BufferedOutputStream bufferedOutPut = new BufferedOutputStream(response.getOutputStream());
        workbook.write(bufferedOutPut);
        bufferedOutPut.flush();
        bufferedOutPut.close();
    }

    @ApiOperation(value = "布隆过滤器", notes = "布隆过滤器")
    @PostMapping("bloomFilter")
    public void testBool(){

        Config config = new Config();
        config.useSingleServer().setAddress("redis://172.16.253.34:6379");
        config.useSingleServer().setPassword("qfjava");
        //构造Redisson
        RedissonClient redisson = Redisson.create(config);

        RBloomFilter<String> bloomFilter = redisson.getBloomFilter("nameList");
        //初始化布隆过滤器：预计元素为100000000L,误差率为3%,根据这两个参数会计算出底层的bit数组大小
        bloomFilter.tryInit(100000000L,0.03);
        //将xiaoming插入到布隆过滤器中
        bloomFilter.add("xiaoming");

        //判断下面号码是否在布隆过滤器中
        System.out.println(bloomFilter.contains("xiaoli"));//false
        System.out.println(bloomFilter.contains("xiaowang"));//false
        System.out.println(bloomFilter.contains("xiaoming"));//true
    }


    @ApiOperation(value = "发消息", notes = "发消息")
    @PostMapping("sendMessage")
    public void sendMessage(){
//        sendService.sendMessage();
//        sendService.sendFanoutMessage();
//        sendService.sendTopicMessage();

        sendService.sendNormalMessage();
    }

    @ApiOperation(value = "发送延迟消息", notes = "发送延迟消息")
    @PostMapping("sendDelayedMessage")
    public void sendMessage(Integer time){
        sendService.sendDelayedMessage(time);
    }

    @ApiOperation(value = "测试线程池", notes = "测试线程池")
    @PostMapping("testThreadPool")
    public void testThreadPool() throws ExecutionException, InterruptedException {
        testServiceImpl.testThreadPool2();
    }

    @ApiOperation(value = "测试缓存--获取商品列表", notes = "测试缓存--获取商品列表")
    @PostMapping("cache")
    public Result<List<Goods>> getGoodsList(){
        List<Goods> list = testServiceImpl.getGoodsList();
        return Result.successToClient(list);
    }

    @ApiOperation(value = "测试缓存--修改商品列表", notes = "测试缓存--获取商品列表")
    @PostMapping("updateGoods")
    public Result updateGoods(){
        testServiceImpl.updateGoods();
        return Result.successToClient();
    }

    @ApiOperation(value = "渠道授权-测试简单工厂模式", notes = "渠道授权-测试简单工厂模式")
    @PostMapping("channel")
    public Result channel(@RequestParam String channel){
        channelFactory.getChannel(channel);
        return Result.successToClient();
    }

    @ApiOperation(value = "es创建索引", notes = "es创建索引")
    @PostMapping("/create")
    public boolean create(){
        return esService.createIndexOfArticle();
    }

    @ApiOperation(value = "es测试索引是否存在", notes = "es测试索引是否存在")
    @PostMapping("/existIndex")
    public boolean testExistIndex() throws IOException {
        return esService.testExistIndex();
    }

    @ApiOperation(value = "es添加数据", notes = "es添加数据")
    @PostMapping("/addEsGoods")
    public Result<IndexResponse> addEsGoods(@RequestBody EsGoods esGoods){
        return Result.successToClient(esService.addEsGoods(esGoods));
    }

    @ApiOperation(value = "es关键字查找", notes = "es关键字查找")
    @PostMapping("/queryByKey")
    public Result<List<EsGoods>> queryByKey(String keyword){
        return Result.successToClient(esService.queryByKey(keyword));
    }

    @ApiOperation(value = "es根据Id查找", notes = "es根据Id查找")
    @PostMapping("/queryById")
    public Result<EsGoods> queryById(String indexId){
        return Result.successToClient(esService.queryById(indexId));
    }

    @ApiOperation(value = "Redis的hash值初始化", notes = "Redis的hash值初始化")
    @PostMapping("/updateSave")
    public Result<EsGoods> updateSave(){
        Map<String, Object> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        redisUtil.hmset("hashKey", map);

        return Result.successToClient();
    }

    @ApiOperation(value = "Redis的hash值更新", notes = "Redis的hash值更新")
    @PostMapping("/updateHash")
    public Result<EsGoods> updateHash(){
        Map<String, Object> map = new HashMap<>();
        map.put("a", 3);
        map.put("b", 4);
        redisUtil.hmset("hashKey", map);

        return Result.successToClient();
    }

    @ApiOperation(value = "限流测试", notes = "限流测试")
    @PostMapping("/limit")
    @Limit(period = 60, count = 10, name = "testLimit", prefix = "limit", limitType = LimitType.IP)
    public Result<String> limit(){
        return Result.successToClient("测试成功");
    }

    @ApiOperation(value = "发送邮件", notes = "发送邮件")
    @PostMapping("/sendMailTest")
    public Result<Void> sendMailTest() {
        myMailServiceImpl.sendMail(
                "1196302555@qq.com",
                "1196302555@qq.com",
                "1196302555@qq.com",
                "SpringBoot发送邮件",
                "邮件发送成功啦!");
        return Result.successToClient();
    }

    @ApiOperation(value = "获取系统信息", notes = "获取系统信息")
    @PostMapping("/serverData")
    public Result<Server> serverData() throws Exception {
        Server server = new Server();
        server.copyTo();
        return Result.successToClient(server);
    }

}
