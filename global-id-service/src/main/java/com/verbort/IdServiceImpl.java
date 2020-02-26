package com.verbort;

import com.verbort.bean.Id;
import com.verbort.bean.IdType;
import com.verbort.populate.CASIdPopulate;
import com.verbort.populate.IdPopulate;
import com.verbort.populate.LockIdPopulate;
import com.verbort.populate.SyncIdPopulate;
import com.verbort.util.CommonsUtils;

public class IdServiceImpl extends AbstractIdService {

    public IdServiceImpl() {
        super();

        initPopulate();
    }

    public IdServiceImpl(IdType idType) {
        super(idType);

        initPopulate();
    }

    public IdServiceImpl(String idType) {
        super(idType);

        initPopulate();
    }

    private static final String SYNC_LOCK_IMPL_KEY = "vesta.sync.lock.impl.key";

    private static final String ATOMIC_IMPL_KEY = "vesta.atomic.impl.key";

    private IdPopulate idPopulate;

    private void initPopulate() {
        if (idPopulate != null){

        } else if (CommonsUtils.isSitchOn(System.getProperty(SYNC_LOCK_IMPL_KEY))){
            idPopulate = new SyncIdPopulate();
        }else if (CommonsUtils.isSitchOn(System.getProperty(ATOMIC_IMPL_KEY))){
            idPopulate = new CASIdPopulate();
        }else {
            idPopulate = new LockIdPopulate();
        }
    }

    @Override
    void populate(Id id) {
        idPopulate.populated(id,super.idMeta);
    }
}
