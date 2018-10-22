package com.shopping.message.service;

import com.alibaba.fastjson.JSONObject;
import com.shopping.message.adapter.MessageAdapter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.asn1.iana.IANAObjectIdentifiers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


/**
 * @Description:
 * @author: zhenglongsu@163.com
 * @date: 2018.09.30 15:40
 */
@Slf4j
@Service
public class EmailService implements MessageAdapter {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String mailFrom;
    @Value("${msg.subject}")
    private String subject;
    @Value("${msg.text}")
    private String text;

    /**
     * 报文格式:{"header":{"interfaceType":"sms_mail"},"content":{"mail":"1464503076@qq.com","username":"admin"}}
     *
     * @param jsonObject
     */
    @Override
    public void distribute(JSONObject jsonObject) {
        String email = jsonObject.getString("mail");
        if (StringUtils.isEmpty(email)) {
            return;
        }
        String username = jsonObject.getString("username");
        log.info("###消费者收到消息-->ip{},mail:{},username:{}", IANAObjectIdentifiers.mail, email, username);
        // 发送邮件
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        // 来自账号
        simpleMailMessage.setFrom(mailFrom);
        // 发送账号
        simpleMailMessage.setTo("504473410@qq.com");
        // 标题
        simpleMailMessage.setSubject(subject);
        // 内容
        simpleMailMessage.setText(text.replace("{}", email));
        log.info("###发送到邮箱 mail:{}", email);
        // 发送邮件
        javaMailSender.send(simpleMailMessage);
        log.info("### 消息服务平台发送邮件:{}完成 ###", email);
    }
}
