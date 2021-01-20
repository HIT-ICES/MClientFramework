package com.hitices.mclient.utils;

import com.hitices.mclient.log.LogType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MLogFunctionUtils {
    public static final List<LogType> BEFORE = new ArrayList<>(Arrays.asList(LogType.LogInfo,LogType.LogExtra));

    public static final List<LogType> AFTER = new ArrayList<>(Arrays.asList(LogType.LogInfo,LogType.LogExtra));

    public static final List<LogType> AFTER_THROWING = new ArrayList<>(Arrays.asList(LogType.LogBug));
}
