package com.wangteng.mclient.controller;


import com.wangteng.mclient.annotation.MLogFunction;
import com.wangteng.mclient.base.MObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mclient")
public class MController extends MObject {
    @GetMapping("/test")
    @MLogFunction
    public String test(){
        return "hello";
    }

}
