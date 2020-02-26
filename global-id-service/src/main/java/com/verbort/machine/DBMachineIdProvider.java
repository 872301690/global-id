package com.verbort.machine;

import com.verbort.util.IpUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

public class DBMachineIdProvider implements MachineIdProvider {
    public long machineId;
    private JdbcTemplate jdbcTemplete;

    public void init(){
        String ip = IpUtils.getLocalHostIp();
        if(StringUtils.isEmpty(ip)){
            throw new IllegalArgumentException("本机地址不能为空");
        }

        Long id = null;
        try{
            id = jdbcTemplete.queryForObject("select  id from  DB_MACHINE_ID_PROVIDER where ip = ?",new Object[]{ip},Long.class);
        }catch (EmptyResultDataAccessException e){

        }

        if (id != null){
            machineId = id;
            return;
        }

        int count = jdbcTemplete.update("update DB_MACHINE_ID_PROVIDER set ip = ? where ip is null limit 1",ip);
        if (count != 1){
            throw new IllegalArgumentException("更新数据库失败");
        }

        id = jdbcTemplete.queryForObject("select  id from  DB_MACHINE_ID_PROVIDER where ip = ?",new Object[]{ip},Long.class);

        if (id == null){
            throw new IllegalArgumentException("id 依旧为空");
        }

        machineId = id;

    }
    public void setMachineId(long machineId) {
        this.machineId = machineId;
    }

    public JdbcTemplate getJdbcTemplete() {
        return jdbcTemplete;
    }

    public void setJdbcTemplete(JdbcTemplate jdbcTemplete) {
        this.jdbcTemplete = jdbcTemplete;
    }

    @Override
    public long getMachineId() {
        return machineId;
    }
}
