package com.shopping.member.service;


import com.shopping.api.member.entities.User;
import com.shopping.common.response.ServerResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author zhenglongsu@163.com
 * @since 2018-09-28
 */
public interface UserService {

    /**
     * 使用userId查找用户信息
     *
     * @param userId
     * @return
     */
    ServerResponse findUserById(Long userId);

    ServerResponse regUser(@RequestBody User user);

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    ServerResponse login(@RequestBody User user);

    /**
     * 使用token进行登录
     *
     * @param token
     * @return
     */
    ServerResponse findByTokenUser(String token);


    /**
     * 使用openid查找用户信息
     *
     * @param openid
     * @return
     */
    @RequestMapping("/findByOpenIdUser")
    ServerResponse findByOpenIdUser(String openid);

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    @RequestMapping("/qqLogin")
    ServerResponse qqLogin(User user);
}