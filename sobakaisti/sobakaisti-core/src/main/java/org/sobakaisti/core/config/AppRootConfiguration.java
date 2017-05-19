package org.sobakaisti.core.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author jelles
 *
 */
@Configuration
@ComponentScan({"org.sobakaisti.core"})
@EnableTransactionManagement
//@Import(AppSecurityConfiguration.class)
public class AppRootConfiguration {
	
	@Bean(name="dataSource")
	public DataSource getDataSource(){
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/sobakaisti?useUnicode=yes&characterEncoding=UTF-8");	// 3306
		dataSource.setUsername("root");
		dataSource.setPassword("");	//root 
		return dataSource;
	}
	@Autowired
	@Bean(name="sessionFactory")
	public SessionFactory getSessionFactory(DataSource dataSource){
		LocalSessionFactoryBuilder sessionFactory = new LocalSessionFactoryBuilder(dataSource);
		sessionFactory.scanPackages("org.sobakaisti.core.model");
		sessionFactory.addProperties(getHibernateProperties());
		return sessionFactory.buildSessionFactory();
	}
	
	@Autowired
	@Bean(name="transactionManager")
	HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory){
		HibernateTransactionManager txManager = new HibernateTransactionManager(sessionFactory);
		return txManager;
	}
	
//	@Autowired
//	@Bean(name="accountManagerDao")
//	public AccountManagerDao getAccuntManagerDao(SessionFactory sessionFactory){
//		AccountManagerDao accountManagment = new AccountManagerDaotImpl(sessionFactory);
//		return accountManagment;
//	}
	
	private Properties getHibernateProperties(){
		Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		hibernateProperties.setProperty("hibernate.connection.CharSet", "utf8");
		hibernateProperties.setProperty("hibernate.connection.characterEncoding", "utf8");
		hibernateProperties.setProperty("hibernate.connection.useUnicode", "true");
		return hibernateProperties;	
	}	
}
