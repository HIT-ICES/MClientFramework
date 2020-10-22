package com.wangteng.mclient.aop;



import com.wangteng.mclient.core.MLogAdaptor;
import com.wangteng.mclient.log.LogPoint;
import com.wangteng.mclient.log.LogType;
import com.wangteng.mclient.utils.MLogUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MLogFunctionAop {

    private static ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

    @Pointcut("@annotation(com.wangteng.mclient.annotation.MLogFunction)")
    public void logFunction(){}

    @Before("logFunction()")
    public void logFunctionCall(JoinPoint joinPoint){
        try {
            MLogAdaptor logAdaptor = (MLogAdaptor) ctx.getBean(joinPoint.getSignature().getName());
            if (logAdaptor!=null){
                logAdaptor.getLogMap().get(LogType.LogInfo).setInfo(LogPoint.Before,joinPoint);
                logAdaptor.getLogMap().get(LogType.LogExtra).setInfo(LogPoint.Before,joinPoint);
                MLogUtils.log(logAdaptor,logAdaptor.getLogMap().get(LogType.LogInfo).toString());
                MLogUtils.log(logAdaptor,logAdaptor.getLogMap().get(LogType.LogExtra).toString());
            }
        }catch (Exception e){
            System.out.println(e);
            // TODO: 接口没有配置文件的情况
        }
    }

    @After("logFunction()")
    public void logFunctionCallEnd(JoinPoint joinPoint){
        try {
            MLogAdaptor logAdaptor = (MLogAdaptor) ctx.getBean(joinPoint.getSignature().getName());
            if (logAdaptor!=null){
                logAdaptor.getLogMap().get(LogType.LogInfo).setInfo(LogPoint.After,joinPoint);
                logAdaptor.getLogMap().get(LogType.LogExtra).setInfo(LogPoint.After,joinPoint);
                MLogUtils.log(logAdaptor,logAdaptor.getLogMap().get(LogType.LogInfo).toString());
                MLogUtils.log(logAdaptor,logAdaptor.getLogMap().get(LogType.LogExtra).toString());
            }
        }catch (Exception e){
            // TODO: 接口没有配置文件的情况
        }
    }

    @AfterThrowing(pointcut = "logFunction()",throwing = "e")
    public void logFunctionCallThrowing(JoinPoint joinPoint,Throwable e){
        try {
            MLogAdaptor logAdaptor = (MLogAdaptor) ctx.getBean(joinPoint.getSignature().getName());
            if (logAdaptor!=null){
                logAdaptor.getLogMap().get(LogType.LogBug).setInfo(LogPoint.AfterThrowing,joinPoint,e);
                MLogUtils.log(logAdaptor,logAdaptor.getLogMap().get(LogType.LogBug).toString());
            }
        }catch (Exception ex){
            // TODO: 接口没有配置文件的情况
        }
    }
}
