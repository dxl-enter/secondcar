package com.dippy.designpattern.handel.comm.impl;

import com.dippy.designpattern.handel.comm.AbstractProcessStrategy;

import java.util.Map;

/**
 * 子类A 重写执行顺序
 * @author Mindless
 */
public class AConcreteProcessStrategyImpl extends AbstractProcessStrategy {
    @Override
    public void doBusiness(Map<String, Object> data) {
        // A 先调用父类的business
        super.doBusiness(data);
        // 具体的业务处理逻辑
        System.out.println("执行了A 个性化的business");
    }

    @Override
    public void doProcess(Map<String, Object> data) {
        System.out.println("调用了A的个性化处理顺序逻辑 ： business => before => after");
        doBusiness(data);
        beforeBusiness(data);
        afterBusiness(data);
    }
}


