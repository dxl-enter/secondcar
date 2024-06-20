package com.dippy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author Mindless
 */
@Component
public class MyExtRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(MyExtRunner.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 在这里添加你想要打印的额外信息
        // Knife4j接口文档  http://localhost:8090/api/doc.html#/home  -- 首页
        //  swagger http://localhost:8090/api/swagger-ui.html   注意有个api/
        logger.info("Knife4j接口文档地址：{}", "http://localhost:8090/api/doc.html#/home");
        logger.info("swagger接口文档地址：{}", "http://localhost:8090/api/swagger-ui.html   注意有个api");
    }
}
