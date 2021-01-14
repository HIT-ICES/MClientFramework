package com.hitices.mclient.utils;

import com.hitices.mclient.log.LogType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MLogFunctionUtils {
    public static final List<LogType> before = new ArrayList<>(Arrays.asList(LogType.LogInfo,LogType.LogExtra));

    public static final List<LogType> after = new ArrayList<>(Arrays.asList(LogType.LogInfo,LogType.LogExtra));

    public static final List<LogType> afterThrowing = new ArrayList<>(Arrays.asList(LogType.LogBug));
}
