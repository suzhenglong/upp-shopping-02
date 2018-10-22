package com.shopping.pay.controller;


import com.shopping.api.pay.entities.PaymentInfo;
import com.shopping.common.response.ServerResponse;
import com.shopping.pay.service.PaymentInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Description:
 * @author: zhenglongsu@163.com
 * @date: 2018.10.10 19:36
 */
@Slf4j
@RestController
@RequestMapping("/pay")
public class PaymentInfoController {

    @Autowired
    private PaymentInfoService paymentInfoService;

    @RequestMapping("/addPayInfoToken")
    public ServerResponse addPayInfoToken(@RequestBody PaymentInfo paymentInfo) {

        return paymentInfoService.addPayInfoToken(paymentInfo);
    }

    @RequestMapping("/getPayInfoToken")
    public ServerResponse getPayInfoToken(@RequestParam("token") String token) {
        if (StringUtils.isEmpty(token)) {
            return ServerResponse.createByErrorMessage("token不能为空!");
        }
        return paymentInfoService.getPayInfoToken(token);
    }

    @RequestMapping("/getByOrderIdPayInfo")
    public ServerResponse getByOrderIdPayInfo(@RequestParam("orderId") String orderId) {

        return paymentInfoService.getByOrderIdPayInfo(orderId);
    }

    @RequestMapping("/updatePayInfo")
    public ServerResponse updatePayInfo(@RequestBody PaymentInfo paymentInfo) {
        return paymentInfoService.updatePayInfo(paymentInfo);
    }

}
