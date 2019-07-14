package com.hongsin.mail.config.database;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.aspectj.apache.bcel.util.ClassLoaderRepository;
import org.aspectj.apache.bcel.util.ClassLoaderRepository.SoftHashMap;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

@Configuration
@AutoConfigureAfter({DataSourceConfiguration.class})
public class MybatisConfiguration extends MybatisAutoConfiguration {

	@Resource(name="masterDataSource")
	private DataSource masterDataSource;
	
	@Resource(name="slaveDataSource")
	private DataSource slaveDataSource;
	
	@Bean(name="sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory() throws Exception{
		return super.sqlSessionFactory(roundRobinDataSourceProxy());
	}
	
	@SuppressWarnings("unchecked")
	public AbstractRoutingDataSource roundRobinDataSourceProxy() {
		ReadWriteSplitRoutingDataSource proxy = new ReadWriteSplitRoutingDataSource();
		SoftHashMap targetDataSources =  new ClassLoaderRepository.SoftHashMap(); 
		targetDataSources.put(DataBaseContextHolder.DataBaseType.MASTER, masterDataSource);
		targetDataSources.put(DataBaseContextHolder.DataBaseType.SLAVE, slaveDataSource);
		//默认数据源
		proxy.setDefaultTargetDataSource(masterDataSource);
		//装入主从两个数据源
		proxy.setTargetDataSources(targetDataSources);
		return proxy;
	}
}
