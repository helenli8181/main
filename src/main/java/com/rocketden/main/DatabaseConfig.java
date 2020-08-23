package com.rocketden.main;

import java.net.URI;
import java.net.URISyntaxException;

import javax.persistence.EntityManagerFactory;

import com.rocketden.main.dao.problem.ProblemRepository;

import io.github.cdimascio.dotenv.Dotenv;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackageClasses=ProblemRepository.class)
public class DatabaseConfig {

  private static Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws URISyntaxException {
    Dotenv dotenv = Dotenv.load();
    URI dbUri = new URI(dotenv.get("REACT_APP_CLEARDB_DATABASE_URL"));

    String username = dbUri.getUserInfo().split(":")[0];
    String password = dbUri.getUserInfo().split(":")[1];
    String dbUrl = "jdbc:mysql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

    BasicDataSource basicDataSource = new BasicDataSource();
    basicDataSource.setUrl(dbUrl);
    basicDataSource.setUsername(username);
    basicDataSource.setPassword(password);

    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
    entityManagerFactoryBean.setDataSource(basicDataSource);
    entityManagerFactoryBean.setPackagesToScan("com.rocketden.main.dao.problem");
    //additional config of factory

    return entityManagerFactoryBean;
  }

  @Bean(name = "transactionManager")
  public PlatformTransactionManager jpaTransactionManager(EntityManagerFactory entityManagerFactory) throws URISyntaxException {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory);
    return transactionManager;
  }
}