package com.wangteng.mclient.log;

import lombok.Getter;
import lombok.Setter;
import org.aspectj.lang.JoinPoint;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@Getter
@Setter
public class MExtraLogInfo extends MLog{
    private Map<String,String> header = new HashMap<>();

    public MExtraLogInfo(String[] headerName){
        for(String s:headerName){
            this.header.put(s,"null");
        }
    }

    @Override
    public void setInfo(LogPoint logPoint, JoinPoint joinPoint) {
        super.setInfo(logPoint, joinPoint);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (request != null) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                entry.setValue(request.getHeader(entry.getKey()));
            }
        }
    }

    @Override
    public String toString() {
        String headerString = "";
        for (Map.Entry<String, String> entry : header.entrySet()) {
            headerString += entry.getKey()+"="+entry.getValue()+"; ";
        }
        return "["+ logName + '|' +
                logDateTime + '|' +
                logPoint +']' +
                ':'+headerString;
    }
}
