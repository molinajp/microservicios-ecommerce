package com.undefined.vault;

import java.net.URI;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.core.VaultTemplate;

import com.undefined.vault.config.ConfigFromYaml;
import com.undefined.vault.dto.Data;
import com.undefined.vault.dto.VaultSecret;

@SpringBootApplication
public class VaultApplication {

	public static void main(String[] args) {
		SpringApplication.run(VaultApplication.class, args);
	}
	
	@Bean
    public CommandLineRunner run(ConfigFromYaml config) {
        return args -> {
            VaultTemplate vaultTemplate = new VaultTemplate(VaultEndpoint.from(new URI(config.getVaultURI())), 
                    new TokenAuthentication(config.getToken()));
            VaultSecret credentials = new VaultSecret(new Data(config.getMysqlUser(), config.getMysqlPass(),
                                                            config.getMongoUser(), config.getMongoPass()));
            vaultTemplate.write("secret/data/application", credentials);
        };
    }

}
