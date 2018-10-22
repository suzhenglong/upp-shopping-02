package com.shopping.pay.service.impl;

import com.shopping.api.pay.entities.PaymentInfo;
import com.shopping.api.pay.entities.PaymentInfoExample;
import com.shopping.common.constants.Constants;
import com.shopping.common.redis.BaseRedisService;
import com.shopping.common.response.ServerResponse;
import com.shopping.common.utils.DateUtils;
import com.shopping.common.utils.TokenUtils;
import com.shopping.pay.mapper.PaymentInfoExtendMapper;
import com.shopping.pay.mapper.PaymentInfoMapper;
import com.shopping.pay.service.PaymentInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @author: zhenglongsu@163.com
 * @date: 2018.10.10 19:42
 */
@Service
@Slf4j
public class PaymentInfoServiceImpl implements PaymentInfoService {

    @Autowired
    private BaseRedisService baseRedisService;

    @Autowired
    private PaymentInfoMapper paymentInfoMapper;

    @Autowired
    private PaymentInfoExtendMapper paymentInfoExtendMapper;

    @Override
    public ServerResponse addPayInfoToken(PaymentInfo paymentInfo) {
        paymentInfo.setCreated(DateUtils.getTimestamp());
        paymentInfo.setUpdated(DateUtils.getTimestamp());
        paymentInfo.setTxntime(DateUtils.getTimestamp());
        paymentInfoExtendMapper.insertAndselectKey(paymentInfo);
        Long paymentInfoId = paymentInfo.getId();
        if (paymentInfoId == null) {
            return ServerResponse.createByErrorMessage("系统错误,创建支付订单支付失败");
        }
        String token = TokenUtils.getPayToken();
        baseRedisService.setString(token, paymentInfoId + "", Constants.PAY_TOKEN_MEMBER_TIME);
        return ServerResponse.createBySuccess(token);
    }

    @Override
    public ServerResponse getPayInfoToken(String token) {
        // 2.判断token有效期
        // 3.使用token 查找redis 找到对应支付id
        String payId = baseRedisService.getString(token);
        if (StringUtils.isEmpty(payId)) {
            return ServerResponse.createByErrorMessage("支付请求已经超时!");
        }
        // 4.使用支付id，进行下单
        Long payIDl = Long.parseLong(payId);

        // 5.使用支付id查询支付信息
        String payInfoId = baseRedisService.getString(token);
        PaymentInfo paymentInfo = paymentInfoMapper.selectByPrimaryKey(Long.parseLong(payInfoId));
        if (paymentInfo == null) {
            return ServerResponse.createByErrorMessage("未找到支付信息");
        }
        return ServerResponse.createBySuccess(paymentInfo);
    }

    @Override
    public ServerResponse getByOrderIdPayInfo(String orderId) {
        PaymentInfoExample example = new PaymentInfoExample();
        example.createCriteria().andOrderidEqualTo(orderId);
        List<PaymentInfo> paymentInfoList = paymentInfoMapper.selectByExample(example);
        if (paymentInfoList.size() == 0) {
            return ServerResponse.createByErrorMessage("未查询到支付信息");
        }
        PaymentInfo paymentInfo = paymentInfoList.get(0);
        return ServerResponse.createBySuccess(paymentInfo);
    }

    @Override
    public ServerResponse updatePayInfo(PaymentInfo paymentInfo) {
        int updateResult = paymentInfoMapper.updateByPrimaryKeySelective(paymentInfo);
        if (updateResult <= 0) {
            ServerResponse.createByErrorMessage("支付订单状态修改失败");
        }
        return ServerResponse.createBySuccess();
    }
}
