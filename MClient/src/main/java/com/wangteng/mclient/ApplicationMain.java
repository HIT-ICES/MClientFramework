package com.wangteng.mclient;

import com.wangteng.mclient.base.MResponse;
import com.wangteng.mclient.controller.MController;
import com.wangteng.mclient.core.MObjectProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@Slf4j
@SpringBootApplication
public class ApplicationMain {

    public static void main(String[] args){
//        SpringApplication.run(ApplicationMain.class, args);
        MObjectProxy mObjectProxy = new MObjectProxy();
        MController sample = new MController();
        sample = (MController)mObjectProxy.getInstance(sample);
        MResponse mResponse = new MResponse();
        int x = 1;
        mResponse.set("string1","qwe");
        mResponse.set("int1",x);
        mResponse.set("b",true);
        log.info(sample.transform("test",mResponse).toString());

    }


}
