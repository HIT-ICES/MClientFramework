package com.hitices.mclient.log;

import lombok.Getter;
import lombok.Setter;
import org.aspectj.lang.JoinPoint;
import org.joda.time.DateTime;


@Getter
@Setter
public abstract class MLog {
    protected String logName;
    protected DateTime logDateTime;
    protected LogPoint logPoint;

    public void setInfo(LogPoint logPoint, JoinPoint joinPoint){
        this.setLogName(joinPoint.getSignature().getName());
        this.setLogDateTime(DateTime.now());
        this.setLogPoint(logPoint);
    }

    public void setInfo(LogPoint logPoint, JoinPoint joinPoint,Throwable e){
        this.setLogName(joinPoint.getSignature().getName());
        this.setLogDateTime(DateTime.now());
        this.setLogPoint(logPoint);
    }

    @Override
    public String toString() {
        return "MLog{" +
                "logName='" + logName + '\'' +
                ", logDateTime=" + logDateTime +
                ", logPoint=" + logPoint +
                '}';
    }
}
