package com.verbort;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.verbort.machine.DBMachineIdProvider;
import com.verbort.machine.IpMachineProvider;
import com.verbort.machine.PropMachineIdProvider;
import com.verbort.service.IdService;
import com.verbort.util.IpUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.beans.PropertyVetoException;

public class IdServiceFactoryBean implements FactoryBean<IdService> {


    public enum Type{
        DB,IP,PROP
    }
    private Type provideType;

    private long genMethod = -1;
    private long mechineId = -1;
    private long version = -1;
    private long type = -1;


    private String url;
    private String driver;
    private String user;
    private String password;

    private String ips;

    private IdService idService;

    public void init(){
        if (provideType == null){
            throw new IllegalArgumentException("provideType can not be null");
        }

        switch (provideType){
            case DB:
                idService = constructDBIdService(url,driver,user,password);
                break;
             case IP:
                 idService = constructIPIdService(ips);
                break;
            case PROP:
                idService = constructPropIdService(mechineId);
                break;
        }


    }

    private IdService constructIPIdService(String ips) {
        IpMachineProvider ipMachineProvider = new IpMachineProvider();
        ipMachineProvider.setIps(ips);
        ipMachineProvider.init();

        IdServiceImpl idService = new IdServiceImpl();
        idService.setMachineIdProvider(ipMachineProvider);
        idService.init();

        if (genMethod != -1)
            idService.setGenMethod(genMethod);
        if (type != -1)
            idService.setGenMethod(type);
        if (version != -1)
            idService.setGenMethod(version);

        return idService;
    }

    private IdService constructPropIdService(long mechineId) {
        PropMachineIdProvider propMachineIdProvider = new PropMachineIdProvider();
        propMachineIdProvider.setMachineId(mechineId);


        IdServiceImpl idService = new IdServiceImpl();
        idService.setMachineIdProvider(propMachineIdProvider);
        idService.init();

        if (genMethod != -1)
            idService.setGenMethod(genMethod);
        if (type != -1)
            idService.setGenMethod(type);
        if (version != -1)
            idService.setGenMethod(version);

        return idService;
    }

    private IdService constructDBIdService(String url, String driver, String user, String password)  {

        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setPassword(password);
        dataSource.setUser(user);
        try {
            dataSource.setDriverClass(driver);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        dataSource.setJdbcUrl(url);

        dataSource.setMinPoolSize(5);
        dataSource.setMaxPoolSize(30);
        dataSource.setIdleConnectionTestPeriod(20);
        dataSource.setMaxIdleTime(25);
        dataSource.setBreakAfterAcquireFailure(false);
        dataSource.setCheckoutTimeout(3000);
        dataSource.setAcquireRetryAttempts(50);
        dataSource.setAcquireRetryDelay(1000);

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);


        DBMachineIdProvider dbMachineIdProvider = new DBMachineIdProvider();
        dbMachineIdProvider.setJdbcTemplete(jdbcTemplate);
        dbMachineIdProvider.init();

        IdServiceImpl idService = new IdServiceImpl();
        idService.setMachineIdProvider(dbMachineIdProvider);
        idService.init();

        if (genMethod != -1)
            idService.setGenMethod(genMethod);
        if (type != -1)
            idService.setGenMethod(type);
        if (version != -1)
            idService.setGenMethod(version);

        return idService;
    }

    @Override
    public IdService getObject() throws Exception {
        return idService;
    }

    @Override
    public Class<?> getObjectType() {
        return idService.getClass();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public Type getProvideType() {
        return provideType;
    }

    public void setProvideType(Type provideType) {
        this.provideType = provideType;
    }

    public long getGenMethod() {
        return genMethod;
    }

    public void setGenMethod(long genMethod) {
        this.genMethod = genMethod;
    }

    public long getMechineId() {
        return mechineId;
    }

    public void setMechineId(long mechineId) {
        this.mechineId = mechineId;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIps() {
        return ips;
    }

    public void setIps(String ips) {
        this.ips = ips;
    }

    public IdService getIdService() {
        return idService;
    }

    public void setIdService(IdService idService) {
        this.idService = idService;
    }
}
