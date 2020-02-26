package com.verbort.populate;

import com.verbort.bean.Id;
import com.verbort.bean.IdMeta;

public interface IdPopulate {
    void populated(Id id, IdMeta idMeta);
}
