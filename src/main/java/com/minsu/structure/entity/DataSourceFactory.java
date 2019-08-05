package com.minsu.structure.entity;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import com.minsu.structure.util.PropertiesUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @author KOUSHENGRUI976
 * @date 2018/3/15
 */
public class DataSourceFactory implements FactoryBean<DataSource>, InitializingBean {

    public static final Log LOGGER = LogFactory.getLog(DataSourceFactory.class);

    private DataSource dataSource;

    @Override
    public void afterPropertiesSet() throws Exception {
        PropertiesUtils instance = PropertiesUtils.getInstance();

        String driverClassName = instance.getProperty("dataSource.driverClassName");
        String url = instance.getProperty("dataSource.url");
        String username = instance.getProperty("dataSource.username");

        String password = instance.getProperty("dataSource.password");

        int initialSize = instance.getProperty("dataSource.initialSize", 50);
        int maxActive = instance.getProperty("dataSource.maxActive", 100);
        int minActive = instance.getProperty("dataSource.minIdle", 20);
        boolean defaultAutoCommit = Boolean.parseBoolean(instance.getProperty("dataSource.defaultAutoCommit"));

        //WallConfig配置
        WallConfig config = new WallConfig();
        config.setDir(instance.getProperty("dataSource.dir", "META-INF/druid/wall/postgres"));
        config.setCommentAllow(Boolean.parseBoolean(instance.getProperty("dataSource.commentAllow", "true")));
        config.setTruncateAllow(Boolean.parseBoolean(instance.getProperty("dataSource.truncateAllow", "false")));
        config.setCreateTableAllow(Boolean.parseBoolean(instance.getProperty("dataSource.createTableAllow", "false")));
        config.setAlterTableAllow(Boolean.parseBoolean(instance.getProperty("dataSource.alterTableAllow", "false")));
        config.setDropTableAllow(Boolean.parseBoolean(instance.getProperty("dataSource.dropTableAllow", "false")));
        config.setMultiStatementAllow(Boolean.parseBoolean(instance.getProperty("dataSource.multiStatementAllow", "true")));
        config.setStrictSyntaxCheck(Boolean.parseBoolean(instance.getProperty("dataSource.strictSyntaxCheck", "false")));
//        config.setSelectLimit(10000);

        //WallFilter配置
        WallFilter filter = new WallFilter();
        filter.setDbType(instance.getProperty("dataSource.dbType", "postgresql"));
        filter.setLogViolation(Boolean.parseBoolean(instance.getProperty("dataSource.logViolation", "true")));
        filter.setThrowException(Boolean.parseBoolean(instance.getProperty("dataSource.throwException", "false")));
        filter.setConfig(config);

        //DruidDataSource配置
        DruidDataSource dds = new DruidDataSource();
        dds.setDriverClassName(driverClassName);
        dds.setUrl(url);
        dds.setUsername(username);
        dds.setPassword(password);
        dds.setDefaultAutoCommit(defaultAutoCommit);

        dds.setInitialSize(initialSize);
        dds.setMinIdle(minActive);
        dds.setMaxActive(maxActive);

        dds.setMaxWait(instance.getProperty("dataSource.maxWait", 5000));
        dds.setMaxWaitThreadCount(instance.getProperty("dataSource.maxWaitThreadCount", 50));

        dds.setTimeBetweenEvictionRunsMillis(instance.getProperty("dataSource.timeBetweenEvictionRunsMillis", 15000));
        dds.setMinEvictableIdleTimeMillis(instance.getProperty("dataSource.minEvictableIdleTimeMillis", 30000));
        dds.setMaxEvictableIdleTimeMillis(instance.getProperty("dataSource.maxEvictableIdleTimeMillis", 30000));

        dds.setTestWhileIdle(Boolean.parseBoolean(instance.getProperty("dataSource.testWhileIdle", "true")));
        dds.setValidationQuery(instance.getProperty("dataSource.validationQuery", "select 1"));

        dds.setPoolPreparedStatements(Boolean.parseBoolean(instance.getProperty("dataSource.poolPreparedStatements", "true")));
        dds.setMaxPoolPreparedStatementPerConnectionSize(instance.getProperty("dataSource.maxPoolPreparedStatementPerConnectionSize", 20));

        dds.setRemoveAbandoned(Boolean.parseBoolean(instance.getProperty("dataSource.removeAbandoned", "true")));
        dds.setRemoveAbandonedTimeoutMillis(instance.getProperty("dataSource.removeAbandonedTimeoutMillis", 300000));
        dds.setLogAbandoned(Boolean.parseBoolean(instance.getProperty("dataSource.logAbandoned", "true")));

        dds.setFilters(instance.getProperty("dataSource.filters", "stat"));
        dds.setProxyFilters(Arrays.asList(filter));
        dataSource = dds;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public DataSource getObject() throws Exception {
        return dataSource;
    }

    @Override
    public Class<?> getObjectType() {
        return (this.dataSource != null ? this.dataSource.getClass() : DataSource.class);
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}