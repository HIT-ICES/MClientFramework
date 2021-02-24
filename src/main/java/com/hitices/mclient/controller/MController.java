package com.hitices.mclient.controller;

import com.hitices.common.bean.MResponse;
import com.hitices.common.bean.mclient.MClientInfoBean;
import com.hitices.common.bean.mclient.MInstInitBean;
import com.hitices.mclient.config.MClientAutoComponentScan;
import com.hitices.mclient.core.MClientSkeleton;
import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.discovery.EurekaClient;
import com.hitices.common.config.MConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/mclient")
@EnableAutoConfiguration
public class MController {

    @Autowired
    private MClientAutoComponentScan componentScan;

    @Qualifier("eurekaApplicationInfoManager")
    @Autowired
    private ApplicationInfoManager aim;

    @Qualifier("eurekaClient")
    @Autowired
    private EurekaClient discoveryClient;

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    @PostConstruct
    public void init() {
        Map<String, String> map = aim.getInfo().getMetadata();
        map.put(MConfig.MCLUSTER_SVC_METADATA_NAME, MConfig.MCLUSTER_SVC_METADATA_VALUE);
        map.put(MConfig.MCLUSTER_SVC_VER_NAME, this.componentScan.getVersion());
        MClientSkeleton.inst().setDiscoveryClient(this.discoveryClient);
        MClientSkeleton.inst().setRequestMappingHandlerMapping(this.handlerMapping);
        MClientSkeleton.inst().setComponentScan(this.componentScan);
        log.info(this.componentScan.toString());
    }

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

    @ResponseBody
    @PostMapping(value = "/init")
    public MResponse instInit(@RequestBody MInstInitBean instInitBean) {
        MClientSkeleton.inst().setGroupGatewayIpSet(instInitBean.getGroupGatewayIpSet());
        return MResponse.successResponse();
    }
}