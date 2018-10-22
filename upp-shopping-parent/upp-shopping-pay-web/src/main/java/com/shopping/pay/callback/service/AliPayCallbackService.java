package com.shopping.pay.callback.service;

import com.shopping.common.response.ServerResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Description:
 * @author: zhenglongsu@163.com
 * @date: 2018.10.12 16:07
 */

public interface AliPayCallbackService {
    /**
     * 同步回调
     *
     * @return
     */
    ServerResponse syn(Map<String, String> params);

    /**
     * 异步回调
     *
     * @return
     */
    String asyn(Map<String, String> params);
}
