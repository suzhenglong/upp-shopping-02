package com.shopping.pay.controller;

import com.shopping.common.response.ServerResponse;
import com.shopping.pay.service.PaymentTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Description:
 * @author: zhenglongsu@163.com
 * @date: 2018.10.10 20:23
 */
@Slf4j
@RestController
@RequestMapping("/pay")
public class PaymentTypeController {

    @Autowired
    private PaymentTypeService paymentTypeService;
    @RequestMapping("/getPaymentType")
    public ServerResponse getPaymentType(@RequestParam("id") Long id) {
        return paymentTypeService.getPaymentType(id);
    }

}
