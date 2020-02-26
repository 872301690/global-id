package com.verbort.convert;

import com.verbort.bean.Id;
import com.verbort.bean.IdMeta;
import com.verbort.bean.IdMetaFactory;
import com.verbort.bean.IdType;

public class IdConvertImpl implements IdConvert {
    private IdMeta idMeta;

    public IdConvertImpl(IdMeta idMeta) {
        this.idMeta = idMeta;
    }
    public IdConvertImpl(IdType idType) {
        this(IdMetaFactory.createIdMeta(idType));
    }


    public IdMeta getIdMeta() {
        return idMeta;
    }

    public void setIdMeta(IdMeta idMeta) {
        this.idMeta = idMeta;
    }

    public IdConvertImpl() {
    }

    @Override
    public long convert(Id id) {
        return doConvert(id,idMeta);
    }

    private long doConvert(Id id, IdMeta idMeta) {
        long ret;

        ret = id.getMachineId();

        ret |= id.getSeq() << idMeta.getSeqBytesPos();

        ret |= id.getTime() << idMeta.getTimeBytesPos();

        ret |= id.getGenMethod() << idMeta.getGenMethodBytesPos();

        ret |= id.getType() << idMeta.getTypeBytesPos();

        ret |= id.getVersion() << idMeta.getVersionBytesPos();

        return ret;
    }

    @Override
    public Id prase(long id) {
        Id ret = new Id();

        ret.setMachineId(id & idMeta.getMachineIdBytesMask());

        ret.setSeq( (id >>> idMeta.getSeqBytesPos()) & idMeta.getSeqBytesPosMask());

        ret.setTime((id >>> idMeta.getTimeBytesPos()) & idMeta.getTimeBytesPosMask());

        ret.setGenMethod((id >>> idMeta.getGenMethodBytesPos()) & idMeta.getGenMethodBytesPosMask());

        ret.setType((id >>> idMeta.getTypeBytesPos()) & idMeta.getTypeBytesPosMask());

        ret.setVersion((id >>> idMeta.getVersionBytesPos()) & idMeta.getVersionBytesPosMask());

        return ret;
    }
}
