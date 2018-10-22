package com.shopping.pc.controller;

import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.oauth.Oauth;
import com.shopping.api.member.entities.User;
import com.shopping.api.member.service.MemberClientService;
import com.shopping.common.constants.Constants;
import com.shopping.common.response.ServerResponse;
import com.shopping.common.utils.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Description:
 * @author: zhenglongsu@163.com
 * @date: 2018.10.05 21:25
 */
@Controller
public class LoginController {

    @Autowired
    private MemberClientService memberClientService;
    private static final String LOGIN = "login";
    private static final String INDEX = "redirect:/";
    private static final String qqrelation = "qqrelation";

    @RequestMapping("/testResponse")
    public String testResponse(HttpServletRequest request) {
        ServerResponse serverResponse = memberClientService.testResponse();
        User user = (User) ServerResponse.mapToEntity(serverResponse, User.class);
        if (user == null) {
            return "index";
        }
        System.out.println("user:" + user);
        request.setAttribute("username", user.getUsername());
        return "index";
    }

    @RequestMapping("/testResponseList")
    public String testResponseList(HttpServletRequest request) {
        ServerResponse serverResponse = memberClientService.testResponseList();
        List<User> users = ServerResponse.mapToListEntity(serverResponse, User.class);
        for (int i = 0; i < users.size(); i++) {
            System.out.println("user" + i + " " + users.get(i));
        }
        request.setAttribute("username", users.get(0).getUsername());
        return "index";
    }

    /**
     * 跳转登录页面
     *
     * @return
     */
    @GetMapping("/login")
    public String loginGet() {
        return LOGIN;
    }

    /**
     * 登录请求具体提交实现
     *
     * @param user
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/login")
    public String loginPost(User user, HttpServletRequest request, HttpServletResponse response) {
        // 1.验证参数
        // 2.调用登录接口，获取token信息
        ServerResponse loginResponse = memberClientService.login(user);
        if (ServerResponse.fail(loginResponse)) {
            request.setAttribute("error", "账号或者密码错误!");
            return LOGIN;
        }

        String token = (String) loginResponse.getData();
        if (StringUtils.isEmpty(token)) {
            request.setAttribute("error", "会话已经失效!");
            return LOGIN;
        }
        // 3.将token信息存放在cookie里面
        setCookie(token, response);
        return INDEX;
    }

    public void setCookie(String token, HttpServletResponse response) {
        CookieUtil.addCookie(response, Constants.COOKIE_MEMBER_TOKEN, token, Constants.COOKIE_TOKEN_MEMBER_TIME);
    }

    /**
     * 生成qq授权登录链接
     * @param reqest
     * @return
     * @throws QQConnectException
     */
    @RequestMapping("/locaQQLogin")
    public String locaQQLogin(HttpServletRequest reqest) throws QQConnectException {
        String authorizeURL = new Oauth().getAuthorizeURL(reqest);
        return "redirect:" + authorizeURL;

    }

    @RequestMapping("/qqLoginCallback")
    public String qqLoginCallback(HttpServletRequest reqest, HttpServletResponse response, HttpSession httpSession) throws QQConnectException {

        // 1.获取授权码COde
        // 2.使用授权码Code获取accessToken
        AccessToken accessTokenOj = new Oauth().getAccessTokenByRequest(reqest);
        if (accessTokenOj == null) {
            reqest.setAttribute("error", "QQ授权失败");
            return "error";
        }
        String accessToken = accessTokenOj.getAccessToken();
        if (accessToken == null) {
            reqest.setAttribute("error", "accessToken为null");
            return "error";
        }
        // 3.使用accessToken获取openid
        OpenID openidOj = new OpenID(accessToken);
        String userOpenId = openidOj.getUserOpenID();
        // 4.调用会员服务接口 使用userOpenId 查找是否已经关联过账号
        ServerResponse serverResponse = memberClientService.findByOpenIdUser(userOpenId);
        if (serverResponse.getCode().equals(Constants.HTTP_RES_CODE_201)) {
            // 5.如果没有关联账号，跳转到关联账号页面
            httpSession.setAttribute("qqOpenid", userOpenId);
            return qqrelation;
        }
        //6.已经绑定账号  自动登录 将用户token信息存放在cookie中
        LinkedHashMap dataTokenMap = (LinkedHashMap) serverResponse.getData();
        String memberToken = (String) dataTokenMap.get("memberToken");
        setCookie(memberToken, response);
        return INDEX;
    }

    /**
     * 登录请求具体提交实现
     * @param userEntity
     * @param request
     * @param response
     * @param httpSession
     * @return
     */
    @PostMapping("/qqRelation")
    public String qqRelation(User userEntity, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
        // 1.获取openid
        String qqOpenid = (String) httpSession.getAttribute("qqOpenid");
        if (StringUtils.isEmpty(qqOpenid)) {
            request.setAttribute("error", "没有获取到openid");
            return "error";
        }

        // 2.调用登录接口，获取token信息
        userEntity.setOpenid(qqOpenid);
        ServerResponse loginBase = memberClientService.qqLogin(userEntity);
        if (ServerResponse.fail(loginBase)) {
            request.setAttribute("error", "账号或者密码错误!");
            return LOGIN;
        }
        String memberToken = (String) loginBase.getData();
        if (StringUtils.isEmpty(memberToken)) {
            request.setAttribute("error", "会话已经失效!");
            return LOGIN;
        }
        // 3.将token信息存放在cookie里面
        setCookie(memberToken, response);
        return INDEX;
    }

}
