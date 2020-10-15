package com.wangteng.mclient.aop;


import com.wangteng.common.log.MFunctionCalledLog;
import com.wangteng.common.log.MFunctionCallEndLog;
import com.wangteng.common.utils.MLogUtils;
import com.wangteng.mclient.base.MObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class MLogFunctionAop {

    private static Logger logger = LogManager.getLogger(MLogFunctionAop.class);

    @Pointcut("@annotation(com.wangteng.mclient.annotation.MLogFunction)")
    public void logFunction(){
        System.out.println("this is in logFunction");
    }

    @Before("logFunction()")
    public void logFunctionCall(JoinPoint joinPoint){
        logger.info("before");
        logger.info(joinPoint.getSignature().getName());
        logger.info(joinPoint.getArgs().length);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        MFunctionCalledLog serviceBaseLog = new MFunctionCalledLog();
        serviceBaseLog.setLogDateTime(DateTime.now());
        serviceBaseLog.setLogMethodName(joinPoint.getSignature().getName());
        serviceBaseLog.setLogObjectId(((MObject)joinPoint.getTarget()).getId());
        if (request != null) {
            serviceBaseLog.setLogFromIpAddr(request.getRemoteAddr());
            serviceBaseLog.setLogFromPort(request.getRemotePort());
            serviceBaseLog.setLogUserId(request.getHeader("userId"));
            serviceBaseLog.setLogIpAddr(request.getLocalAddr());
        }
        logger.info(serviceBaseLog);

    }

    @After("logFunction()")
    public void logFunctionCallEnd(JoinPoint joinPoint){
        logger.info("after");
        logger.info(joinPoint.getSignature().getName());
        logger.info(joinPoint.getArgs().length);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        MFunctionCallEndLog serviceBaseLog = new MFunctionCallEndLog();
        serviceBaseLog.setLogDateTime(DateTime.now());
        serviceBaseLog.setLogMethodName(joinPoint.getSignature().getName());
        serviceBaseLog.setLogObjectId(((MObject)joinPoint.getTarget()).getId());

        if (request != null) {
            serviceBaseLog.setLogFromIpAddr(request.getRemoteAddr());
            serviceBaseLog.setLogFromPort(request.getRemotePort());
            serviceBaseLog.setLogUserId(request.getHeader("userId"));
            serviceBaseLog.setLogIpAddr(request.getLocalAddr());
        }
        logger.info(serviceBaseLog);
    }
}
