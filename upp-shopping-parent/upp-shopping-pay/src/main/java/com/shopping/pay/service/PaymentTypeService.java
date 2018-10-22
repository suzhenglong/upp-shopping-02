package com.shopping.pay.service;


import com.shopping.common.response.ServerResponse;


/**
 * @Description:
 * @author: zhenglongsu@163.com
 * @date: 2018.10.10 20:24
 */
public interface PaymentTypeService {

    ServerResponse getPaymentType(Long id);

}
