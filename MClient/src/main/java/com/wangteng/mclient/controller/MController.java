package com.wangteng.mclient.controller;


import com.wangteng.mclient.annotation.MLogInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MController{
    @GetMapping("/test")
    @MLogInfo
    public String test(){


        return "hello";
    }

}
