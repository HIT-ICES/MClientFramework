package com.wangteng.mclient.log;


import lombok.Getter;
import lombok.Setter;
import org.aspectj.lang.JoinPoint;

@Getter
@Setter
public class MBugLog extends MLog{
    private Throwable e;

    @Override
    public void setInfo(LogPoint logPoint, JoinPoint joinPoint, Throwable e) {
        super.setInfo(logPoint, joinPoint);
        this.e = e;
    }

    @Override
    public String toString() {
        return "[" +logName+'|'+
                logDateTime+'|'+
                logPoint+']'+':'+
                e;
    }
}
