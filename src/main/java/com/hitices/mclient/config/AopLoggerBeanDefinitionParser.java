package com.hitices.mclient.config;

import com.hitices.mclient.log.*;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;
import com.hitices.mclient.core.MLogAdaptor;

import java.util.HashMap;
import java.util.Map;

public class AopLoggerBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
    @Override
    protected Class<?> getBeanClass(Element element) {
        return MLogAdaptor.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        String id = element.getAttribute("id");
        String logInfo = element.getAttribute("logInfo");
        String logPath = element.getAttribute("logPath");
        String logBug = element.getAttribute("logBug");
        String logExtra = element.getAttribute("logExtra");
        Map<LogType, MLog> logMap = new HashMap<>();



        if(StringUtils.hasText(id)){
            builder.addPropertyValue("id",id);
        }
        if(StringUtils.hasText(logInfo)){
            if(logInfo.equals("true")){
                logMap.put(LogType.LogInfo,new MBaseLogInfo());
            }
        }
        if(StringUtils.hasText(logPath)){
            builder.addPropertyValue("logPath",logPath);
        }
        if(StringUtils.hasText(logBug)){
            if(logInfo.equals("true")){
                logMap.put(LogType.LogBug,new MBugLog());
            }
        }
        if(StringUtils.hasText(logExtra)){
            logMap.put(LogType.LogExtra,new MExtraLogInfo(logExtra.split(",")));
        }
        builder.addPropertyValue("logMap",logMap);


    }
}
