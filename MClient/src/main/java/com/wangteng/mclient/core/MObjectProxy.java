package com.wangteng.mclient.core;

import com.wangteng.mclient.base.MObject;
import com.wangteng.mclient.base.MResponse;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.DefaultParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;

import static com.wangteng.mclient.utils.StringUtils.ArrayToSet;


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
            Boolean getMethod = false;
            String methodName = objects[0].toString();
            MResponse mResponse = (MResponse)objects[1];
            DefaultParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
            String[] parameterNames = null;
            Map<String, Object> valueMap = mResponse.getValueMap();
            for(Method m :target.getClass().getDeclaredMethods()){
                if(m.getName().equals(methodName)){
                    parameterNames = parameterNameDiscoverer.getParameterNames(m);
                    if(valueMap.keySet().equals(ArrayToSet(parameterNames))){
                        //参数名一致
                        //判断同名参数的数据类型是否一致
                        getMethod = true;
                        method = m;
                        Object[] objectsTemp = mResponse.getParameterValue(parameterNames);
                        Type[] types = m.getGenericParameterTypes();
                        for(int i = 0;i< objectsTemp.length;i++){
                            if(!objectsTemp[i].getClass().getTypeName().equals(types[i].getTypeName())){
                                //排除基本类型与其封装类型冲突的情况
                                if(isPrimitiveType(objectsTemp[i].getClass().getTypeName(),types[i].getTypeName()))
                                    continue;
                                getMethod = false;
                                break;
                            }
                        }
                        if(getMethod) break;
                    }

                }
            }
            if(!getMethod){
                logger.error("no target method");
                return null;
            }
            objects = mResponse.getParameterValue(parameterNames);
        }
        Object result = method.invoke(o.getClass().getSuperclass().newInstance(),objects);
        return result;
    }

    private static boolean isPrimitiveType(String type1,String type2){
        if(type2.equals(byte.class.getTypeName())){
            if(type1.equals(Byte.class.getTypeName()))
                return true;
        }else if(type2.equals(char.class.getTypeName())){
            if(type1.equals(Character.class.getTypeName()))
                return true;
        }else if(type2.equals(short.class.getTypeName())){
            if(type1.equals(Short.class.getTypeName()))
                return true;
        }else if(type2.equals(int.class.getTypeName())){
            if(type1.equals(Integer.class.getTypeName()))
                return true;
        }else if(type2.equals(long.class.getTypeName())){
            if(type1.equals(Long.class.getTypeName()))
                return true;
        }else if(type2.equals(float.class.getTypeName())){
            if(type1.equals(Float.class.getTypeName()))
                return true;
        }else if(type2.equals(double.class.getTypeName())){
            if(type1.equals(Double.class.getTypeName()))
                return true;
        }else if(type2.equals(boolean.class.getTypeName())){
            if(type1.equals(Boolean.class.getTypeName()))
                return true;
        }
        return false;
    }
}
