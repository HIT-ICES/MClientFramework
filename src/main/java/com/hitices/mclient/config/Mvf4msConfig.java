package com.hitices.mclient.config;

import com.hitices.mclient.controller.MController;
import com.septemberhx.common.config.Mvf4msDep;
import com.septemberhx.common.config.Mvf4msDepConfig;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SeptemberHX
 * @version 0.1
 * @date 2020/3/1
 */
@EnableConfigurationProperties
@Configuration
@ConfigurationProperties(prefix = "mvf4ms")
@Getter
@Setter
@ToString
public class Mvf4msConfig {
    private String version;
    private List<Mvf4msDepConfig> dependencies;

    @Bean
    public MController mClientController() {
        return new MController();
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
