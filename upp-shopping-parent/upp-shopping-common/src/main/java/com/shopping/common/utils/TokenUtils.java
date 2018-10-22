package com.shopping.common.utils;

import com.shopping.common.constants.Constants;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @Description:
 * @author: zhenglongsu@163.com
 * @date: 2018.10.05 16:21
 */
public class TokenUtils {

    static String getToken() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取支付token
     *
     * @return
     */
    public static String getPayToken() {
        return Constants.TOKEN_PAY + "-" + getToken();
    }

    /**
     * 获取用户token
     *
     * @return
     */
    public static String getMemberToken() {
        return Constants.TOKEN_MEMBER + "-" + getToken();
    }

}
