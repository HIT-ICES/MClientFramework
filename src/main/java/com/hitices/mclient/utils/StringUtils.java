package com.hitices.mclient.utils;

import com.hitices.mclient.controller.MController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StringUtils {
    public static Set<String> arrayToSet(String[] strings){
        Set<String> stringSet = new HashSet<>();
        for (String s : strings){
            stringSet.add(s);
        }
        return stringSet;
    }
}
