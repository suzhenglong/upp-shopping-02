package com.shopping.api.pay.service;

import com.shopping.api.pay.entities.PaymentInfo;
import com.shopping.common.response.ServerResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @Description:用户服务
 * @author: zhenglongsu@163.com
 * @date: 2018.09.28 18:41
 */
@FeignClient(value = "UPP-SHOPPING-PAY")
public interface PayClientService {

    /**
     * 换取支付令牌
     *
     * @param paymentInfo
     * @return
     */
    @RequestMapping("/pay/addPayInfoToken")
    ServerResponse addPayInfoToken(@RequestBody PaymentInfo paymentInfo);

    /**
     * 使用token查找支付信息
     *
     * @param token
     * @return
     */
    @RequestMapping("/pay/getPayInfoToken")
    ServerResponse getPayInfoToken(@RequestParam("token") String token);

    /**
     * 使用token查找支付信息
     *
     * @param orderId
     * @return
     */
    @RequestMapping("/pay/getByOrderIdPayInfo")
    ServerResponse getByOrderIdPayInfo(@RequestParam("orderId") String orderId);

    /**
     * 使用token查找支付信息
     *
     * @param paymentInfo
     * @return
     */
    @RequestMapping("/pay/updatePayInfo")
    ServerResponse updatePayInfo(@RequestBody PaymentInfo paymentInfo);

    /**
     * 获取支付方式
     *
     * @param id
     * @return
     */
    @RequestMapping("/pay/getPaymentType")
    ServerResponse getPaymentType(@RequestParam("id") Long id);

}
