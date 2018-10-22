package com.shopping.pay.callback.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.config.AlipayConfig;
import com.shopping.api.pay.entities.PaymentInfo;
import com.shopping.api.pay.service.PayClientService;
import com.shopping.common.constants.Constants;
import com.shopping.common.response.ServerResponse;
import com.shopping.pay.callback.service.AliPayCallbackService;
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
public class AliPayCallbackServiceImpl implements AliPayCallbackService {

    @Autowired
    private PayClientService payClientService;


    @Override
    public ServerResponse syn(Map<String, String> params) {
        try {
            // 1.日志记录
            log.info("##### 支付宝同步通知syn#####开始,params:{} #####", params);
            // 2.验签操作
            // 调用SDK验证签名
            boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key,
                    AlipayConfig.charset, AlipayConfig.sign_type);
            log.info("##### 支付宝同步通知signVerified:{} ######", signVerified);
            // ——请在这里编写您的程序（以下代码仅作参考）——
            if (!signVerified) {
                return ServerResponse.createByErrorMessage("验签失败!");
            }
            // 商户订单号
            String outTradeNo = params.get("out_trade_no");
            // 支付宝交易号
            String tradeNo = params.get("trade_no");
            // 付款金额
            String totalAmount = params.get("total_amount");
            JSONObject data = new JSONObject();
            data.put("outTradeNo", outTradeNo);
            data.put("tradeNo", tradeNo);
            data.put("totalAmount", totalAmount);
            return ServerResponse.createBySuccess(data);
        } catch (Exception e) {
            log.error("####支付宝同步通知出现异常,ERROR:", e);
            return ServerResponse.createByErrorMessage("系统错误!");
        } finally {
            log.info("##### 支付宝同步通知 syn ##### 结束,params:{} #####", params);
        }

    }

    @Override
    public String asyn(Map<String, String> params) {
        // 1.日志记录
        log.info("##### 支付宝异步通知 asyn ##### 开始,params:{} #####", params);
        // 2.验签操作
        try {
            boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key,
                    AlipayConfig.charset, AlipayConfig.sign_type);
            // 调用SDK验证签名
            log.info("##### 支付宝异步通知 signVerified:{} ######", signVerified);
            // ——请在这里编写您的程序（以下代码仅作参考）——
            if (!signVerified) {
                return Constants.ALI_PAY_FAIL;
            }
            // 商户订单号
            String outTradeNo = params.get("out_trade_no");
            ServerResponse byOrderIdPayInfo = payClientService.getByOrderIdPayInfo(outTradeNo);
            if (ServerResponse.fail(byOrderIdPayInfo)) {
                return Constants.ALI_PAY_FAIL;
            }
            PaymentInfo paymentInfo = (PaymentInfo) ServerResponse.mapToEntity(byOrderIdPayInfo, PaymentInfo.class);

            // 支付宝重试机制
            String state = "1";
            if (paymentInfo.getState().equals(state)) {
                return Constants.ALI_PAY_SUCCESS;
            }

            // 支付宝交易号
            String tradeNo = params.get("trade_no");
            // 付款金额
            // String totalAmount = params.get("total_amount");
            // 判断实际付款金额与商品金额是否一致
            // 修改 支付表状态
            paymentInfo.setState("1");
            // 标识为已经支付
            paymentInfo.setPaymessage(params.toString());
            paymentInfo.setPlatformorderid(tradeNo);
            // 手动 begin begin
            ServerResponse updatePayInfo = payClientService.updatePayInfo(paymentInfo);
            if (ServerResponse.fail(updatePayInfo)) {
                return Constants.ALI_PAY_FAIL;
            }

            return Constants.ALI_PAY_SUCCESS;
        } catch (Exception e) {
            log.error("#### 支付宝异步通知出现异常,ERROR:", e);
            // 回滚 手动回滚 rollback
            return Constants.ALI_PAY_FAIL;
        } finally {
            log.info("##### 支付宝异步通知 syn #####结束,params:{}", params);
        }
    }
}