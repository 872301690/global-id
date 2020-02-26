package com.verbort.machine;

import com.verbort.util.IpUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class IpMachineProvider implements MachineIdProvider {
    private long machineId;
    private Map<String,Long> ipsMap = new HashMap<>();

    public void setMachineId(long machineId) {
        this.machineId = machineId;
    }
    public void setIps(String ips){
        String[] split = ips.split(",");
        for (int i = 0 ; i < split.length; i++){
            ipsMap.put(split[0],Long.valueOf(i));
        }
    }

    @Override
    public long getMachineId() {
        return machineId;
    }

    public void init(){
        String ip = IpUtils.getLocalHostIp();
        if(StringUtils.isEmpty(ip)){
            throw new IllegalArgumentException("本机地址不能为空");
        }

        Long machineId = ipsMap.get(ip);
        if (machineId == null){
            throw new IllegalArgumentException("本地ip未配置");
        }
        this.machineId = machineId;

    }
}
