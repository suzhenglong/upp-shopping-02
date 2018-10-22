package com.shopping.pay.adapt;


import com.shopping.api.pay.entities.PaymentInfo;
import com.shopping.api.pay.entities.PaymentType;

/**
 * @Description:
 * @author: zhenglongsu@163.com
 * @date: 2018.10.11 14:53
 */
public interface PayAdaptService {

    String pay(PaymentInfo paymentInfo, PaymentType paymentType);

}

