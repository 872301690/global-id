package com.verbort.populate;

import com.verbort.bean.Id;
import com.verbort.bean.IdMeta;
import com.verbort.bean.IdType;
import com.verbort.util.TimeUtils;

import java.util.concurrent.atomic.AtomicReference;

public class CASIdPopulate implements IdPopulate {

    class Varint{
        long seq = 0;
        long lastTimestamp = -1;
    }
    private AtomicReference<Varint> reference = new AtomicReference<>(new Varint());

    @Override
    public void populated(Id id, IdMeta idMeta) {
        Varint oldValue,newValue ;
        long seq;

        while (true){
            oldValue = reference.get();
            long timestamp = TimeUtils.genTimestamp(IdType.parse(id.getType()));
            TimeUtils.validateTimestamp(timestamp,oldValue.lastTimestamp);

            seq = oldValue.seq;

            if (oldValue.lastTimestamp == timestamp){
                seq ++;
                seq &= idMeta.getSeqBytesPosMask();
                if (seq == 0){
                    timestamp = TimeUtils.tillNextTime(oldValue.lastTimestamp,IdType.parse(id.getType()));
                }
            }else {
                seq = 0;
            }
            newValue = new Varint();
            newValue.seq = seq;
            newValue.lastTimestamp = timestamp;

            if (reference.compareAndSet(oldValue,newValue)){
                id.setTime(timestamp);
                id.setSeq(seq);

                break;
            }

        }
    }
}
