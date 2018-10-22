package com.shopping.member.mq.roducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;

/**
 * @Description:往消息服务 推送 邮件信息
 * @author: zhenglongsu@163.com
 * @date: 2018.09.30 11:13
 */
@Service
public class RegisterMailboxProducer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void send(Destination destination, String json) {
        jmsMessagingTemplate.convertAndSend(destination, json);
    }

}
