package com.shopping.pay.load;

import com.unionpay.acp.sdk.SDKConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @author: zhenglongsu@163.com
 * @date: 2018.10.11 15:35
 */
@Component
public class AutoLoadRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        SDKConfig.getConfig().loadPropertiesFromSrc();
        System.out.println(">>>>>>>>>>>>>>>服务启动执行,执行加载数据等操作<<<<<<<<<<<<<");
    }

}