package com.hitices.mclient.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class NamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("aop-log", new AopLoggerBeanDefinitionParser());
    }
}
