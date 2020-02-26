package com.verbort.bean;

import lombok.Data;

@Data
public class IdMeta {
    private byte machineIdBytes;
    private byte seqBytes;
    private byte timeBytes;
    private byte genMethodBytes;
    private byte typeBytes;
    private byte versionBytes;

    public IdMeta(byte machineIdBytes, byte seqBytes, byte timeBytes, byte genMethodBytes, byte typeBytes, byte versionBytes) {
        this.machineIdBytes = machineIdBytes;
        this.seqBytes = seqBytes;
        this.timeBytes = timeBytes;
        this.genMethodBytes = genMethodBytes;
        this.typeBytes = typeBytes;
        this.versionBytes = versionBytes;
    }

    public long getMachineIdBytesMask(){
        return -1 ^ -1 << machineIdBytes;
    }

    public long getSeqBytesPos(){
        return machineIdBytes;
    }

    public long getSeqBytesPosMask(){
        return -1 ^ -1 << seqBytes;
    }
    public long getTimeBytesPos(){
        return machineIdBytes + seqBytes;
    }

    public long getTimeBytesPosMask(){
        return  -1 ^ -1 << timeBytes;
    }
    public long getGenMethodBytesPos(){
        return machineIdBytes + seqBytes + timeBytes;
    }

    public long getGenMethodBytesPosMask(){
        return -1 ^ -1 << genMethodBytes;
    }
    public long getTypeBytesPos(){
        return machineIdBytes + seqBytes + timeBytes + genMethodBytes;
    }

    public long getTypeBytesPosMask(){
        return -1 ^ -1 << typeBytes;
    }

    public long getVersionBytesPos(){
        return machineIdBytes + seqBytes + timeBytes + genMethodBytes + versionBytes;
    }

    public long getVersionBytesPosMask(){
        return -1 ^ -1 << versionBytes;
    }



}
