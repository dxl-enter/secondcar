package com.dippy.designpattern.handel;

import java.util.Map;

/**
 * 策略模式
 *  公共接口
 * @author Mindless
 */
public interface ProcessInterface {
    /**
     * 前置处理逻辑
     * @param data
     */
    void beforeBusiness(Map<String, Object> data);

    /**
     * 具体各个业务的具体处理
     * @param data
     */
    void doBusiness(Map<String, Object> data);

    /**
     * 后置处理逻辑
     * @param data
     */
    void afterBusiness(Map<String, Object> data);
}
