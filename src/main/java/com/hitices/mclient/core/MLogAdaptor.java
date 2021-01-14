package com.hitices.mclient.core;


import com.hitices.mclient.log.LogType;
import com.hitices.mclient.log.MLog;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class MLogAdaptor {
    private String id;//接口名称
    private String logPath;
    private Map<LogType, MLog> logMap = new HashMap<>();

    public void putLog(LogType logType,MLog log){logMap.put(logType,log);}


}
