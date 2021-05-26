package com.nrbswift.spring4web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jndi.JndiTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.NamingException;
import javax.sql.DataSource;


@EnableTransactionManagement
@Configuration
@ComponentScan({"com.nrbswift.spring4web.dao"})
public class DaoConfiguration {

    @Autowired
    private Environment environment;

    @Bean
    public DataSource dataSource() throws NamingException {
        return (DataSource) new JndiTemplate().lookup(environment.getRequiredProperty("jdbc.jndi.connection.name"));
    }

    @Bean
    public DataSourceTransactionManager transactionManager() throws NamingException {
        return new DataSourceTransactionManager(dataSource());
    }
}
