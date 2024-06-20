package com.dippy.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * redis的使用
 */
@SpringBootTest
public class TestRedisTemplate {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Test
    public void testRedisAll() {
        this.testRedisString();
        this.testRedisList();
        this.testRedisHash();
        this.testRedisSet();
        this.testRedisZSet();
    }

    /**
     * String
     */
    @Test
    public void testRedisString() {
        String key = "userName";
        redisTemplate.opsForValue().set(key, "用户名");
        System.out.println(redisTemplate.opsForValue().get("userName").toString());

        // 设置过期时间 milliseconds毫秒，microseconds 微秒
        redisTemplate.opsForValue().set("userName:OnTime", "用户名：1", 5000, TimeUnit.MILLISECONDS);
        System.out.println(redisTemplate.opsForValue().get("userName:OnTime"));

        // getAndSet
        String oldValue = (String) redisTemplate.opsForValue().getAndSet(key, "用户名getAndSet");
        String newValue = (String) redisTemplate.opsForValue().get(key);
        System.out.println("旧值：" + oldValue);
        System.out.println("新值：" + newValue);

    }

    /**
     * list
     */
    @Test
    public void testRedisList() {
        String key = "list";

        String[] list1 = new String[]{"1", "list1", "user1"};
        String[] list2 = new String[]{"2", "list2", "user22"};
        String[] list3 = new String[]{"3", "list3", "user333"};
        String[] list4 = new String[]{"4", "list4", "user4444"};
        String[] listSet = new String[]{"Set", "listSet", "userSet"};

        // redisTemplate.opsForList().leftPush(key, (list1));
        // redisTemplate.opsForList().leftPush(key, (list2));
        // redisTemplate.opsForList().rightPush(key, (list3));
        redisTemplate.opsForList().leftPushAll(key, list1, list2, list3);
        redisTemplate.opsForList().leftPush(key, (list4));


        // 单独设置 -- 将第一个修改
        redisTemplate.opsForList().set(key, 0, listSet);


        /**
         * remove
         * count=0：删除等于value的所有元素。
         * count>0：删除等于从头到尾移动的值的元素。
         * count<0：删除等于从尾到头移动的值的元素
         */
        // 从存储在键中的列表，删除给定“count”值的元素的第1个计数事件  删除列表中第3次出现的值
        redisTemplate.opsForList().remove(key, 3, list3);

        // 长度
        Long listSize = redisTemplate.opsForList().size(key);

        // 过期时间
        redisTemplate.expire(key, 10000, TimeUnit.MILLISECONDS);

        System.out.println(listSize);
        System.out.println(redisTemplate.opsForList().index(key, 0));
        System.out.println(redisTemplate.opsForList().range(key, 0, -1));

    }

    /**
     * Hash
     */
    @Test
    public void testRedisHash() {
        String key = "hash";

        HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("username", "用户名");
        userMap.put("password", "密码");
        opsForHash.putAll(key, userMap);
        System.out.println(opsForHash.get(key, "username"));
        System.out.println(opsForHash.values(key));

        System.out.println();

        // 键重名则会替换
        redisTemplate.opsForHash().put(key, "username", "username");

        redisTemplate.opsForHash().put(key, "blogUrl", "blogUrl");
        redisTemplate.opsForHash().put(key, "blogRemark", "blogRemark");

        // 设置过期时间 10s
        redisTemplate.boundHashOps(key).expire(10000, TimeUnit.MILLISECONDS);

        System.out.println(redisTemplate.opsForHash().hasKey(key, "username"));
        System.out.println(redisTemplate.opsForHash().hasKey(key, "age"));
        System.out.println(redisTemplate.opsForHash().size(key));

    }

    /**
     * Set
     */
    @Test
    public void testRedisSet() {
        String key = "set";

        String[] city1 = new String[]{"北京1", "上海1", "广州1", "深圳1"};
        String[] city2 = new String[]{"北京2", "上海2", "广州2", "深圳2"};

        redisTemplate.opsForSet().add(key, city1, city2);
        System.out.println(redisTemplate.opsForSet().members(key));

        // redisTemplate.opsForSet().
        BoundSetOperations<String, Object> boundSetOps = redisTemplate.boundSetOps(key);

        // 设置过期时间 10s
        redisTemplate.expire(key, 10000, TimeUnit.MILLISECONDS);
        boundSetOps.expire(10000, TimeUnit.MILLISECONDS);
        System.out.println(boundSetOps.union(key));
    }

    /**
     * ZSet
     */
    @Test
    public void testRedisZSet() {
        String key = "zSet";

        String[] city1 = new String[]{"北京1", "上海1", "广州1", "深圳1"};
        String[] city2 = new String[]{"北京2", "上海2", "广州2", "深圳2"};

        redisTemplate.opsForZSet().add(key, city1, 1);
        BoundZSetOperations<String, Object> boundZSetOps = redisTemplate.boundZSetOps(key);

        // 设置过期时间  10s
        boundZSetOps.expire(10000, TimeUnit.MILLISECONDS);
        System.out.println(redisTemplate.opsForZSet().range(key, 0, -1));
    }

}
