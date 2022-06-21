package org.example.leetcode;

import org.example.domin.EsGoods;
import org.example.domin.Goods;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Test {

    public static void main(String[] args) {
        //测试数据，请不要纠结数据的严谨性
//        List<EsGoods> goodsList = new ArrayList<>();
//        goodsList.add(new EsGoods("李小明", "1003.2"));
//        goodsList.add(new EsGoods("李小明", "1003.4"));
//        goodsList.add(new EsGoods("李小明", "1001"));
//        goodsList.add(new EsGoods("李小明", "1002"));
//        List<EsGoods> goodsSortName = goodsList.stream().sorted(Comparator.comparing(EsGoods::getGoodsId)).collect(Collectors.toList());
//        System.out.println(goodsSortName);
//
//        System.out.println(100%10);
//        System.out.println(101%10);
//        System.out.println(102%10);


//        System.out.println(String.format("%06d", 1));
//        System.out.println(String.format("%06d", 10));
//        System.out.println(String.format("%06d", 300));
//        System.out.println(String.format("%06d", 600000));
//        System.out.println(String.format("%06d", 6000000));
        System.out.println(new SimpleDateFormat("yyMMdd").format(new Date()));
    }

}
