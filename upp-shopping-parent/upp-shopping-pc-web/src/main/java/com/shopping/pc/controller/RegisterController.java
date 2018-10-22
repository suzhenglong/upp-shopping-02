package com.shopping.pc.controller;

import com.shopping.api.member.entities.User;
import com.shopping.api.member.service.MemberClientService;
import com.shopping.common.constants.Constants;
import com.shopping.common.response.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @author: zhenglongsu@163.com
 * @date: 2018.10.17 15:43
 */
@Controller
public class RegisterController {

    @Autowired
    private MemberClientService memberClientService;
    private static final String REGISTER = "register";
    private static final String LOGIN = "login";

    /**
     * 跳转注册页面
     *
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerGet() {
        return REGISTER;
    }

    /**
     * 注册业务具体实现
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerPost(User user, HttpServletRequest reqest) {
        // 1. 验证参数
        // 2. 调用会员注册接口
        ServerResponse regUser = memberClientService.regUser(user);
        //3. 如果失败，跳转到失败页面
        if (ServerResponse.fail(regUser)) {
            reqest.setAttribute("error", "注册失败");
            return REGISTER;
        }
        // 4. 如果成功,跳转到成功页面
        return LOGIN;
    }

}
