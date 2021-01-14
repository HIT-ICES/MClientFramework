package com.hitices.mclient.utils;

import com.hitices.mclient.controller.MController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StringUtils {
    public static Set<String> ArrayToSet(String[] strings){
        Set<String> stringSet = new HashSet<>();
        for (String s : strings){
            stringSet.add(s);
        }
        return stringSet;
    }

    public static void main(String[] args) {
        boolean b = true;
        List<String> strings = new ArrayList<>();
        MController m = new MController();
        String s1 = "java.lang.Integer";
        String s2 = "int";
        System.out.println(m.getClass().getTypeName());
    }
}
