package com.hitices.mclient.core;

import com.hitices.mclient.base.MObject;
import lombok.Getter;;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class MClientSkeleton {

    private static MClientSkeleton instance;
    @Getter
    private Map<String, MObject> mObjectMap;
    @Getter
    private Map<String, String> parentIdMap;
    @Getter
    private Map<String, Set<String>> objectId2ApiSet;

    private MClientSkeleton() {
        this.mObjectMap = new HashMap<>();
        this.parentIdMap = new HashMap<>();
        this.objectId2ApiSet = new HashMap<>();
    }

    public static MClientSkeleton inst() {
        if (instance == null) {
            synchronized (MClientSkeleton.class) {
                instance = new MClientSkeleton();
            }
        }
        return instance;
    }
    /*
     * register object
     */
    public void registerMObject(MObject object) {
        if (this.mObjectMap.containsKey(object.getId())) {
            log.warn("MObject " + object.getId() + " has been registered before !!!");
        } else {
            this.mObjectMap.put(object.getId(), object);
        }
    }

    /*
     * register the parent id of object
     */
    public void registerParent(MObject object, String parentId) {
        if (this.mObjectMap.containsKey(object.getId())) {
            this.parentIdMap.put(object.getId(), parentId);
        } else {
            log.warn("MObject " + object.getId() + " not registered");
        }
    }

    public void printParentIdMap() {
        log.info(this.parentIdMap.toString());
    }

    public List<String> getMObjectIdList() {
        return new ArrayList<>(this.mObjectMap.keySet());
    }

    public void registerObjectAndApi(String mObjectId, String apiName) {
        if (!this.objectId2ApiSet.containsKey(mObjectId)) {
            this.objectId2ApiSet.put(mObjectId, new HashSet<>());
        }
        this.objectId2ApiSet.get(mObjectId).add(apiName);
    }
}
