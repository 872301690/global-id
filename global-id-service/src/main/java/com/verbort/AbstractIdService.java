package com.verbort;

import com.verbort.bean.Id;
import com.verbort.bean.IdMeta;
import com.verbort.bean.IdMetaFactory;
import com.verbort.bean.IdType;
import com.verbort.convert.IdConvert;
import com.verbort.convert.IdConvertImpl;
import com.verbort.machine.MachineIdProvider;
import com.verbort.service.IdService;
import com.verbort.util.TimeUtils;

import java.util.Date;

public abstract class AbstractIdService implements IdService {
    protected long machineId;
    protected long genMethod;
    protected long version;
    protected long type;

    protected IdType idType;
    protected IdMeta idMeta;
    protected IdConvert idConvert;
    protected MachineIdProvider machineIdProvider;

    public AbstractIdService() {
        idType = IdType.MAX_PEAK;
    }

    public AbstractIdService(IdType idType) {
        this.idType = idType;
    }

    public AbstractIdService(String idType) {
        this.idType = IdType.parse(idType);
    }

    public void init(){
        machineId = machineIdProvider.getMachineId();

        if(idMeta == null){
            idMeta = IdMetaFactory.createIdMeta(idType);
            type = idType.value();

        }else {
            if (idMeta.getTimeBytes() == 30){
                type = 0;
            }else if (idMeta.getTimeBytes() == 40){
                type = 1;
            }else {
                throw new IllegalArgumentException("不正确的idMeta");
            }

        }

        idConvert = new IdConvertImpl(idMeta);
    }


    @Override
    public long genId() {
        Id id = new Id();
        id.setMachineId(machineId);
        id.setGenMethod(genMethod);
        id.setType(type);
        id.setVersion(version);

        populate(id);

        return idConvert.convert(id);
    }

    abstract void populate(Id id);

    @Override
    public Id expId(long id) {
        return idConvert.prase(id);
    }

    @Override
    public long makeId(long seq, long time) {
        return makeId(machineId,seq,time);
    }

    @Override
    public long makeId(long machineId, long seq, long time) {
        return makeId(machineId,genMethod,seq,time);
    }

    @Override
    public long makeId(long machineId, long genMethod, long seq, long time) {
        return makeId(machineId,genMethod,type,seq,time);
    }

    @Override
    public long makeId(long machineId, long genMethod, long type, long seq, long time) {
        return makeId(machineId,genMethod,type,version,seq,time);
    }

    @Override
    public long makeId(long machineId, long genMethod, long type, long version, long seq, long time) {
        Id id = new Id();
        id.setVersion(version);
        id.setType(type);
        id.setGenMethod(genMethod);
        id.setMachineId(machineId);
        id.setTime(time);
        id.setSeq(seq);
        return idConvert.convert(id);
    }

    @Override
    public Date transTime(long time) {
        Date date = null;
        if (IdType.parse(type) == IdType.MAX_PEAK){
            date = new Date((time + TimeUtils.EPOCH) * 1000);
        }else if (IdType.parse(type) == IdType.MIN_GRANULARITY){
            date = new Date(time + TimeUtils.EPOCH);
        }

        return date;
    }

    public long getMachineId() {
        return machineId;
    }

    public void setMachineId(long machineId) {
        this.machineId = machineId;
    }

    public long getGenMethod() {
        return genMethod;
    }

    public void setGenMethod(long genMethod) {
        this.genMethod = genMethod;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

    public IdType getIdType() {
        return idType;
    }

    public void setIdType(IdType idType) {
        this.idType = idType;
    }

    public IdMeta getIdMeta() {
        return idMeta;
    }

    public void setIdMeta(IdMeta idMeta) {
        this.idMeta = idMeta;
    }

    public IdConvert getIdConvert() {
        return idConvert;
    }

    public void setIdConvert(IdConvert idConvert) {
        this.idConvert = idConvert;
    }

    public MachineIdProvider getMachineIdProvider() {
        return machineIdProvider;
    }

    public void setMachineIdProvider(MachineIdProvider machineIdProvider) {
        this.machineIdProvider = machineIdProvider;
    }
}
