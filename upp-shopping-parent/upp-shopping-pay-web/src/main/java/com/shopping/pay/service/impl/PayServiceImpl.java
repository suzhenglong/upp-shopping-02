package com.shopping.pay.service.impl;


import com.shopping.pay.adapt.PayAdaptService;
import com.shopping.pay.adapt.impl.AliPayService;
import com.shopping.pay.adapt.impl.YinLianPayService;
import com.shopping.pay.service.PayService;
import com.shopping.api.pay.entities.PaymentInfo;
import com.shopping.api.pay.entities.PaymentType;
import com.shopping.api.pay.service.PayClientService;
import com.shopping.common.response.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Description:
 * @author: zhenglongsu@163.com
 * @date: 2018.10.11 14:51
 */
@Service
public class PayServiceImpl implements PayService {

    @Autowired
    private PayClientService payClientService;

    @Autowired
    private YinLianPayService yinLianPayService;
    @Autowired
    private AliPayService aliPayService;

    @Override
    public String pay(PaymentInfo paymentInfo) {
        // 判断支付类型
        String typeId = paymentInfo.getTypeid();
        ServerResponse serverResponse = payClientService.getPaymentType(Long.parseLong(typeId));
        PaymentType paymentType = (PaymentType) ServerResponse.mapToEntity(serverResponse, PaymentType.class);
        if (paymentType == null) {
            return null;
        }
        String typeName = paymentType.getTypename();
        PayAdaptService payAdaptService = null;
        switch (typeName) {
            case "yinlianPay":
                payAdaptService = yinLianPayService;
                break;
            case "aliPay":
                payAdaptService = aliPayService;
                break;
            default:
                break;
        }
        return payAdaptService.pay(paymentInfo, paymentType);
    }
}
