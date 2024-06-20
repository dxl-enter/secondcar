package com.dippy.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dippy.config.AlipayConfig;
import com.dippy.entity.SysOrder;
import com.dippy.mapper.SysOrderMapper;
import com.dippy.service.SysOrderService;
import com.dippy.util.PageUtils;
import com.dippy.util.Query;
import com.dippy.vo.AlipayVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//开启注解扫描配置文件
@EnableConfigurationProperties(AlipayConfig.class)
@Service/*("sysOrderService")*/
public class SysOrderServiceImpl extends ServiceImpl<SysOrderMapper, SysOrder> implements SysOrderService {

    @Autowired
    private AlipayConfig alipayConfig;

    @Autowired
    private SysOrderMapper sysOrderMapper;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysOrder> page = this.page(
                new Query<SysOrder>().getPage(params),
                new QueryWrapper<SysOrder>()
        );

        return new PageUtils(page);
    }

    /**
     * 下单
     *
     * @param sysOrder
     * @return
     */
    @Override
    public String createOrder(SysOrder sysOrder) {
        // 保存到数据库中
        sysOrderMapper.insert(sysOrder);
        AlipayClient alipayClient = new DefaultAlipayClient(
                alipayConfig.getGatewayUrl(),//支付宝网关
                alipayConfig.getApp_id(),//appid
                alipayConfig.getMerchant_private_key(),//商户私钥
                alipayConfig.getFormat(),
                alipayConfig.getCharset(),//字符编码格式
                alipayConfig.getAlipay_public_key(),//支付宝公钥
                alipayConfig.getSign_type());//签名方式
        //2、设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        //页面跳转同步通知页面路径
        alipayRequest.setReturnUrl(alipayConfig.getReturn_url());
        // 服务器异步通知页面路径
        alipayRequest.setNotifyUrl(alipayConfig.getNotify_url());
        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = sysOrder.getOrderNumber();
        //付款金额，必填
        String total_amount = String.valueOf(sysOrder.getCarPrice());
        //订单名称，必填
        String subject = "用户Id " + sysOrder.getBuyId() + "的订单";
        //商品描述，可空
        String body = "";
        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求
        String result = null;
        try {
            result = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        //输出
        System.out.println(result);
        return result;
    }


    /**
     * 【异步通知】
     **/
    @Override
    public String notifyUrl(HttpServletRequest request) {
        String payStatus = "fail";
        Map<String, String> parmas = new HashMap<>();
        Map<String, String[]> requestParmas = request.getParameterMap();

        for (Iterator<String> iter = requestParmas.keySet().iterator(); iter.hasNext(); ) {
            String name = iter.next();
            String[] values = requestParmas.get(name);
            String valStr = "";
            for (int i = 0; i < values.length; i++) {
                valStr = (i == values.length - 1) ? valStr + values[i] : valStr + values[i] + ",";
            }
            parmas.put(name, valStr);
            //System.out.println("【异步通知的值】" + name + "\t\t:" + valStr);
        }

        //签名验证
        boolean signVerified = false;

        try {
            signVerified = AlipaySignature.rsaCheckV1(parmas, alipayConfig.getAlipay_public_key(), alipayConfig.getCharset(), alipayConfig.getSign_type());
        } catch (AlipayApiException e) {
            System.out.println("【异步签名异常】" + e.getErrMsg());
        }
        //签名验证通过
        if (signVerified) {
            System.out.println("【异步通知签名验证】" + signVerified);
            String trade_status = request.getParameter("trade_status");//交易状态
            String out_trade_no = request.getParameter("out_trade_no");//商户订单号
            //System.out.println("【异步通知商户订单号】" + out_trade_no + "\t\t【异步通知交易状态】" + trade_status);
            if (trade_status.equals("TRADE_SUCCESS")) {  //判断订单交易状态
                payStatus = out_trade_no;
            }
        }
        return payStatus;
    }

    /**
     * 【同步跳转】
     **/
    @Override
    public AlipayVo returnUrl(HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //System.out.println("<--同步回调的值-->" + name + "\t\t" + valueStr);
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        boolean signVerified = false;
        try {
            signVerified = AlipaySignature.rsaCheckV1(params, alipayConfig.getAlipay_public_key(), alipayConfig.getCharset(), alipayConfig.getSign_type());
        } catch (Exception e) {
            System.out.println("报错：" + e.getMessage());
            e.printStackTrace();
        }
        AlipayVo alipayVo = new AlipayVo();
        if (signVerified) {

            System.out.println("<--同步回调签名验证-->" + signVerified);
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");
            alipayVo.setOut_trade_no(out_trade_no);
            alipayVo.setSubject(trade_no);
            alipayVo.setTotal_amount(total_amount);
            // 返回的数据
            request.setAttribute("out_trade_no", out_trade_no);
            request.setAttribute("trade_no", trade_no);
            request.setAttribute("total_amount", total_amount);
            //System.out.println("<--同步回调系统订单号-->" + out_trade_no + "\t\t<--同步回调支付宝交易号-->" + trade_no);
            //系统处理根据支付宝回调更改订单状态或者其他关联表的数据
        } else {
            request.setAttribute("reason", "验签失败");
        }
        request.setAttribute("signVerified", signVerified);
        return alipayVo;
    }

    /**
     * 【交易查询】
     **/
    @Override
    public String queryOrder(String orderNumber) throws AlipayApiException {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(
                alipayConfig.getGatewayUrl(),//支付宝网关
                alipayConfig.getApp_id(),//appid
                alipayConfig.getMerchant_private_key(),//商户私钥
                alipayConfig.getCharset(),//字符编码格式
                alipayConfig.getAlipay_public_key(),//支付宝公钥
                alipayConfig.getSign_type());//签名方式

        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent("{" +
                "\"out_trade_no\":\"" + orderNumber + "\"," +
                "\"trade_no\":\"" + "" + "\"," +
                "\"org_pid\":\"" + "" + "\"," +
                "      \"query_options\":[" +
                "        \"TRADE_SETTLE_INFO\"" +
                "      ]" +
                "  }");
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        System.out.println("【查询返回交易状态】" + response.getTradeStatus());
        return response.getTradeStatus();
    }

    @Override
    public String toAlipay(AlipayVo alipayVo) {

            AlipayClient alipayClient = new DefaultAlipayClient(
                    alipayConfig.getGatewayUrl(),//支付宝网关
                    alipayConfig.getApp_id(),//appid
                    alipayConfig.getMerchant_private_key(),//商户私钥
                    alipayConfig.getFormat(),
                    alipayConfig.getCharset(),//字符编码格式
                    alipayConfig.getAlipay_public_key(),//支付宝公钥
                    alipayConfig.getSign_type());//签名方式
            //2、设置请求参数
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
            //页面跳转同步通知页面路径
            alipayRequest.setReturnUrl(alipayConfig.getReturn_url());
            // 服务器异步通知页面路径
            alipayRequest.setNotifyUrl(alipayConfig.getNotify_url());
            //封装参数
            alipayRequest.setBizContent(JSON.toJSONString(alipayVo));
            String result="";
            try {
                //3、请求支付宝进行付款，并获取支付结果
                result  = alipayClient.pageExecute(alipayRequest).getBody();
            } catch (AlipayApiException e) {
                e.printStackTrace();
            }
            //返回付款信息
            return result;

    }

}
