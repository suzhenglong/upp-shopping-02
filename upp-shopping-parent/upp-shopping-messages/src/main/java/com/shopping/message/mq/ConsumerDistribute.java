package com.shopping.message.mq;

import com.alibaba.fastjson.JSONObject;
import com.shopping.common.constants.MQInterfaceType;
import com.shopping.message.adapter.MessageAdapter;
import com.shopping.message.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @Description:消费消息 mq 从队列获取最新消息
 * @author: zhenglongsu@163.com
 * @date: 2018.09.30 15:44
 */
@Slf4j
@Component
public class ConsumerDistribute {

    @Autowired
    private EmailService emailService;

    /**
     * 报文格式:{"header":{"interfaceType":"sms_mail"},"content":{"mail":"1464503076@qq.com","username":"admin"}}
     *
     * @param json
     */
    @JmsListener(destination = "mail_queue")
    public void distribute(String json) {
        log.info("###消息服务###收到消息,消息内容--> json:{}", json);
        if (StringUtils.isEmpty(json)) {
            return;
        }
        JSONObject jsonObject = JSONObject.parseObject(json);
        JSONObject header = jsonObject.getJSONObject("header");
        String interfaceType = header.getString("interfaceType");
        MessageAdapter messageAdapter = null;
        switch (interfaceType) {
            case MQInterfaceType
                    .SMS_MAIL:
                messageAdapter = emailService;
                break;
            default:
                break;
        }
        JSONObject content = jsonObject.getJSONObject("content");
        messageAdapter.distribute(content);
    }
}
