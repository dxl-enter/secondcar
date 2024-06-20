package com.dippy.designpattern.strategy;

import java.util.Map;


/**
 * 设计模式
 * 策略模式 子类重写则执行子类的方法。子类也可以定义执行顺序
 * @author Mindless
 * @date 2024年3月3日
 */
public abstract class AbstractStrategy implements StrategyInterface{

    /**
     * 业务执行前的模板方法
     *
     * @param data
     */
    protected final void beforeBusinMapTemplate(Map<String, Object> data) {
        System.out.println("Parent before business logic beforeBusinMapTemplate");
        beforeBusinMap(data);
    }

    /**
     * 子类可以重写的业务执行前的方法
     *
     * @param data
     */
    public void beforeBusinMap(Map<String, Object> data) {
        System.out.println("Parent beforeBusinMap");
        // 默认为空，子类可以选择性重写
    }

    /**
     * 业务执行的模板方法
     *
     * @param data
     */
    protected final void doBusinTemplate(Map<String, Object> data) {
        System.out.println("Parent doing business logic doBusinTemplate");
        doBusin(data);
    }

    /**
     * 子类可以重写的业务执行方法
     *
     * @param data
     */
    @Override
    public void doBusin(Map<String, Object> data) {
        System.out.println("Parent doBusin");
        // 默认为空，子类可以选择性重写
    }

    /**
     * 业务执行后的模板方法
     *
     * @param data
     */
    protected final void afterBusinTemplate(Map<String, Object> data) {
        System.out.println("Parent after business logic afterBusinTemplate");
        afterBusin(data);
    }

    /**
     * 子类可以重写的业务执行后的方法
     *
     * @param data
     */
    public void afterBusin(Map<String, Object> data) {
        // 默认为空，子类可以选择性重写
        System.out.println("Parent afterBusin");
    }

    /**
     * 控制业务执行顺序的方法
     *
     * @param data
     */
    public void businOrder(Map<String, Object> data) {
        System.out.println("========= Parent businOrder  start =========");
        beforeBusinMapTemplate(data);
        doBusinTemplate(data);
        afterBusinTemplate(data);
        System.out.println("========= Parent businOrder  end   ========= ");
    }

}
