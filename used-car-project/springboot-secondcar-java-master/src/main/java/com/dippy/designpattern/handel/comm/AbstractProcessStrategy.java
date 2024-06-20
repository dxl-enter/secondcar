package com.dippy.designpattern.handel.comm;

import com.dippy.designpattern.handel.ProcessInterface;

import java.util.Map;

/**
 * 设计模式
 * 使用抽象类来实现策略模式：
 * 前置处理 -> 业务处理 -> 后置处理
 */
public abstract class AbstractProcessStrategy implements ProcessInterface {

    /**
     * 公共执行顺序处理
     *  如果调用顺序不适用时，请在子类中重载该方法
     * @param data
     */
    public void doProcess(Map<String, Object> data) {
        System.out.println("默认公共执行顺序处理: before -> business -> after");
        beforeBusiness(data);
        doBusiness(data);
        afterBusiness(data);
    }

    /**
     * 默认的前置处理逻辑
     * @param data
     */
    @Override
    public void beforeBusiness(Map<String, Object> data) {
        // 默认的前置处理逻辑
        System.out.println("默认公共的前置处理逻辑");
    }

    /**
     * 具体各个业务的具体处理
     *  各个子类都要实现
     * @param data
     */
    @Override
    public void doBusiness(Map<String, Object> data) {
        // 公共的逻辑
        System.out.println("公共的逻辑");
    }

    /**
     * 默认的后置处理逻辑
     * @param data
     */
    @Override
    public void afterBusiness(Map<String, Object> data) {
        // 默认的后置处理逻辑
        System.out.println("默认公共的后置处理逻辑");
    }
}
