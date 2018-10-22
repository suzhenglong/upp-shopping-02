package com.shopping.pay.callback.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.shopping.api.pay.entities.PaymentInfo;
import com.shopping.api.pay.service.PayClientService;
import com.shopping.common.response.ServerResponse;
import com.shopping.common.utils.DateUtils;
import com.shopping.pay.callback.service.YinLianCallbackService;
import com.unionpay.acp.sdk.AcpService;
import com.unionpay.acp.sdk.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description:
 * @author: zhenglongsu@163.com
 * @date: 2018.10.12 16:08
 */
@Slf4j
@Service
public class YinLianCallbackServiceImpl implements YinLianCallbackService {

    @Autowired
    private PayClientService payClientService;

    /**
     * 同步通知
     * @param reqParam
     * @return
     */
    @Override
    public Map<String, String> syn(Map<String, String> reqParam) {
        LogUtil.writeLog("YinLianCallbackServiceImpl syn () 前台接收报文返回开始");

        // 获取银联通知服务器发送的后台通知参数
        LogUtil.printRequestLog(reqParam);

        LogUtil.writeLog("YYinLianCallbackServiceImpl syn () 前台接收报文返回结束");
        return reqParam;
    }

    /**
     * 异步通知
     * @param reqParam
     * @return
     */
    @Override
    public String asyn(Map<String, String> reqParam,String encoding) {
        LogUtil.writeLog("YinLianCallbackServiceImpl asyn () 前台接收报文返回开始");

        // 获取银联通知服务器发送的后台通知参数
        LogUtil.printRequestLog(reqParam);

        //重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
        if (!AcpService.validate(reqParam, encoding)) {
            LogUtil.writeLog("验证签名结果[失败].");
            //验签失败，需解决验签问题

        }
        LogUtil.writeLog("验证签名结果[成功].");
        //【注：为了安全验签成功才应该写商户的成功处理逻辑】交易成功，更新商户订单状态

        //获取后台通知的数据，其他字段也可用类似方式获取
        String orderId = reqParam.get("orderId");
        ServerResponse byOrderIdPayInfo = payClientService.getByOrderIdPayInfo(orderId);
        if(ServerResponse.fail(byOrderIdPayInfo)){
            return "fail";
        }
        PaymentInfo paymentInfo = (PaymentInfo) ServerResponse.mapToEntity(byOrderIdPayInfo, PaymentInfo.class);
        log.info("paymentInfo:{}", paymentInfo);
        String state = paymentInfo.getState();
        if (state.equals("1")) {
            log.error("订单号:{},已经支付成功!,无需再次做操作..", paymentInfo.getOrderid());
            return "ok";
        }


        // 第三方支付订单号
        paymentInfo.setPlatformorderid(reqParam.get("queryId"));
        // 修改時間
        paymentInfo.setUpdated(DateUtils.getTimestamp());
        // 支付报文
        paymentInfo.setPaymessage(JSONObject.toJSONString(reqParam));
        // 状态
        paymentInfo.setState("1");

        payClientService.updatePayInfo(paymentInfo);

        LogUtil.writeLog("YYinLianCallbackServiceImpl asyn () 前台接收报文返回结束");

        return "ok";
    }



}
