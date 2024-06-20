package com.dippy.service;

import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dippy.entity.SysOrder;
import com.dippy.util.PageUtils;
import com.dippy.vo.AlipayVo;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 *
 *
 * @author dippy
 * @date 2021-05-15 00:14:02
 */
public interface SysOrderService extends IService<SysOrder> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 下单
     * @param sysOrder
     * @return
     */
    String createOrder(SysOrder sysOrder);

    // *【异步通知会返回一个request】*
     String notifyUrl(HttpServletRequest request);

    // *【同步跳转】*
     AlipayVo returnUrl(HttpServletRequest request) throws UnsupportedEncodingException;

    // *【交易查询】*
     String queryOrder(String orderNumber) throws AlipayApiException;

    String toAlipay(AlipayVo alipayVo);
}

