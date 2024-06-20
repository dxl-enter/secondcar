package com.dippy.designpattern.strategy.impl;

import com.dippy.designpattern.strategy.AbstractStrategy;

import java.util.Map;

/**
 * @author Mindless
 */
public class BConcreteAbstractStrategy extends AbstractStrategy {

    @Override
    public void doBusin(Map<String, Object> data) {
        // 子类重写的业务执行逻辑
        System.out.println(" B Child doing business logic");
    }
}
