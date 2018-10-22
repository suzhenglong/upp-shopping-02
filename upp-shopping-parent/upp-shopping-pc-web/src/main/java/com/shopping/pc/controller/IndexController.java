package com.shopping.pc.controller;

import com.shopping.api.member.entities.User;
import com.shopping.api.member.service.MemberClientService;
import com.shopping.common.constants.Constants;
import com.shopping.common.response.ServerResponse;
import com.shopping.common.utils.CookieUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Description:
 * @author: zhenglongsu@163.com
 * @date: 2018.10.17 14:05
 */
@Controller
public class IndexController {

    private static final String INDEX = "index";
    @Autowired
    private MemberClientService memberClientService;

    /**
     * 主页
     *
     * @param reqest
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(HttpServletRequest reqest) {
        // 1.从cookie中获取 token信息
        String token = CookieUtil.getUid(reqest, Constants.COOKIE_MEMBER_TOKEN);
        // 2.如果cookie 存在 token，调用会员服务接口，使用token查询用户信息
        if (!StringUtils.isEmpty(token)) {
            ServerResponse serverResponse = memberClientService.findByTokenUser(token);
            User user = (User) ServerResponse.mapToEntity(serverResponse, User.class);
            System.out.println(user);
            reqest.setAttribute("username", user.getUsername());
        }
        return INDEX;
    }

}
