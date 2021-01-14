package com.hitices.mclient.config;

import com.hitices.mclient.aop.MLogFunctionAop;
import com.hitices.mclient.controller.MController;
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
