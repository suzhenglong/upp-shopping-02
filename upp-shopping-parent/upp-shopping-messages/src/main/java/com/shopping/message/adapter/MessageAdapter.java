package com.shopping.message.adapter;

import com.alibaba.fastjson.JSONObject;

/**
 * @Description:所有消息都会交给他进行妆发--适配器模式
 * @author: zhenglongsu@163.com
 * @date: 2018.09.30 15:23
 */
public interface MessageAdapter {
    /**
     * 接受消息
     * @param jsonObject
     */
    void distribute(JSONObject jsonObject);
}
