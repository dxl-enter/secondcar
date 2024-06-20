package com.dippy.designpattern.strategy.impl;

import com.dippy.designpattern.strategy.AbstractStrategy;

import java.util.Map;

/**
 * @author Mindless
 */
public class AConcreteAbstractStrategy extends AbstractStrategy {

    /**
     * 子类可以重写的业务执行前的方法
     * @param data
     */
    @Override
    public void beforeBusinMap(Map<String, Object> data) {
        super.beforeBusinMap(data);
        // 子类重写的业务执行逻辑
        System.out.println(" A Child beforeBusinMap");
    }

    /**
     * 子类可以重写的业务执行的方法
     * @param data
     */
    @Override
    public void doBusin(Map<String, Object> data) {
        super.doBusin(data);
        // 子类重写的业务执行逻辑
        System.out.println(" A Child business");
    }

    /**
     * 子类可以重写的业务执行后的方法
     * @param data
     */
    @Override
    public void afterBusin(Map<String, Object> data) {
        //super.afterBusin(data);
        // 默认为空，子类可以选择性重写
        System.out.println(" A Child afterBusin");
    }

    /**
     * 子类重写的控制业务执行顺序的方法
     * @param data
     */
    /*@Override
    // 控制业务执行顺序的方法
    public void businOrder(Map<String, Object> data) {
        System.out.println("========= A Child  businOrder start =========");
        beforeBusinMapTemplate(data);
        doBusinTemplate(data);
        afterBusinTemplate(data);
        System.out.println("========= A Child businOrder  end   ========= ");
    }*/
}
