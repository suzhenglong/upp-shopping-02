package com.shopping.common.log;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @Description:使用aop打印请求日志 @Aspect:申明是个切面
 * @author: zhenglongsu@163.com
 * @date: 2018.09.28 16:04
 */

@Aspect
@Component
@Slf4j
public class LogAspect {

    /**
     * 申明一个切点 里面是 execution表达式
     */
    @Pointcut("execution(public * com.shopping.api.*.service.*.*(..)) || execution(public * com.shopping.*.controller.*.*(..))||execution(public * com.shopping.pay.service.*.*(..))||execution(public * com.shopping.pay.controller.*.*(..))")
    private void controllerAspect() {
    }

    /**
     * 请求method前打印内容
     *
     * @param joinPoint
     */
    @Before(value = "controllerAspect()")
    public void methodBefore(JoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        log.info("===============请求内容 start ===============");
        try {
            // 打印请求内容
            log.info("请求地址:" + request.getRequestURL().toString());
            log.info("请求方式:" + request.getMethod());
            log.info("请求类方法:" + joinPoint.getSignature());
            log.info("请求类方法参数:" + Arrays.toString(joinPoint.getArgs()));
        } catch (Exception e) {
            log.error("###LogAspectServiceApi.class methodBefore() ### ERROR:", e);
        }
        log.info("===============请求内容 end ===============");
    }

    /**
     * 在方法执行完结后打印返回内容
     *
     * @param o
     */
    @AfterReturning(returning = "o", pointcut = "controllerAspect()")
    public void methodAfterReturing(Object o) {
        log.info("--------------返回内容 start ----------------");
        try {
            log.info("Response内容:" + JSONObject.toJSONString(o));
        } catch (Exception e) {
            log.error("###LogAspectServiceApi.class methodAfterReturing() ### ERROR:", e);
        }
        log.info("--------------返回内容 end ----------------");
    }
}
