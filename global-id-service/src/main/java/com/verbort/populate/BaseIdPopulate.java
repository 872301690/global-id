package com.verbort.populate;

import com.verbort.bean.Id;
import com.verbort.bean.IdMeta;
import com.verbort.bean.IdType;
import com.verbort.util.TimeUtils;

public class BaseIdPopulate implements IdPopulate {
    private long seq = 0;
    private long lastTimestamp = -1;

    @Override
    public void populated(Id id, IdMeta idMeta) {
        long timestamp = TimeUtils.genTimestamp(IdType.parse(id.getType()));
        TimeUtils.validateTimestamp(timestamp,lastTimestamp);

        if (timestamp == lastTimestamp){
            seq++;
            seq &= idMeta.getSeqBytesPosMask();
            if (seq == 0){
                timestamp = TimeUtils.tillNextTime(lastTimestamp,IdType.parse(id.getType()));
            }
        }else {
            seq = 0;
            lastTimestamp = timestamp;
        }


        id.setSeq(seq);
        id.setTime(timestamp);

    }
}
