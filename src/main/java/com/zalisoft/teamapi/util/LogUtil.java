package com.zalisoft.teamapi.util;

import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.util.StringUtils;

public class LogUtil {


    public static boolean isBatchProcess() {
        String batchProcess = ThreadContext.get("batchProcess");
        return !StringUtils.isEmpty(batchProcess) && Boolean.parseBoolean(batchProcess);
    }

    public static String batchName() {
        return ThreadContext.get("batchName");
    }
}
