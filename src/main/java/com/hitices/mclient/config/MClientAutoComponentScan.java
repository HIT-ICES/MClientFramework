package com.hitices.mclient.config;

import com.hitices.common.config.Mvf4msDep;
import com.hitices.common.config.Mvf4msDepConfig;
import com.hitices.mclient.aop.MLogFunctionAop;
import com.hitices.mclient.controller.MController;
import com.hitices.mclient.core.MControllerService;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@EnableConfigurationProperties
@Configuration
@ConfigurationProperties(prefix = "mvf4ms")
@Getter
@Setter
@ToString
public class MClientAutoComponentScan {

    private String version;
    private List<Mvf4msDepConfig> dependencies;

    @Bean
    public MController mClientController() {
        return new MController();
    }

    @Bean
    public MLogFunctionAop mLogFunctionAop(){
        return new MLogFunctionAop();
    }

    @Bean
    public MControllerService mControllerService(){
        return new MControllerService();
    }

    /**
     * Please note that there may have multi dep with the same dep id for one request
     **/
    public List<Mvf4msDep> getDepListById(String depId) {
        List<Mvf4msDep> result = new ArrayList<>();
        for (Mvf4msDepConfig depConfig : this.dependencies) {
            depConfig.getDepById(depId).ifPresent(result::add);
        }

        return result;
    }

}
