package com.test.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.test.service"})
@EnableJpaRepositories(basePackages = "com.test.repository")
public class DatabaseConfig {

        @Bean
        public DataSource dataSource() {

                DriverManagerDataSource ds = new DriverManagerDataSource();

                ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
                ds.setUrl("jdbc:mysql://127.0.0.1:3306/coursServlet");
                ds.setUsername("tomefy");
                ds.setPassword("etu003948");

                return ds;
        }

        @Bean
        public LocalContainerEntityManagerFactoryBean entityManagerFactory(
                        DataSource dataSource) {

                LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();

                emf.setDataSource(dataSource);
                emf.setPackagesToScan("com.test.model");

                HibernateJpaVendorAdapter vendor = new HibernateJpaVendorAdapter();

                vendor.setGenerateDdl(true);
                vendor.setShowSql(true);

                emf.setJpaVendorAdapter(vendor);

                Properties properties = new Properties();

                properties.put("hibernate.hbm2ddl.auto", "update");
                properties.put("hibernate.dialect",
                                "org.hibernate.dialect.MySQLDialect");
                properties.put("hibernate.format_sql", "true");

                emf.setJpaProperties(properties);

                return emf;
        }

        @Bean
        public PlatformTransactionManager transactionManager(
                        LocalContainerEntityManagerFactoryBean emf) {

                JpaTransactionManager tx = new JpaTransactionManager();
                tx.setEntityManagerFactory(emf.getObject());

                return tx;
        }

}