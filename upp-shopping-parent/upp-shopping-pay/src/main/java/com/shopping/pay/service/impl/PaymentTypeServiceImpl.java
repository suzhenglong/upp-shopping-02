package com.shopping.pay.service.impl;


import com.shopping.api.pay.entities.PaymentType;
import com.shopping.common.response.ServerResponse;
import com.shopping.pay.mapper.PaymentTypeMapper;
import com.shopping.pay.service.PaymentTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Description:
 * @author: zhenglongsu@163.com
 * @date: 2018.10.10 20:25
 */
@Service
@Slf4j
public class PaymentTypeServiceImpl implements PaymentTypeService {

    @Autowired
    private PaymentTypeMapper paymentTypeMapper;

    @Override
    public ServerResponse getPaymentType(Long id) {
        PaymentType paymentType = paymentTypeMapper.selectByPrimaryKey(id);
        if (paymentType == null) {
            return ServerResponse.createByErrorMessage("未查找到类型");
        }
        return ServerResponse.createBySuccess(paymentType);
    }
}
