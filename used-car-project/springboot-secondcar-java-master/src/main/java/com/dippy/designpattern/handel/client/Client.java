package com.dippy.designpattern.handel.client;

import com.dippy.designpattern.handel.comm.AbstractProcessStrategy;
import com.dippy.designpattern.handel.comm.impl.AConcreteProcessStrategyImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * 具体调用测试
 */
public class Client {
    public static void main(String[] args) {
        Map<String, Object> data = new HashMap<>();

        AbstractProcessStrategy strategy = new AConcreteProcessStrategyImpl();
        //  TODO: 2024/3/3 可以改成方法中传入具体实现类然后调用对应的方法。结合@PostConstruct这样可以加到IOC中
        strategy.doProcess(data);
    }
}
