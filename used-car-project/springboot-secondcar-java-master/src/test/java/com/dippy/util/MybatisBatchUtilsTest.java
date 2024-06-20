package com.dippy.util;

import com.dippy.entity.UserInfo;
import com.dippy.mapper.UserInfoMapper;
import com.dippy.service.UserInfoService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
class MybatisBatchUtilsTest {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserInfoMapper userInfoMapper;


    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Test
    void batchUpdateOrInsert() {
        long start = System.currentTimeMillis();
        List<UserInfo> list = new ArrayList<>();

        UserInfo user;
        for (int i = 1; i <= 500000; i++) {
            user = new UserInfo();
            user.setName("java" + i);
            user.setUsername("username" + i);
            user.setPassword("password" + i);

            list.add(user);
        }
        System.out.println("拼装数据 耗时：" + (System.currentTimeMillis() - start));
        System.out.println(list.size());

        MybatisBatchUtils mybatisBatchUtils = new MybatisBatchUtils();
        System.out.println("sql 插入开始========");
        long start1 = System.currentTimeMillis();
        boolean b = userInfoService.saveBatch(list);// 5W条记录耗时 12121ms  50W 条记录耗时109673ms
        // mybatisBatchUtils.batchUpdateOrInsert(list,UserInfoMapper.class,UserInfoMapper::insertUserInfo );

        BatchInsertUtil.batchInsert(list, UserInfoMapper.class, UserInfoMapper::insertUserInfo);
        // userInfoService.sqlInsert(list);
        System.out.println("sql 插入耗时：" + (System.currentTimeMillis() - start1));

    }


    @Test
    void insertBatchUserOne() {
        Instant start = Instant.now();
        // 获取sqlSession 设置插入方式为批处理BATCH
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        UserInfoMapper mapper = sqlSession.getMapper(UserInfoMapper.class);
        // sqlSession.insert()
        try {
            int count = 500000; // 批量插入50W 费时1479698毫秒
            for (int i = 0; i < count; i++) {
                UserInfo user = new UserInfo();
                user.setCreateTime(new Date());
                user.setUsername("username" + i);
                user.setPassword("password" + i);
                mapper.insertUserInfo(user);
                if (i % 1000 == 0 || i == count - 1) {
                    sqlSession.commit();
                    sqlSession.clearCache();
                }
            }
        } catch (Exception exception) {
            System.out.println(exception);
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
        System.out.println("***********************batch总共用时：" + ChronoUnit.MILLIS.between(start, Instant.now()) + "毫秒***********************************");
    }
}

