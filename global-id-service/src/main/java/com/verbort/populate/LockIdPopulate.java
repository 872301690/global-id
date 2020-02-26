package com.verbort.populate;

import com.verbort.bean.Id;
import com.verbort.bean.IdMeta;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockIdPopulate extends BaseIdPopulate {
    private Lock lock = new ReentrantLock();
    @Override
    public void populated(Id id, IdMeta idMeta) {
        lock.lock();
        try{

            super.populated(id, idMeta);
        }finally {
            lock.unlock();
        }
    }
}
