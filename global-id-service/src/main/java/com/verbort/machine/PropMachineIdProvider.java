package com.verbort.machine;

public class PropMachineIdProvider implements MachineIdProvider {
    private long machineId;

    public void setMachineId(long machineId) {
        this.machineId = machineId;
    }

    @Override
    public long getMachineId() {
        return machineId;
    }
}
