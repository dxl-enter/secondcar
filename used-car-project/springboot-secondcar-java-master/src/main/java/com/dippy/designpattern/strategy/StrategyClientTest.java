package com.dippy.designpattern.strategy;

import com.dippy.designpattern.strategy.impl.AConcreteAbstractStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * 具体调用测试
 */
public class StrategyClientTest {
    public static void main(String[] args) {
        Map<String, Object> data = new HashMap<>();

        AbstractStrategy concreteAbstractStrategy = new AConcreteAbstractStrategy();
        concreteAbstractStrategy.businOrder(data);

    }
}
