package com.hitices.mclient.controller;


import com.hitices.mclient.bean.MClientInfoBean;
import com.hitices.mclient.core.MClientSkeleton;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
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

    @GetMapping(path = "/info")
    public MClientInfoBean getInfo() {
        MClientInfoBean infoBean = new MClientInfoBean();
        infoBean.setApiMap(MClientSkeleton.inst().getObjectId2ApiSet());
        infoBean.setParentIdMap(MClientSkeleton.inst().getParentIdMap());
        infoBean.setMObjectIdSet(new HashSet<>(MClientSkeleton.inst().getMObjectIdList()));
        return infoBean;
    }

}
