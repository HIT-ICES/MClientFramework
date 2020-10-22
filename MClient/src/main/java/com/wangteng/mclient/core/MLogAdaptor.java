package com.wangteng.mclient.core;


import com.wangteng.mclient.log.LogPoint;
import com.wangteng.mclient.log.LogType;
import com.wangteng.mclient.log.MLog;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.lang.JoinPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class MLogAdaptor {
    private String id;//接口名称
    private String logPath;
    private Map<LogType,MLog> logMap = new HashMap<>();

    public void putLog(LogType logType,MLog log){logMap.put(logType,log);}


}
