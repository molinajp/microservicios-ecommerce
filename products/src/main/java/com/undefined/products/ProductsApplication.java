package com.undefined.products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import com.undefined.products.utils.MongoBDInsert;

@SpringBootApplication
@EnableEurekaClient
public class ProductsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductsApplication.class, args);
        MongoBDInsert.insertData();
    }

}
