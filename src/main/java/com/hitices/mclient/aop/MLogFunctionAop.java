package com.hitices.mclient.aop;



import com.hitices.mclient.log.LogPoint;
import com.hitices.mclient.log.LogType;
import com.hitices.mclient.core.MLogAdaptor;
import com.hitices.mclient.utils.MLogFunctionUtils;
import com.hitices.mclient.utils.MLogUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Aspect
@Component
@Slf4j
public class MLogFunctionAop {

    private static ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

    @Pointcut("@annotation(com.hitices.mclient.annotation.MLogFunction)")
    public void logFunction() {
    }

    @Before("logFunction()")
    public void logFunctionCall(JoinPoint joinPoint) {
        try {
            MLogAdaptor logAdaptor = null;
            if(!ctx.containsBean(joinPoint.getSignature().getName())){
                logAdaptor = (MLogAdaptor) ctx.getBean(joinPoint.getSignature().getDeclaringType().getSimpleName());
            }
            else{
                logAdaptor = (MLogAdaptor) ctx.getBean(joinPoint.getSignature().getName());
            }
            if (logAdaptor != null) {
                for (LogType logType : MLogFunctionUtils.BEFORE) {
                    if (logAdaptor.getLogMap().get(logType) != null) {
                        logAdaptor.getLogMap().get(logType).setInfo(LogPoint.Before, joinPoint);
                        MLogUtils.log(logAdaptor, logAdaptor.getLogMap().get(logType).toString());
                    }

                }
            }
        } catch (Exception e) {
            throw new RuntimeException("There is no applicationContext.xml");
        }
    }

    @After("logFunction()")
    public void logFunctionCallEnd(JoinPoint joinPoint) {
        try {
            MLogAdaptor logAdaptor = null;
            if(!ctx.containsBean(joinPoint.getSignature().getName())){
                logAdaptor = (MLogAdaptor) ctx.getBean(joinPoint.getSignature().getDeclaringType().getSimpleName());
            }
            else{
                logAdaptor = (MLogAdaptor) ctx.getBean(joinPoint.getSignature().getName());
            }
            if (logAdaptor != null) {
                for (LogType logType : MLogFunctionUtils.AFTER) {
                    if (logAdaptor.getLogMap().get(logType) != null) {
                        logAdaptor.getLogMap().get(logType).setInfo(LogPoint.After, joinPoint);
                        MLogUtils.log(logAdaptor, logAdaptor.getLogMap().get(logType).toString());
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("There is no applicationContext.xml");
        }
    }

    @AfterThrowing(pointcut = "logFunction()", throwing = "e")
    public void logFunctionCallThrowing(JoinPoint joinPoint, Throwable e) {
        try {
            MLogAdaptor logAdaptor = null;
            if(!ctx.containsBean(joinPoint.getSignature().getName())){
                logAdaptor = (MLogAdaptor) ctx.getBean(joinPoint.getSignature().getDeclaringType().getSimpleName());
            }
            else{
                logAdaptor = (MLogAdaptor) ctx.getBean(joinPoint.getSignature().getName());
            }
            if (logAdaptor != null) {
                for (LogType logType : MLogFunctionUtils.AFTER_THROWING) {
                    if (logAdaptor.getLogMap().get(logType) != null) {
                        logAdaptor.getLogMap().get(logType).setInfo(LogPoint.AfterThrowing, joinPoint, e);
                        MLogUtils.log(logAdaptor, logAdaptor.getLogMap().get(logType).toString());
                    }

                }
            }
        } catch (Exception ex) {
            throw new RuntimeException("There is no applicationContext.xml");
        }
    }


}
