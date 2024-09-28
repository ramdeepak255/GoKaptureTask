package com.example.todoapp.configurations;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    @Primary
    public DataSource dataSourceForTodoappPostgres1() {
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://localhost:5432/MyDatabase")
                .username("postgres")  // Change this to your PostgreSQL username
                .password("@RAMmanoj1")  // Change this to your PostgreSQL password
                .build();
    }
}
