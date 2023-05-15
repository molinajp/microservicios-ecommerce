package com.undefined.vault.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Configuration
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigFromYaml {
    
    @Value("${data.uri}")
    private String vaultURI;
    @Value("${data.token}")
    private String token;
    @Value("${data.mysqlUser}")
    private String mysqlUser;
    @Value("${data.mysqlPass}")
    private String mysqlPass;
    @Value("${data.mongoUser}")
    private String mongoUser;
    @Value("${data.mongoPass}")
    private String mongoPass;

}
