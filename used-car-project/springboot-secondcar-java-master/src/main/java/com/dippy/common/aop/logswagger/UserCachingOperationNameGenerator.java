package com.dippy.common.aop.logswagger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class UserCachingOperationNameGenerator {
    private Map<String, Integer> generated = new HashMap();
    private static Logger log = LoggerFactory.getLogger(UserCachingOperationNameGenerator.class);
    @Pointcut("execution(* springfox.documentation.spring.web.readers.operation.CachingOperationNameGenerator.startingWith(String))")
    public void c() {
    }


    @Around("c()")
    public Object a(ProceedingJoinPoint point) {
        Object[] args = point.getArgs();
        return startingWith(String.valueOf(args[0]));
    }

    private String startingWith(String prefix) {
        if (generated.containsKey(prefix)) {
            generated.put(prefix, generated.get(prefix) + 1);
            String nextUniqueOperationName = String.format("%s_%s", prefix, generated.get(prefix));
            if (log.isDebugEnabled()){
                log.warn("组件中存在相同的方法名称，自动生成组件方法唯一名称进行替换: {}", nextUniqueOperationName);
            }
            return nextUniqueOperationName;
        } else {
            generated.put(prefix, 0);
            return prefix;
        }
    }
}

