package com.verbort.util;

import com.verbort.bean.IdMeta;
import com.verbort.bean.IdType;

import javax.xml.crypto.Data;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

    public static final long EPOCH = 1546272000000L;

    public static long genTimestamp(IdType idType) {
        if (idType == IdType.MAX_PEAK){
            //最大峰值型
            return (System.currentTimeMillis() - EPOCH) / 1000;
        } else if (idType == IdType.MIN_GRANULARITY){
            //最小粒度型
            return System.currentTimeMillis() - EPOCH;
        }
        return (System.currentTimeMillis() - EPOCH) / 1000;
    }

    public static void validateTimestamp(long timestamp,long lastTimestamp) {
       if (timestamp < lastTimestamp){
           throw  new IllegalArgumentException("当前时间不能小于上次时间");
       }
    }

    public static long tillNextTime(long lastTimestamp, IdType idType) {
        long timestamp = genTimestamp(idType);
        while (timestamp <= lastTimestamp){
            timestamp = genTimestamp(idType);
        }

        return timestamp;
    }
}
