package com.wangteng.mclient.base;

import afu.org.checkerframework.checker.oigj.qual.O;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

@Getter
@Setter
@ToString
public class MResponse {
    private String status = "Success";
    private Map<String, Object> valueMap = new HashMap<>();

    public Object get(String key) {
        return this.valueMap.getOrDefault(key, null);
    }

    public MResponse set(String key, Object value) {
        this.valueMap.put(key, value);
        return this;
    }

    public Object[] getObject(){
        List<Object> temp = new ArrayList<>();
        Iterator<Object> iterator = valueMap.values().iterator();
        while (iterator.hasNext()){
            temp.add(iterator.next());
        }
        return temp.toArray();
    }

    public MResponse() {

    }

    public static MResponse failResponse() {
        MResponse response = new MResponse();
        response.setStatus("Fail");
        return response;
    }
}
