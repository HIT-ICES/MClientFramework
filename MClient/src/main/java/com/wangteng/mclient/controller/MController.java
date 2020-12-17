package com.wangteng.mclient.controller;


import com.wangteng.mclient.annotation.MLogFunction;
import com.wangteng.mclient.base.MObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/mclient")
public class MController extends MObject {

    @GetMapping("/ismclient")
    public String isMClient(){
        String resultString = "{\"mclient\":true}";
        return resultString;
    }

}
