package com.wangteng.mclient.controller;


import com.wangteng.mclient.annotation.MLogFunction;
import com.wangteng.mclient.base.MObject;
import com.wangteng.mclient.core.MClientSkeleton;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mclient")
public class MController {

    @GetMapping("/ismclient")
    public String isMClient(){
        String resultString = "{\"mclient\":true}";
        return resultString;
    }

    @GetMapping(path = "/getMObjectIdList")
    public List<String> getMObjectIdList() {
        return MClientSkeleton.inst().getMObjectIdList();
    }

}
