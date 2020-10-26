package com.wangteng.mclient.aop;



import com.wangteng.mclient.core.MLogAdaptor;
import com.wangteng.mclient.log.LogPoint;
import com.wangteng.mclient.log.LogType;
import com.wangteng.mclient.utils.MLogFunctionUtils;
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
                for(LogType logType : MLogFunctionUtils.before){
                    if(logAdaptor.getLogMap().get(logType)!=null){
                        logAdaptor.getLogMap().get(logType).setInfo(LogPoint.Before,joinPoint);
                        MLogUtils.log(logAdaptor,logAdaptor.getLogMap().get(logType).toString());
                    }

                }
            }
        }catch (Exception e){
            // TODO: 接口无配置文件
        }
    }

    @After("logFunction()")
    public void logFunctionCallEnd(JoinPoint joinPoint){
        try {
            MLogAdaptor logAdaptor = (MLogAdaptor) ctx.getBean(joinPoint.getSignature().getName());
            if (logAdaptor!=null){
                for(LogType logType : MLogFunctionUtils.after){
                    if(logAdaptor.getLogMap().get(logType)!=null){
                        logAdaptor.getLogMap().get(logType).setInfo(LogPoint.After,joinPoint);
                        MLogUtils.log(logAdaptor,logAdaptor.getLogMap().get(logType).toString());
                    }
                }
            }
        }catch (Exception e){
            // TODO: 接口无配置文件
        }
    }

    @AfterThrowing(pointcut = "logFunction()",throwing = "e")
    public void logFunctionCallThrowing(JoinPoint joinPoint,Throwable e){
        try {
            MLogAdaptor logAdaptor = (MLogAdaptor) ctx.getBean(joinPoint.getSignature().getName());
            if (logAdaptor!=null){
                for(LogType logType : MLogFunctionUtils.afterThrowing){
                    if(logAdaptor.getLogMap().get(logType)!=null){
                        logAdaptor.getLogMap().get(logType).setInfo(LogPoint.AfterThrowing,joinPoint,e);
                        MLogUtils.log(logAdaptor,logAdaptor.getLogMap().get(logType).toString());
                    }

                }
            }
        }catch (Exception ex){
            // TODO: 接口无配置文件
        }
    }
}
