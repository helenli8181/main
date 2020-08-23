package com.rocketden.main;

import java.net.URI;
import java.net.URISyntaxException;

import io.github.cdimascio.dotenv.Dotenv;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainConfig {

  @Bean
  public BasicDataSource dataSource() throws URISyntaxException {
    Dotenv dotenv = Dotenv.load();
    URI dbUri = new URI(dotenv.get("REACT_APP_CLEARDB_DATABASE_URL"));

    String username = dbUri.getUserInfo().split(":")[0];
    String password = dbUri.getUserInfo().split(":")[1];
    String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

    BasicDataSource basicDataSource = new BasicDataSource();
    basicDataSource.setUrl(dbUrl);
    basicDataSource.setUsername(username);
    basicDataSource.setPassword(password);

    return basicDataSource;
  }
}