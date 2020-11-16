package com.wangteng.mclient.core;

import com.wangteng.mclient.base.MObject;
import com.wangteng.mclient.base.MResponse;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;


public class MObjectProxy implements InvocationHandler {

    private static Logger logger = LogManager.getLogger(MObjectProxy.class);

    private MObject target;

    public MObjectProxy() {

    }

    public MObject getInstance(MObject mObject) {
        this.target = mObject;
        Enhancer en = new Enhancer();
        en.setSuperclass(mObject.getClass());
        en.setCallback(this);
        return (MObject)en.create();
    }

    public <T extends MObject> T getInstance(Class<T> tClass) throws IllegalAccessException, InstantiationException {
        this.target = tClass.newInstance();
        Enhancer en = new Enhancer();
        en.setSuperclass(tClass);
        en.setCallback(this);
        return tClass.cast(en.create());
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        if(method.getName().equals("transform")){
            method = target.getClass().getMethod(objects[0].toString());
            objects = ((MResponse)objects[1]).getObject();
        }
        Object result = method.invoke(o.getClass().getSuperclass().newInstance(),objects);
        return result;
    }
}
