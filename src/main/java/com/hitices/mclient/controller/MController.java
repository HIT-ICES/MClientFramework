package com.hitices.mclient.controller;

import com.hitices.mclient.bean.MClientInfoBean;
import com.hitices.mclient.config.Mvf4msConfig;
import com.hitices.mclient.core.MClientSkeleton;
import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.discovery.EurekaClient;
import com.septemberhx.common.config.MConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static Logger logger = LogManager.getLogger(MController.class);

    @Autowired
    private Mvf4msConfig mvf4msConfig;

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
        map.put(MConfig.MCLUSTER_SVC_VER_NAME, this.mvf4msConfig.getVersion());
        MClientSkeleton.inst().setDiscoveryClient(this.discoveryClient);
        MClientSkeleton.inst().setRequestMappingHandlerMapping(this.handlerMapping);
        MClientSkeleton.inst().setMvf4msConfig(this.mvf4msConfig);
        logger.info(this.mvf4msConfig.toString());
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

}
