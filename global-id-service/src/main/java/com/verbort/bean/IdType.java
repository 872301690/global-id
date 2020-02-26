package com.verbort.bean;

public enum  IdType {
    MAX_PEAK("max-peak"),
    MIN_GRANULARITY("min-granularity");
    private String name;

    IdType(String name) {
        this.name = name;
    }

    public static IdType parse(String type){
        if (MAX_PEAK.name.equals(type)){
            return MAX_PEAK;
        }else if (MIN_GRANULARITY.name.equals(type)){
            return MIN_GRANULARITY;
        }
        return null;
    }

    public static IdType parse(long type){
        if (type == 0){
            return MAX_PEAK;
        }else if (type == 1){
            return MIN_GRANULARITY;
        }
        return MAX_PEAK;
    }

    public long value(){
        if (this == MAX_PEAK){
            return 0;
        }else if (this == MIN_GRANULARITY){
            return 1;

        }
        return 0;
    }
}
