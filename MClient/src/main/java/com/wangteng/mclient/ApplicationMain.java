package com.wangteng.mclient;

import com.squareup.javapoet.*;
import com.wangteng.mclient.base.MResponse;
import com.wangteng.mclient.controller.MController;
import com.wangteng.mclient.core.MObjectProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.lang.model.element.Modifier;
import java.io.FileWriter;
import java.io.IOException;

@Slf4j
@SpringBootApplication
public class ApplicationMain {

    public static void main(String[] args){
//        SpringApplication.run(ApplicationMain.class, args);
        MObjectProxy mObjectProxy = new MObjectProxy();
        MController sample = new MController();
        sample = (MController)mObjectProxy.getInstance(sample);
        log.info(sample.test());
        log.info(sample.transform("test2",new MResponse()).toString());

    }


}
