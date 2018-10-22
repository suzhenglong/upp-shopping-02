package com.shopping.common.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @author: zhenglongsu@163.com
 * @date: 2018.09.28 09:19
 */
@Component
@Slf4j
public class BaseRedisService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 往redis中添加信息
     *
     * @param key
     * @param value
     */
    public void setString(String key, String value) {
        setKeyValue(key, value, null);
    }

    public void setString(String key, String value, Long timeout) {
        setKeyValue(key, value, timeout);
    }

    public void setKeyValue(String key, Object value, Long timeout) {

        log.info("redis中添加数据:key = {}, value ={} , timeout = {} ", key, value, timeout);

        if (value != null) {
            if (value instanceof String) {
                String setValue = (String) value;
                stringRedisTemplate.opsForValue().set(key, setValue);
            }
            //设置有效期
            if (timeout != null) {
                stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
            }
        }
    }

    /**
     * 从redis中获取值
     *
     * @param key
     * @return
     */
    public String getString(String key) {
        log.info("reids 获取 key:{}", key);
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 从redis中删除key
     *
     * @param key
     */
    public void delKey(String key) {
        log.info("reids 删除 key:{}", key);
        stringRedisTemplate.delete(key);
    }
}
