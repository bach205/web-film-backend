/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huybach;

import com.huybach.filter.AuthorizationFilter;
import com.huybach.filter.SessionCheckFilter;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterRegistration;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 *
 * @author HOME PC
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.huybach")
public class AppConfig implements WebMvcConfigurer {
    
    //khoi tao datasource chua thong tin de ket noi toi database
    @Bean
    public DataSource dataSource() throws ClassNotFoundException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSource.setUrl("jdbc:sqlserver://localhost:1433;databaseName=webfilm;encrypt=true;trustServerCertificate=true");
        dataSource.setUsername("sa");
        dataSource.setPassword("123");
        return dataSource;
    }

    //khoi tao class de thuc hien cac cau lenh sql
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    //chuyen du lieu request cua json sang object
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter());

    }

    //cau hinh cors
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);

    }
}
