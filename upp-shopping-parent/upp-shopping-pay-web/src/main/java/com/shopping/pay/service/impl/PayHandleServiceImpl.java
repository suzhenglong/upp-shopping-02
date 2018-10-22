package com.shopping.pay.service.impl;


import com.shopping.pay.service.PayHandleService;
import com.shopping.pay.service.PayService;
import com.shopping.api.pay.entities.PaymentInfo;
import com.shopping.api.pay.service.PayClientService;
import com.shopping.common.response.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Description:
 * @author: zhenglongsu@163.com
 * @date: 2018.10.11 14:34
 */
@Service
public class PayHandleServiceImpl implements PayHandleService {

    @Autowired
    private PayClientService payClientService;
    @Autowired
    private PayService payService;

    @Override
    public String payHandle(String token) {
        ServerResponse payInfoToken = payClientService.getPayInfoToken(token);
        if (ServerResponse.fail(payInfoToken)) {
            return payInfoToken.getMsg();
        }
        PaymentInfo paymentInfo = (PaymentInfo) ServerResponse.mapToEntity(payInfoToken, PaymentInfo.class);
        System.out.println("paymentInfo" + paymentInfo);
        String html = payService.pay(paymentInfo);
        return html;
    }
}
