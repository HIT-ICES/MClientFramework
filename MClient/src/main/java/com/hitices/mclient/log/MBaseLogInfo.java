package com.hitices.mclient.log;


import lombok.Getter;
import lombok.Setter;
import org.aspectj.lang.JoinPoint;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
@Getter
@Setter
public class MBaseLogInfo extends MLog {

    private String logFromIpAddr;
    private String logLocalAddr;
    private Integer logFromPort = 0;
    private Integer logLocalPort = 0;

    @Override
    public void setInfo(LogPoint logPoint, JoinPoint joinPoint) {
        super.setInfo(logPoint,joinPoint);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (request != null) {
            this.setLogFromIpAddr(request.getRemoteAddr());
            this.setLogFromPort(request.getRemotePort());
            this.setLogLocalAddr(request.getLocalAddr());
            this.setLogLocalPort(request.getLocalPort());
        }
    }

    @Override
    public String toString() {
        return "[" +logName+'|'+
                logDateTime+'|'+
                logPoint+']'+':'+
                "logFromIpAddr=" + logFromIpAddr+
                ", logLocalAddr=" + logLocalAddr+
                ", logFromPort=" + logFromPort +
                ", logLocalPort=" + logLocalPort;
    }
}
