package com.dippy.controller;

import com.alipay.api.AlipayApiException;
import com.dippy.common.result.Result;
import com.dippy.entity.SysOrder;
import com.dippy.util.OrderUtils;
import com.dippy.util.PageUtils;
import com.dippy.vo.AlipayVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
// import com.dippy.common.utils.PageUtils;

/**
 * @author dippy
 * @date 2021-05-15 00:14:02
 */
@Controller
@RequestMapping("/sysOrder")
@Api(value = "订单接口")
public class SysOrderController extends BaseController {

    /**
     * 列表
     */
    @ApiOperation(value = "查看所有")
    @PostMapping("/list")
    // @ApiImplicitParam(name = "type", value = "地址类型，人还是车的", dataType = "String",paramType = "query")
    // @RequiresPermissions("yueChi:sysorder:list")
    public Result list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysOrderService.queryPage(params);

        return Result.succ("操作成功！", page);
    }


    /**
     * 信息
     */
    @ApiOperation(value = "查询单个信息")
    @GetMapping("/info/{orderId}")
    // @RequiresPermissions("yueChi:sysorder:info")
    public Result info(@PathVariable("orderId") Integer orderId) {
        SysOrder sysOrder = sysOrderService.getById(orderId);

        return Result.succ("操作成功！", sysOrder);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存")
    @PostMapping("/save")
    // @RequiresPermissions("yueChi:sysorder:save")
    public Result save(@RequestBody SysOrder sysOrder) {
        sysOrderService.save(sysOrder);

        return Result.succ("保存成功！");
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改")
    @PostMapping("/update")
    // @RequiresPermissions("yueChi:sysorder:update")
    public Result update(@RequestBody SysOrder sysOrder) {
        sysOrderService.updateById(sysOrder);

        return Result.succ("修改成功！");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @PostMapping("/delete")
    // @RequiresPermissions("yueChi:sysorder:delete")
    public Result delete(@RequestBody Integer[] orderIds) {
        sysOrderService.removeByIds(Arrays.asList(orderIds));

        return Result.succ("删除成功！");
    }


    // @ResponseBody
    // @PostMapping("createOrder")
    // public Result create(@NotNull @RequestBody SysOrder sysOrder) {
    //
    //     return Result.succ();
    // }

    @Transactional
    @ResponseBody
    @PostMapping("/pay")
    @ApiOperation(value = "创建订单并下单", httpMethod = "POST")
    @ApiImplicitParam(name = "sysOrder", value = "订单信息", dataType = "SysOrder", paramType = "query")
    public String createOrder(@NotNull @RequestBody SysOrder sysOrder) {

        // 生成订单编号
        String orderNumber = OrderUtils.createOrderNumber(sysOrder.getCarId());
        sysOrder.setOrderNumber(orderNumber);
        sysOrder.setStatus(3);// 3-创建状态。
        sysOrder.setCreateTime(LocalDateTime.now());
        sysOrder.setUpdateTime(LocalDateTime.now());

        // 下单
        String result = sysOrderService.createOrder(sysOrder);

        return result;
    }


    @PostMapping("/alipay")
    @ResponseBody
    public String toAlipay(@RequestBody AlipayVo alipayVo) throws IOException {
        String result = sysOrderService.toAlipay(alipayVo);
        response.setContentType("text/html;charset=utf-8");
        return result;
    }


    // *【支付宝异步通知，支付宝支付后，会回调该接口】*
    @PostMapping(value = "/notify_url")
    public String notifyUrl(HttpServletRequest request) {
        System.out.println("异常通知");

/*        String orderNumber = sysOrderService.notifyUrl(request);
        System.out.println("【异步通知返回的订单号】" + orderNumber);
        if (!orderNumber.equals("fail")){
            Tcharge chargeInfo = chargeService.selectDataByOrderNumber(orderNumber);
            if (chargeInfo != null){
                chargeInfo.setPayStatus("已支付");
                chargeService.updateChargeData(chargeInfo);
                System.out.println("【异步通知订单更新完成！】");
            }else {
                System.out.println("【异步通知订单更新失败！】");
            }
        }*/
        return "recordTab";

        // return null;
    }

    // *【同步跳转，告诉你是否调用成功，不能拿来判断支付成功】*
    @GetMapping(value = "/return_url")
    public String returnUrl(HttpServletRequest request) throws UnsupportedEncodingException {
        /*sysOrderService.returnUrl(request);
        return "Index";*/
        System.out.println("同步");
        return "http://localhost:8080/sysOrder/myOrder";
    }

    // *【支付宝交易状态查询】*
    @RequestMapping(value = "/requestQuery")
    @ResponseBody
    public String requestQuery(String orderNumber) throws AlipayApiException {
        return sysOrderService.queryOrder(orderNumber);
    }

}
