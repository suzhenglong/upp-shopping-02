package com.shopping.pay.callback.service;

import java.util.Map;

/**
 * @Description:
 * @author: zhenglongsu@163.com
 * @date: 2018.10.12 16:07
 */

public interface YinLianCallbackService {
    /**
     * 同步回调
     *
     * @return
     */
    Map<String, String> syn(Map<String, String> reqParam);

    /**
     * 异步回调
     *
     * @return
     */
    String asyn(Map<String, String> reqParam,String encoding);
}
