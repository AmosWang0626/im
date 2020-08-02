package com.amos.im.common.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * PROJECT: 由静态方法构成的Redis工具类
 *
 * @author amos.wang
 * @date 2018/8/30
 */
public class RedisUtil {

    private static JedisPool jedisPool;

    public static void setPool(JedisPool jedisPool) {
        RedisUtil.jedisPool = jedisPool;
    }

    private static JedisPool getPool() {
        return jedisPool;
    }

    /*
     * string
     */

    public static void set(String key, String value) {
        set(key, value, null, null);
    }

    public static void set(String key, String value, Integer second) {
        set(key, value, null, second);
    }

    public static void setByIndex(String key, String value, Integer index) {
        set(key, value, index, null);
    }

    public static void set(String key, String value, Integer index, Integer second) {
        Jedis jedis = getPool().getResource();
        Optional.ofNullable(index).ifPresent(jedis::select);

        jedis.set(key, value);

        // 过期时间
        if (second != null && second != 0) {
            jedis.expire(key, second);
        }

        jedis.close();
    }

    public static String get(String key) {
        return get(key, null);
    }

    public static String get(String key, Integer index) {
        try (Jedis jedis = getPool().getResource()) {
            Optional.ofNullable(index).ifPresent(jedis::select);

            return jedis.get(key);
        }
    }

    public static Long incr(String key) {
        return incr(key, null, null);
    }

    public static Long incr(String key, Integer second) {
        return incr(key, null, second);
    }

    public static Long incrByIndex(String key, Integer index) {
        return incr(key, index, null);
    }

    public static Long incr(String key, Integer index, Integer second) {
        Jedis jedis = getPool().getResource();
        Optional.ofNullable(index).ifPresent(jedis::select);

        Long count = jedis.incr(key);

        // 过期时间
        if (second != null && second != 0) {
            jedis.expire(key, second);
        }

        jedis.close();

        return count;
    }

    /*
     * key
     */

    public static void del(String key) {
        del(key, null);
    }

    public static void del(String key, Integer index) {
        try (Jedis jedis = getPool().getResource()) {
            Optional.ofNullable(index).ifPresent(jedis::select);

            jedis.del(key);
        }
    }

    /*
     * list
     */

    /**
     * 最后插入的在列表第一个位置
     */
    public static Long lpush(String key, String... strings) {
        return lpush(key, null, strings);
    }

    /**
     * 最后插入的在列表第一个位置
     */
    public static Long lpush(String key, Integer index, String... strings) {
        try (Jedis jedis = getPool().getResource()) {
            Optional.ofNullable(index).ifPresent(jedis::select);

            return jedis.lpush(key, strings);
        }
    }

    /**
     * 覆盖列表第一个元素
     */
    public static Long lpushx(String key, String... strings) {

        return lpushx(key, null, strings);
    }

    /**
     * 覆盖列表第一个元素
     */
    public static Long lpushx(String key, Integer index, String... strings) {
        try (Jedis jedis = getPool().getResource()) {
            Optional.ofNullable(index).ifPresent(jedis::select);

            return jedis.lpushx(key, strings);
        }
    }

    public static List<String> lrange(String key, long start, long end) {
        return lrange(key, null, start, end);
    }

    public static List<String> lrange(String key, Integer index, long start, long end) {
        try (Jedis jedis = getPool().getResource()) {
            Optional.ofNullable(index).ifPresent(jedis::select);

            return jedis.lrange(key, start, end);
        }
    }

    /*
     * hash
     */

    public static boolean hsetnx(String key, String field, String value) {
        return hsetnx(key, field, value, null);
    }

    public static boolean hsetnx(String key, String field, String value, Integer index) {
        try (Jedis jedis = getPool().getResource()) {
            Optional.ofNullable(index).ifPresent(jedis::select);

            Long hSetNx = jedis.hsetnx(key, field, value);
            return hSetNx.equals(1L);
        }
    }

    public static boolean hset(String key, String field, String value) {
        return hsetnx(key, field, value, null);
    }

    public static boolean hset(String key, String field, String value, Integer index) {
        try (Jedis jedis = getPool().getResource()) {
            Optional.ofNullable(index).ifPresent(jedis::select);

            Long hSetNx = jedis.hset(key, field, value);
            return hSetNx.equals(1L);
        }
    }

    public static String hmset(String key, Map<String, String> map) {
        return hmset(key, map, null);
    }

    public static String hmset(String key, Map<String, String> map, Integer index) {
        try (Jedis jedis = getPool().getResource()) {
            Optional.ofNullable(index).ifPresent(jedis::select);

            return jedis.hmset(key, map);
        }
    }

    public static Long hdel(String key, String field) {
        return hdel(key, field, null);
    }

    public static Long hdel(String key, String field, Integer index) {
        try (Jedis jedis = getPool().getResource()) {
            Optional.ofNullable(index).ifPresent(jedis::select);

            return jedis.hdel(key, field);
        }
    }

    public static Boolean hexists(String key, String field) {
        return hexists(key, field, null);
    }

    public static Boolean hexists(String key, String field, Integer index) {
        try (Jedis jedis = getPool().getResource()) {
            Optional.ofNullable(index).ifPresent(jedis::select);

            return jedis.hexists(key, field);
        }
    }

    public static String hget(String key, String field) {
        return hget(key, field, null);
    }

    public static String hget(String key, String field, Integer index) {
        try (Jedis jedis = getPool().getResource()) {
            Optional.ofNullable(index).ifPresent(jedis::select);

            return jedis.hget(key, field);
        }
    }

    /*
     * set
     */

    public static Long sadd(String key, String... strings) {
        return sadd(key, null, strings);
    }

    public static Long sadd(String key, Integer index, String... strings) {
        try (Jedis jedis = getPool().getResource()) {
            Optional.ofNullable(index).ifPresent(jedis::select);

            return jedis.sadd(key, strings);
        }
    }

    public static Set<String> smembers(String key, Integer index) {
        try (Jedis jedis = getPool().getResource()) {
            Optional.ofNullable(index).ifPresent(jedis::select);

            return jedis.smembers(key);
        }
    }

    /*
     * sort
     */

    public static Long zadd(String key, double score, String member, Integer index) {
        try (Jedis jedis = getPool().getResource()) {
            Optional.ofNullable(index).ifPresent(jedis::select);

            return jedis.zadd(key, score, member);
        }
    }

    public static Long zadd(String key, Map<String, Double> scoreMembers, Integer index) {
        try (Jedis jedis = getPool().getResource()) {
            Optional.ofNullable(index).ifPresent(jedis::select);

            return jedis.zadd(key, scoreMembers);
        }
    }

    public static Set<String> zrange(String key, Integer index, long start, long end) {
        try (Jedis jedis = getPool().getResource()) {
            Optional.ofNullable(index).ifPresent(jedis::select);

            return jedis.zrange(key, start, end);
        }
    }

    public static void zrem(String key, Integer index, String... member) {
        try (Jedis jedis = getPool().getResource()) {
            Optional.ofNullable(index).ifPresent(jedis::select);

            jedis.zrem(key, member);
        }
    }

}
