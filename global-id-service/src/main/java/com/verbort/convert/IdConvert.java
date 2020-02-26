package com.verbort.convert;

import com.verbort.bean.Id;

public interface IdConvert {
    long convert(Id id);
    Id prase(long id);
}
