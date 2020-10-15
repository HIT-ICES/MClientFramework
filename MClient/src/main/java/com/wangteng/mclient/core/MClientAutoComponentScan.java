package com.wangteng.mclient.core;

import com.wangteng.mclient.aop.MLogFunctionAop;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MClientAutoComponentScan {
    @Bean
    public MLogFunctionAop mLogFunctionAop(){return new MLogFunctionAop(); }
}
