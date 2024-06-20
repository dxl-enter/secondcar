package com.dippy.designpattern.handel.comm.impl;

import com.dippy.designpattern.handel.comm.AbstractProcessStrategy;

import java.util.Map;

/**
 * B子类
 *
 * @author Mindless
 */
public class BConcreteProcessStrategyImpl extends AbstractProcessStrategy {
    @Override
    public void afterBusiness(Map<String, Object> data) {
        super.afterBusiness(data);
        // 具体的业务处理逻辑
    }
}