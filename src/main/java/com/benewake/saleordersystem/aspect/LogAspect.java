package com.benewake.saleordersystem.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Lcs
 * @Description TODO
 * @since 2023年06月29 15:11
 */

@Slf4j
@Aspect
@Component
public class LogAspect {
    /**
     * ..表示包及子包 该方法代表controller层所有方法
     */
    @Pointcut("execution(public * com.benewake.saleordersystem.controller..*.*(..))")
    public void controllerMethod(){}

    @Around("controllerMethod()")
    public void before(ProceedingJoinPoint joinPoint) throws Throwable {
        // before
        // 用户XXX，在XXX（时间），访问了[xxx方法]
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(attributes == null) {
            return;
        }
        HttpServletRequest request = attributes.getRequest();
        String ip = request.getRemoteHost();
        String now = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss").format(new Date());
        String target = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        log.info(String.format("用户[%s],在[%s],使用[%s]请求,Url:[%s],请求参数：[%s],访问了[%s].",
                ip,now,request.getMethod(),request.getRequestURL().toString(),JSON.toJSONString(joinPoint.getArgs()),target));

        Object result = joinPoint.proceed();

        // after
        log.info("返回结果："+JSON.toJSONString(result));


    }



}
