package com.wangteng.mclient.controller;


import com.wangteng.mclient.annotation.MLogFunction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MController{
    @GetMapping("/test")
    @MLogFunction
    public String test(){


        return "hello";
    }

}
