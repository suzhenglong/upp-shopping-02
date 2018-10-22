package com.shopping.api.member.service;


import com.shopping.api.member.entities.User;
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
@FeignClient(value = "UPP-SHOPPING-MEMBER")
public interface MemberClientService {

    @RequestMapping("/user/testResponse")
    ServerResponse testResponse();

    @RequestMapping("/user/testResponseList")
    ServerResponse testResponseList();

    /**
     * 使用userId查找用户信息
     *
     * @param userId
     * @return
     */
    @RequestMapping("/user/findUserById")
    ServerResponse findUserById(@RequestParam("userId") Long userId);

    @RequestMapping("/user/regUser")
    ServerResponse regUser(@RequestBody User user);

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    @RequestMapping("/user/login")
    ServerResponse login(@RequestBody User user);

    /**
     * 使用token进行登录
     *
     * @param token
     * @return
     */
    @RequestMapping("/user/findByTokenUser")
    ServerResponse findByTokenUser(@RequestParam("token") String token);


    /**
     * 使用openid查找用户信息
     *
     * @param openid
     * @return
     */
    @RequestMapping("/user/findByOpenIdUser")
    ServerResponse findByOpenIdUser(@RequestParam("openid") String openid);

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    @RequestMapping("/user/qqLogin")
    ServerResponse qqLogin(@RequestBody User user);
}
