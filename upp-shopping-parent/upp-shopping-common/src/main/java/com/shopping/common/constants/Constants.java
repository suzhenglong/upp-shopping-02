package com.shopping.common.constants;

/**
 * @Description:
 * @author: zhenglongsu@163.com
 * @date: 2018.10.05 16:34
 */
public interface Constants {
    /**
     * 响应请求成功
     */
    String HTTP_RES_CODE_200_VALUE = "success";
    /**
     * 系统错误
     */
    String HTTP_RES_CODE_500_VALUE = "fial";
    /**
     * 响应请求成功code
     */
    Integer HTTP_RES_CODE_200 = 200;
    /**
     * 系统错误
     */
    Integer HTTP_RES_CODE_500 = 500;

    /**
     * 用户会话保存90天
     */
    Long USER_TOKEN_TEMEVALIDITY = 60L * 60L * 24L * 90L;

    /**
     * 会员token
     */
    String TOKEN_MEMBER = "TOKEN_MEMBER";
    /**
     * cookie 会员 totoken 名称
     */
    String COOKIE_MEMBER_TOKEN = "cookie_member_token";

    Integer COOKIE_TOKEN_MEMBER_TIME = 60 * 60 * 24 * 90;
    /**
     * 未关联QQ账号
     */
    Integer HTTP_RES_CODE_201 = 201;

    /**
     * 支付token
     */
    String TOKEN_PAY = "TOKEN_PAY";
    /**
     * 支付token有效期
     */
    Long PAY_TOKEN_MEMBER_TIME = 60L * 15L;

    /**
     * 支付成功
     */
    String ALI_PAY_SUCCESS = "success";
    /**
     * 支付失败
     */
    String ALI_PAY_FAIL = "fail";
}

