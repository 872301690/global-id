package com.verbort.service;

import com.verbort.bean.Id;

import java.util.Date;

public interface IdService {

    long genId();
    Id expId(long id);
    long makeId(long seq,long time);
    long makeId(long machineId,long seq,long time);
    long makeId(long machineId,long genMethod,long seq,long time);
    long makeId(long machineId,long genMethod,long type,long seq,long time);
    long makeId(long machineId,long genMethod,long type,long version,long seq,long time);
    Date transTime(long time);

}
