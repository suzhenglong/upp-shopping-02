package com.shopping.pay.controller.callback;

import com.alibaba.fastjson.JSONObject;
import com.shopping.common.response.ServerResponse;
import com.shopping.pay.callback.service.AliPayCallbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Description:
 * @author: zhenglongsu@163.com
 * @date: 2018.10.12 16:28
 */
@RequestMapping("/pay/aliPayCallback")
@Controller
@Slf4j
public class AliPayCallbackController {


    @Autowired
    private AliPayCallbackService aliPayCallbackService;
    private static final String PAY_SUCCESS = "ali_pay_success";

    /**
     * 功能说明:同步回调
     *
     * @param request
     * @return
     */
    @RequestMapping("/syn")
    public void syn(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html;charset=utf-8");
        Map<String, String> params = getRequestParams(request);
        log.info("### 支付宝同步回调 AliPayCallbackController #### syn 开始 params:{}", params);

        ServerResponse serverResponse = aliPayCallbackService.syn(params);
        PrintWriter writer = response.getWriter();
        if (ServerResponse.fail(serverResponse)) {
            // 报错页面
            return;
        }
        JSONObject data = (JSONObject) serverResponse.getData();
        String outTradeNo = (String) data.get("outTradeNo");
        // 支付宝交易号
        String tradeNo = (String) data.get("tradeNo");
        // 付款金额
        String totalAmount = (String) data.get("totalAmount");


        // 封装成html 浏览器模拟去提交
        String htmlFrom = "<form name='punchout_form' "
                + "method='post' "
                + "action='http://127.0.0.1:8768/pay-web/pay/aliPayCallback/synSuccessPage' >"
                + "<input type='hidden' name='outTradeNo' value='" + outTradeNo + "'>"
                + "<input type='hidden' name='tradeNo' value='" + tradeNo + "'>"
                + "<input type='hidden' name='totalAmount' value='" + totalAmount + "'>"
                + "<input type='submit' value='立即支付' style='display:none'>"
                + "</form>"
                + "<script>document.forms[0].submit();</script>";
        writer.println(htmlFrom);
    }

    private Map<String, String> getRequestParams(HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        return params;
    }

    /**
     * 以post表达隐藏参数
     *
     * @return
     */
    @RequestMapping(value = "/synSuccessPage", method = RequestMethod.POST)
    public String synSuccessPage(HttpServletRequest request, String outTradeNo, String tradeNo, String totalAmount) {
        request.setAttribute("outTradeNo", outTradeNo);
        request.setAttribute("tradeNo", tradeNo);
        request.setAttribute("totalAmount", totalAmount);
        return PAY_SUCCESS;
    }

    /**
     * 功能说明:异步回调
     *
     * @param request
     * @return
     */
    @RequestMapping("/asyn")
    @ResponseBody
    public String asyn(HttpServletRequest request) throws IOException{
        Map<String, String> params = getRequestParams(request);
        log.info("### 支付宝异步回调 AliPayCallbackController #### asyn 开始 params:{}", params);
        String result = aliPayCallbackService.asyn(params);
        return result;
    }

}
