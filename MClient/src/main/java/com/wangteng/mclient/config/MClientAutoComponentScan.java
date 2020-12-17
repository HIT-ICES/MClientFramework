package com.wangteng.mclient.config;

import com.wangteng.mclient.aop.MLogFunctionAop;
import com.wangteng.mclient.controller.MController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MClientAutoComponentScan {

    @Bean
    public MController mClientController() {
        return new MController();
    }

    @Bean
    public MLogFunctionAop mLogFunctionAop(){
        return new MLogFunctionAop();
    }


}
