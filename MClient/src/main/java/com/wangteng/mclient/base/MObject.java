package com.wangteng.mclient.base;

import com.wangteng.mclient.annotation.MFunctionType;
import com.wangteng.mclient.core.MClientSkeleton;
import com.wangteng.mclient.core.MObjectProxy;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

@Slf4j
public abstract class MObject {

    protected String id = null;
    private MObject objectProxy;

    protected MObject() {
        this.id = this.getClass().getCanonicalName() + "_" + UUID.randomUUID().toString();

        if (this.getId() != null) {
            for (Field field : this.getClass().getDeclaredFields()) {
                if (field.getAnnotation(MFunctionType.class) != null) {
                    if (!MObject.class.isAssignableFrom(field.getType())) {
                        throw new IllegalArgumentException("Wrong type with MFunctionType com.wangteng.mclient.annotation!");
                    } else {

                        try {
                            MObjectProxy tmpProxy = new MObjectProxy();

                            // create an instance of field.getType with default constructor
                            // this means the class needs to have an default constructor
                            Class<?> clazz = field.getType();
                            Constructor<?> ctor = clazz.getConstructor();
                            MObject obj = (MObject) ctor.newInstance(new Object[]{});
                            MClientSkeleton.inst().registerParent(obj, this.getId());

                            // set the proxy object
                            field.setAccessible(true);
                            field.set(this, tmpProxy.getInstance((obj)));
                        } catch (IllegalAccessException e1) {
                            log.error(e1.toString());
                        } catch (InstantiationException e2) {
                            log.error(e2.toString());
                        } catch (NoSuchMethodException e3) {
                            log.error(e3.toString());
                        } catch (InvocationTargetException e4) {
                            log.error(e4.toString());
                        }
                    }
                }
            }

            for (Method method : this.getClass().getDeclaredMethods()) {
                MClientSkeleton.inst().registerObjectAndApi(this.getId(), method.getName());
            }
            MClientSkeleton.inst().registerMObject(this);
            log.info(this.getId() + " created");
            MClientSkeleton.inst().printParentIdMap();
        }
    }

    public String getId() {
        return id;
    }

    protected void setId(String id) {
        this.id = id;
    }

    public Object transform(String methodName,MResponse mResponse){
        return null;
    }

}
