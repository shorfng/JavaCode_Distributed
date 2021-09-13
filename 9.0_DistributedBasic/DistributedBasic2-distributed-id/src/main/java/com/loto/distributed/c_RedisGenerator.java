package com.loto.distributed;

import redis.clients.jedis.Jedis;

public class c_RedisGenerator {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.137.145",6379);
        Long id = jedis.incr("id");//<id,0>
        System.out.println(id);
    }
}
