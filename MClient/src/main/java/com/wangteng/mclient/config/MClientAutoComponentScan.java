package com.wangteng.mclient.config;

import com.wangteng.mclient.aop.MLogInfoAop;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MClientAutoComponentScan {
    @Bean
    public MLogInfoAop mLogFunctionAop(){return new MLogInfoAop();}
}
