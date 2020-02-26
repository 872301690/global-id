package com.verbort.bean;

public class IdMetaFactory {
    private static final IdMeta MAX_PEAK = new IdMeta((byte)10,(byte)20,(byte)30,(byte)2,(byte)1,(byte)1);

    private static final IdMeta MIN_GRANULARITY = new IdMeta((byte)10,(byte)10,(byte)40,(byte)2,(byte)1,(byte)1);

    public static final IdMeta createIdMeta(IdType idType){
        if (idType == IdType.MAX_PEAK){
            return MAX_PEAK;
        }else if (idType == IdType.MIN_GRANULARITY){
            return MIN_GRANULARITY;
        }
        return null;
    }
}
