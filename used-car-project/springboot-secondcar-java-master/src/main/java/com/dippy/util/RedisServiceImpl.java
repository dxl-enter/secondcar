package com.dippy.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Transactional
@Service("redisService")
public class RedisServiceImpl /*implements RedisService*/ {

    //导入数据源
    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;


    /**
     * 新增一条该userid用户在搜索栏的历史记录
     * searchkey 代表输入的关键词
     *
     * @param userid
     * @param searchkey
     * @return
     */
    public int addSearchHistoryByUserId(String userid, String searchkey) {
        String shistory = RedisKeyUtils.getSearchHistoryKey(userid);
        boolean b = redisTemplate.hasKey(shistory);
        if (b) {
            Object hk = redisTemplate.opsForHash().get(shistory, searchkey);
            if (hk != null) {
                return 1;
            } else {
                redisTemplate.opsForHash().put(shistory, searchkey, "1");
            }
        } else {
            redisTemplate.opsForHash().put(shistory, searchkey, "1");
        }
        return 1;
    }

    /**
     * 删除个人历史数据
     *
     * @param userid
     * @param searchkey
     * @return
     */
    public Long delSearchHistoryByUserId(String userid, String searchkey) {
        String shistory = RedisKeyUtils.getSearchHistoryKey(userid);
        return redisTemplate.opsForHash().delete(shistory, searchkey);
    }

    /**
     * 获取个人历史数据列表
     *
     * @param userid
     * @return
     */
    public List<String> getSearchHistoryByUserId(String userid) {
        List<String> stringList = null;
        String shistory = RedisKeyUtils.getSearchHistoryKey(userid);
        boolean b = redisTemplate.hasKey(shistory);
        if (b) {
            Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(shistory, ScanOptions.NONE);
            while (cursor.hasNext()) {
                Map.Entry<Object, Object> map = cursor.next();
                String key = map.getKey().toString();
                stringList.add(key);
            }
            return stringList;
        }
        return null;
    }

    /**
     * 新增一条热词搜索记录，将用户输入的热词存储下来
     *
     * @param searchkey
     * @return
     */
    public int incrementScoreByUserId(String searchkey) {
        Long now = System.currentTimeMillis();
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        List<String> title = new ArrayList<>();
        title.add(searchkey);
        for (int i = 0, lengh = title.size(); i < lengh; i++) {
            String tle = title.get(i);
            try {
                if (zSetOperations.score("title", tle) <= 0) {
                    zSetOperations.add("title", tle, 0);
                    valueOperations.set(tle, String.valueOf(now));
                }
            } catch (Exception e) {
                zSetOperations.add("title", tle, 0);
                valueOperations.set(tle, String.valueOf(now));
            }
        }
        return 1;
    }

    /**
     * 根据searchkey搜索其相关最热的前十名 (如果searchkey为null空，则返回redis存储的前十最热词条)
     *
     * @param searchkey
     * @return
     */
    public List<String> getHotList(String searchkey) {
        String key = searchkey;
        Long now = System.currentTimeMillis();
        List<String> result = new ArrayList<>();
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Set<String> value = zSetOperations.reverseRangeByScore("title", 0, Double.MAX_VALUE);
        //key不为空的时候 推荐相关的最热前十名
        if (StringUtils.isNotEmpty(searchkey)) {
            for (String val : value) {
                if (StringUtils.containsIgnoreCase(val, key)) {
                    //只返回最热的前十名
                    if (result.size() > 9) {
                        break;
                    }
                    Long time = Long.valueOf(valueOperations.get(val));
                    //返回最近一个月的数据
                    if ((now - time) < 2592000000L) {
                        result.add(val);
                    } else {//时间超过一个月没搜索就把这个词热度归0
                        zSetOperations.add("title", val, 0);
                    }
                }
            }
        } else {
            for (String val : value) {
                if (result.size() > 9) {
                    // 只返回最热的前十名
                    break;
                }
                Long time = Long.valueOf(valueOperations.get(val));
                //返回最近一个月的数据
                if ((now - time) < 2592000000L) {
                    result.add(val);
                } else {//时间超过一个月没搜索就把这个词热度归0
                    zSetOperations.add("title", val, 0);
                }
            }
        }
        return result;
    }

    /**
     * 每次点击给相关词searchkey热度 +1
     *
     * @param searchkey
     * @return
     */
    public int incrementScore(String searchkey) {
        String key = searchkey;
        Long now = System.currentTimeMillis();
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        zSetOperations.incrementScore("title", key, 1);
        valueOperations.getAndSet(key, String.valueOf(now));
        return 1;
    }
}
