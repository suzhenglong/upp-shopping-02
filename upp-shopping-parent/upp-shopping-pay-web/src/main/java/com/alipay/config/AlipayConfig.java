package com.alipay.config;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @Description:
 * @author: zhenglongsu@163.com
 * @date: 2018.10.19 11:05
 * 类名：AlipayConfig
 * 功能：基础配置类
 * 详细：设置帐户有关信息及返回路径
 * 修改日期：2017-04-05
 * 说明：
 * 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 * 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */
public class AlipayConfig {

    // ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    /**
     * 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
     */
    public static String app_id = "2016091000481652";

    /**
     * 商户私钥，您的PKCS8格式RSA2私钥
     */
    public static String merchant_private_key = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCWUo5bKDTwBJkhoDANqvP/k1Op3vxqZV63LI9SI0GDnqfWc19B9ycxIaL1+/np5XamXmoo0t7C5NqjjrAmzUO2KrbOy4+zCBUOcuvCRbnsVIUbxeL31EiBRxSlkq3ktKtt2yLscI/m5x8Brawz2QVcPi9yDT6wpIK6ZxwsgMMNysQoDeZJBLHRxFVk2s6yS64c5PRMYd8ZfJqIgPKOSl1zD9nfrLgtKcsyf7AG+zeaft+raPrdDVNJX/YloMEw/LGKKfAaFhsbIQlLn8/ctD8bHAAnW3f3RqYQHO0Vmd8VZPPqshL2OabO68j+DdoWi+483hY/9G2HijuUu4rSFDUrAgMBAAECggEACdODsrnCHB5w1B3ghkQyhvZedbA518qgkmC7Tac4VR494mrQQ0OgsDl+qY27HGr/goiRb/G41gyXrjFZmm+4iWPpZ0MvNYjFt5gTiTN9n9E2kkYxO/oIc0ruMpSQAW899YRpmAMTpQheooFzzD/Xddwqxc3P10Qpt32d/dq85V3ODc1vN6kEHYm52Vf//2L6u9WirzXlDyLtMCXCVu4/u10R3gZ3wKQBhHsqxp5/1swUh5DHxfEGhIG43EleBsgKvdP19PnwOB5XH5p8pC96kA61Wxbtabc/XETd0IvsY/Zk/xJLYKblyAOuZZFyss1JfJ6NKfT2nLWuqgI0lgTbYQKBgQDWMshZiv8FLOi/g0T35NjC8V+Sh53xP8sLxknXl7IIDgiaBzkeNlVBHa2BePI5ww+Q8pi/E70iOZYGJVLdDhMevyVp0mKLKjWTCOWfMUejKwP+clB7JtJbNSiObh2T7Jxe1H9ZGSb+D9BjRI9hsg/bhydV6a7e3x4BV/tBmXBn3QKBgQCzqJLvKCMWnsWhpjIVdvtHByqjsKb9kI00szgm35z0fdbkWfw4QS7Da07/1mJ7A+YlJu1vQQTevoEw5aOQd6Thj5O3OZre78+KdkLmaTTATEhx8YAU6vJvdKL5sxgVn6dMz/dM7ehx1bo1IIZXoXAauQ7YsAT8Ra+ry2ZBjxUEpwKBgQCPlrlh9WCheoqIH5pzR3Dbte0okEsbt3UI7qL/qIxLBAVWI/NxQmECZr+yJH7Uo+Nieo5UwQmv0MS2V/zlfRLqMJ8HAmbW/bOuXm93uRRZ2HMz9jPnao3nYIpQeJbRW/MzSuYXt1nBRJ6gfQxVJIWm10JSQHXep9k4c19WiWKl7QKBgQCgCIdqNayb165gAfUXHbdTW9TlntQBhqPhz8szBXmAaB/lpSqAyb9oH8xtJIDw51Y4veY+dgJqj/PwpH4AkDL1IboJOmz0LVYIFrQdHpX4vXrC1Mas22Q9V/5cwuWvTVnvmEjH5BxsWl/WNzS0FoAPrczFxHWMal7DfxmFSHkGfQKBgQDUYX1z2UIOieKOilK8eaN671yPBaKYj2Ra8+Oxwa2VIObjtWsR14xRgDz3DrstXZ23vMFMh0WORJTpvEKUxD6a7ZVyrWofn/fqEwdOZAfsof5Fe+AElUiivIynMwbhQ1FGA+WJY7MM2aZMW6wSVk7jxrNjlKcAkhbCScWH2bbrlw==";

    /**
     * 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
     */
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxKXb+CM4FyPOOruhVGcA36o9mDEqbfRlS8biSw4b1tHuEJ115GD3seL+9x0zu2Z7s5yZX2Ftj4hnGxzaqxYeRNCTtYmoWzgenEL4faK2nIo8edbfhsJNAS1X2CnanNJ1GUV/P4W2ATvDQZmqB+P2eon7C6ymKCjDruKAoBpai283HxELkJNNcsJ+wqetVzaRGLPeuPyaN2xvB99jzHdBgLubhZDrVgB44Z1AYrQhLjSwiMZ1DdG0xzc3NXTtqdF+qvYPeGkLAh/zxLMxUhwPZM4QvowhNVou3h4TaoescroH85Y7m2YAA0Tn3Tw5+rL40ZmF8u4S/DvFmWt6IPwqfQIDAQAB";

    /**
     * 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     */
    public static String notify_url = "http://8409c4a0.ngrok.io/notify_url.jsp";

    /**
     * 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     */
    public static String return_url = "http://8409c4a0.ngrok.io/return_url.jsp";

    /**
     * 签名方式
     */
    public static String sign_type = "RSA2";

    /**
     * 字符编码格式
     */
    public static String charset = "utf-8";

    /**
     * 支付宝网关
     */
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    /**
     * 支付宝网关
     */
    public static String log_path = "D:\\logs\\alipay";

    // ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     *
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis() + ".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
