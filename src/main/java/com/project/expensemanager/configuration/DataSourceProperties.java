package com.project.expensemanager.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "database")
@Data
public class DataSourceProperties {
    String url;
    String username;
    String password;
    String driver;
}
