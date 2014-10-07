/*
 * (c) Copyright 2014 Swisscom AG
 * All Rights Reserved.
 */
package com.zuehlke.carrera.bot.app;

import java.util.Arrays;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

/**
 * @author Nicolas Regez
 * @since 24.02.2014
 */
@Configuration
@EnableWebMvc
@EnableJpaRepositories
@EnableTransactionManagement
@ComponentScan("com.zuehlke.carrera.bot")
@PropertySource("classpath:application.properties")
public class SpringDemoWebApplication extends WebMvcConfigurerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(SpringDemoWebApplication.class);

    @Autowired
    ApplicationContext ctx;

    @Resource
    private Environment env;

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public UrlBasedViewResolver viewResolver() {
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setViewClass(JstlView.class);
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(emf);
        return tm;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
        emfb.setDataSource(ctx.getBean("spring-demo-db", DataSource.class));
        emfb.setPackagesToScan(new String[] {"com.zuehlke.carrera.bot.model"});
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setGenerateDdl(true);
        adapter.setDatabase(Database.MYSQL);
        adapter.setShowSql(true);
        emfb.setJpaVendorAdapter(adapter);
        emfb.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        emfb.setPersistenceUnitName("default");
        return emfb;
    }

    /**
     * @return Access to relational database system for local deployments
     */
    @Bean(name = "spring-demo-db")
    public DataSource dataSource() {
        boolean isCloud = Arrays.asList(ctx.getEnvironment().getActiveProfiles()).contains("cloud");

        if (isCloud) {
            String msg = "DataSource bean has not been auto-reconfigured."
                    + " Without, the application cannot exist in the Cloud.";
            logger.error(msg);
            throw new RuntimeException(msg);
        }

        logger.info("DataSource instance about to be created");
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(env.getRequiredProperty("db.driver"));
        ds.setUrl(env.getRequiredProperty("db.url"));
        ds.setUsername(env.getRequiredProperty("db.username"));
        ds.setPassword(env.getRequiredProperty("db.password"));
        return ds;
    }

}
