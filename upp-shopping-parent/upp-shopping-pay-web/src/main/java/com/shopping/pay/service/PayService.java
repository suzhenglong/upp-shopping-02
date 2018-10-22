package com.shopping.pay.service;


import com.shopping.api.pay.entities.PaymentInfo;

/**
 * @Description:
 * @author: zhenglongsu@163.com
 * @date: 2018.10.11 14:51
 */
public interface PayService {

    String pay(PaymentInfo paymentInfo);

}
