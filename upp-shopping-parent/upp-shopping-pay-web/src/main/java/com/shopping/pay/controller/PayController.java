package com.shopping.pay.controller;

import com.shopping.pay.service.PayHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description:
 * @author: zhenglongsu@163.com
 * @date: 2018.10.11 11:20
 */

@Controller
@RequestMapping("/pay")
public class PayController{

    @Autowired
    private PayHandleService payHandleService;

    @RequestMapping("/payIndex")
    public void payIndex(String token, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        // 使用token 查找对应支付信息
        try {
            String html = payHandleService.payHandle(token);
            out.println(html);
        } catch (Exception e) {
            out.println("系统错误");
        } finally {
            out.close();
        }
    }
}
