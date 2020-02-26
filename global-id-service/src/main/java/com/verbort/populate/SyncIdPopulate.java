package com.verbort.populate;

import com.verbort.bean.Id;
import com.verbort.bean.IdMeta;

public class SyncIdPopulate extends BaseIdPopulate {
    @Override
    public synchronized void populated(Id id, IdMeta idMeta) {
        super.populated(id, idMeta);
    }
}
