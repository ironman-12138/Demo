package org.example.utils;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisUtil {

    //1. 创建连接池配置信息
    public static GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
    public static JedisPool pool = null;
    static {
        poolConfig.setMaxTotal(100);  // 连接池中最大的活跃数
        poolConfig.setMaxIdle(10);   // 最大空闲数
        poolConfig.setMinIdle(5);   // 最小空闲数
        poolConfig.setMaxWaitMillis(3000);  // 当连接池空了之后,多久没获取到Jedis对象,就超时
        pool = new JedisPool(poolConfig,"47.96.100.109",6379);
    }

    public static Jedis getJedis() {
        return pool.getResource();
    }
}
