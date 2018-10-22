package com.shopping.pay.controller.callback;

import com.shopping.pay.callback.service.YinLianCallbackService;
import com.unionpay.acp.sdk.AcpService;
import com.unionpay.acp.sdk.LogUtil;
import com.unionpay.acp.sdk.SDKConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @author: zhenglongsu@163.com
 * @date: 2018.10.12 16:28
 */
@Slf4j
@RequestMapping("/pay/yinlianPayCallback")
@Controller
public class YinLianCallbackController {


    @Autowired
    private YinLianCallbackService yinLianCallbackService;
    private static final String PAY_SUCCESS = "pay_success";
    private static final String PAY_FAIL = "pay_fail";

    /**
     * 功能说明:同步回调
     * @param request
     * @return
     */
    @RequestMapping("/syn")
    public String syn(HttpServletRequest request) {
        Map<String, String> reqParam = getRequestParam(request);
        Map<String, String> valideData = yinLianCallbackService.syn(reqParam);
        String encoding = request.getParameter(SDKConstants.param_encoding);
        //重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
        if (!AcpService.validate(valideData, encoding)) {
            LogUtil.writeLog("验证签名结果[失败].");
            //验签失败，需解决验签问题
            return PAY_FAIL;

        }

        LogUtil.writeLog("验证签名结果[成功].");
        request.setAttribute("txnAmt", Double.parseDouble(valideData.get("txnAmt")) / 100);
        request.setAttribute("orderId", Long.parseLong(valideData.get("orderId")));
        LogUtil.writeLog("验证签名结果[成功].");
        return PAY_SUCCESS;

    }

    /**
     * 功能说明:异步回调
     * @param request
     * @return
     */
    @RequestMapping("/asyn")
    public String asyn(HttpServletRequest request) {
        Map<String, String> reqParam = getRequestParam(request);
        String encoding = request.getParameter(SDKConstants.param_encoding);
        return yinLianCallbackService.asyn(reqParam,encoding);
    }


    /**
     * 获取请求参数中所有的信息
     * 当商户上送frontUrl或backUrl地址中带有参数信息的时候，
     * 这种方式会将url地址中的参数读到map中，会导多出来这些信息从而致验签失败，这个时候可以自行修改过滤掉url中的参数或者使用getAllRequestParamStream方法。
     *
     * @param request
     * @return
     */
    private Map<String, String> getRequestParam(
            final HttpServletRequest request) {
        Map<String, String> res = new HashMap<>(16);
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
                // 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
                if (res.get(en) == null || "".equals(res.get(en))) {
                    log.info("======为空的字段名====" + en);
                    res.remove(en);
                }
            }
        }
        return res;
    }

}
