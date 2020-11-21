package com.wangteng.mclient.controller;


import com.wangteng.mclient.annotation.MLogFunction;
import com.wangteng.mclient.base.MObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MController extends MObject {

    @GetMapping("/test")
    @MLogFunction
    public Object test(String string1,Integer int1,Boolean b){
        log.info(string1);
        return "hello";
    }


    @GetMapping("/test2")
    public Object test2(){
        return "name";
    }
}
