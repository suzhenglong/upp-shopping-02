package com.shopping.pay.service;

import com.shopping.api.pay.entities.PaymentInfo;
import com.shopping.common.response.ServerResponse;

/**
 * @Description:
 * @author: zhenglongsu@163.com
 * @date: 2018.10.10 19:40
 */
public interface PaymentInfoService {

    ServerResponse addPayInfoToken(PaymentInfo paymentInfo);
    ServerResponse getPayInfoToken(String token);
    ServerResponse getByOrderIdPayInfo(String orderId);
    ServerResponse updatePayInfo(PaymentInfo paymentInfo);

}
