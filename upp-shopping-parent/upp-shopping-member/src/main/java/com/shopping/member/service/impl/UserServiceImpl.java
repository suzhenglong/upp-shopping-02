package com.shopping.member.service.impl;

import com.alibaba.fastjson.JSONObject;

import com.shopping.api.member.entities.User;
import com.shopping.api.member.entities.UserExample;
import com.shopping.common.constants.Constants;
import com.shopping.common.constants.MQInterfaceType;
import com.shopping.common.redis.BaseRedisService;
import com.shopping.common.response.ServerResponse;
import com.shopping.common.utils.DateUtils;
import com.shopping.common.utils.MD5Util;
import com.shopping.common.utils.TokenUtils;
import com.shopping.member.mapper.UserMapper;
import com.shopping.member.mq.roducer.RegisterMailboxProducer;
import com.shopping.member.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author zhenglongsu@163.com
 * @since 2018-09-28
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RegisterMailboxProducer registerMailboxProducer;
    @Value("${messages.queue}")
    private String MESSAGES_QUEUE;
    @Autowired
    private BaseRedisService baseRedisService;


    @Override
    public ServerResponse findUserById(Long userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            return ServerResponse.createByErrorMessage("未查找到用户信息.");
        }
        user.setPassword(null);
        return ServerResponse.createBySuccess(user);
    }

    @Override
    public ServerResponse regUser(User user) {

        user.setCreated(DateUtils.getTimestamp());
        user.setUpdated(DateUtils.getTimestamp());
        String phone = user.getPhone();
        user.setPassword(md5PassSalt(user.getPassword()));
        Integer result = userMapper.insert(user);
        if (result <= 0) {
            return ServerResponse.createByErrorMessage("注册用户信息失败.");
        }
        // 采用异步方式发送消息
        sendMailMessage(user);
        return ServerResponse.createBySuccessMessage("用户注册成功.");
    }

    private void sendMailMessage(User user) {
        Destination activeMQQueue = new ActiveMQQueue(MESSAGES_QUEUE);
        //报文格式:{"header":{"interfaceType":"sms_mail"},"content":{"mail":"1464503076@qq.com","username":"1464503076@qq.com"}}
        // 组装报文格式
        JSONObject root = new JSONObject();
        JSONObject header = new JSONObject();
        header.put("interfaceType", MQInterfaceType.SMS_MAIL);
        JSONObject content = new JSONObject();
        content.put("mail", user.getEmail());
        content.put("username", user.getUsername());
        root.put("header", header);
        root.put("content", content);

        String mailMessage = root.toJSONString();
        log.info("###  regist() 注册发送邮件报文 mailMessage:{}", mailMessage);
        // mq
        registerMailboxProducer.send(activeMQQueue, mailMessage);
    }

    @Override
    public ServerResponse login(User user) {
        // 2.数据库查找账号密码是否正确
        user.setPassword(md5PassSalt(user.getPassword()));
        UserExample example = new UserExample();
        if (!StringUtils.isEmpty(user.getUsername())) {
            example.createCriteria().andUsernameEqualTo(user.getUsername()).andPasswordEqualTo(user.getPassword());
        }
        if (!StringUtils.isEmpty(user.getPhone())) {
            example.createCriteria().andPhoneEqualTo(user.getPhone()).andPasswordEqualTo(user.getPassword());
        }
        List<User> users = userMapper.selectByExample(example);
        if (users.size() == 0) {
            return ServerResponse.createByErrorMessage("用户名或者密码错误");
        }
        User selectOne = users.get(0);
        return setLogin(selectOne);
    }

    private ServerResponse setLogin(User selectOne) {
        Long id = selectOne.getId();
        String token = null;
        String tokenId = baseRedisService.getString(id + "");
        if (StringUtils.isEmpty(tokenId)) {
            // 3.如果账号密码正确，对应生成token
            token = setUsertoken(id);
        } else {
            token = tokenId;
        }
        // 5.直接返回token
        return ServerResponse.createBySuccess(token);
    }

    private String setUsertoken(Long id) {
        String memberToken = TokenUtils.getMemberToken();
        //4. key为token值value为userId存放到redis中
        baseRedisService.setKeyValue(memberToken, id + "", Constants.USER_TOKEN_TEMEVALIDITY);
        baseRedisService.setKeyValue(id + "", memberToken, Constants.USER_TOKEN_TEMEVALIDITY);
        return memberToken;
    }

    @Override
    public ServerResponse findByTokenUser(String token) {
        // 2.从redis中 使用token 查找对应 userid
        String strUserId = (String) baseRedisService.getString(token);
        if (StringUtils.isEmpty(strUserId)) {
            return ServerResponse.createByErrorMessage("token无效或者已经过期!");
        }
        // 3.使用userid数据库查询用户信息返回给客户端
        Long userId = Long.parseLong(strUserId);
        User userEntity = userMapper.selectByPrimaryKey(userId);
        if (userEntity == null) {
            return ServerResponse.createByErrorMessage("为查找到该用户信息");
        }
        userEntity.setPassword(null);
        return ServerResponse.createBySuccess(userEntity);
    }

    @Override
    public ServerResponse findByOpenIdUser(String openid) {
        // 2.使用openid 查询数据库 user表对应数据信息
        UserExample example = new UserExample();
        example.createCriteria().andOpenidEqualTo(openid);
        List<User> users = userMapper.selectByExample(example);
        if (users.size() == 0) {
            return ServerResponse.createByErrorCodeMessage(Constants.HTTP_RES_CODE_201, "该openid没有关联");
        }

        // 3.自动登录
        return setLogin(users.get(0));
    }

    @Override
    public ServerResponse qqLogin(User user) {
        // 2.先进行账号登录
        ServerResponse serverResponse = login(user);
        if (ServerResponse.fail(serverResponse)) {
            return serverResponse;
        }
        // 3.自动登录
        // 4. 获取token信息
        String memberToken = (String) serverResponse.getData();
        ServerResponse userToken = findByTokenUser(memberToken);
        if (ServerResponse.fail(userToken)){
            return userToken;
        }
        User entity = (User) ServerResponse.mapToEntity(userToken, User.class);
        // 5.修改用户openid
        // 修改到数据库
        User userEntity = new User();
        userEntity.setId(entity.getId());
        userEntity.setOpenid(user.getOpenid());
        userEntity.setUpdated(DateUtils.getTimestamp());
        int byPrimaryKey = userMapper.updateByPrimaryKey(userEntity);
        if (byPrimaryKey <= 0) {
            return ServerResponse.createByErrorMessage("QQ账号管理失败!");
        }
        return serverResponse;
    }


    public String md5PassSalt(String password) {
        String newPass = MD5Util.MD5("dafaqj23ou89ZXcj@#$@#$#@KJdjklj;D../dSF.," + password);
        return newPass;
    }

}

