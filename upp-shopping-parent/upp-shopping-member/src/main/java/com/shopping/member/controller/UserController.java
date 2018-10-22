package com.shopping.member.controller;

import com.shopping.api.member.entities.User;
import com.shopping.common.response.ServerResponse;
import com.shopping.common.utils.DateUtils;
import com.shopping.member.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author zhenglongsu@163.com
 * @since 2018-09-28
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/testResponse")
    public ServerResponse testResponse() {
        User user = new User();
        user.setCreated(DateUtils.getTimestamp());
        user.setUpdated(DateUtils.getTimestamp());
        user.setPhone("121331341341");
        user.setPassword("12313");
        user.setOpenid("1233113131");
        user.setId(12L);
        user.setEmail("12313@163.com");
        user.setUsername("sssss");
        return ServerResponse.createBySuccess("成功", user);
    }

    @RequestMapping("/testResponseList")
    public ServerResponse testResponseList() {
        User user1 = new User(13L, "李四", "12313", "1233113131", "121331341341", "12313@163.com", DateUtils.getTimestamp(), DateUtils.getTimestamp());
        User user2 = new User(12L, "王五", "12313", "1233313221", "121331223418", "13313@163.com", DateUtils.getTimestamp(), DateUtils.getTimestamp());
        List list = new ArrayList(16);
        list.add(user1);
        list.add(user2);
        return ServerResponse.createBySuccess("成功", list);
    }

    /**
     * 使用userId查找用户信息
     *
     * @param userId
     * @return
     */
    @RequestMapping("/findUserById")
    public ServerResponse findUserById(Long userId) {
        return userService.findUserById(userId);
    }

    @RequestMapping("/regUser")
    public ServerResponse regUser(@RequestBody User user) {
        // 参数验证
        if (StringUtils.isEmpty(user.getPassword())) {
            return ServerResponse.createByErrorMessage("密码不能为空.");
        }
        return userService.regUser(user);

    }

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    @RequestMapping("/login")
    public ServerResponse login(@RequestBody User user) {
        // 1.验证参数
        if (StringUtils.isEmpty(user.getPhone()) && StringUtils.isEmpty(user.getUsername())) {
            return ServerResponse.createByErrorMessage("手机号码不能为空!");
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            return ServerResponse.createByErrorMessage("密码不能为空!");
        }
        return userService.login(user);
    }

    /**
     * 使用token进行登录
     *
     * @param token
     * @return
     */
    @RequestMapping("/findByTokenUser")
    public ServerResponse findByTokenUser(String token) {
        // 1.验证参数
        if (StringUtils.isEmpty(token)) {
            return ServerResponse.createByErrorMessage("token不能为空!");
        }
        return userService.findByTokenUser(token);
    }


    /**
     * 使用openid查找用户信息
     *
     * @param openid
     * @return
     */
    @RequestMapping("/findByOpenIdUser")
    public ServerResponse findByOpenIdUser(String openid) {
        // 1.验证参数
        if (StringUtils.isEmpty(openid)) {
            return ServerResponse.createByErrorMessage("openid不能为空1");
        }
        return userService.findByOpenIdUser(openid);
    }

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    @RequestMapping("/qqLogin")
    public ServerResponse qqLogin(@RequestBody User user) {
        // 1.验证参数
        String openid = user.getOpenid();
        if (StringUtils.isEmpty(openid)) {
            return ServerResponse.createByErrorMessage("openid不能为空!");
        }
        return userService.qqLogin(user);
    }

}