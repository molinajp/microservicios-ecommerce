package com.undefined.backendforfrontend.feignclients;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.undefined.backendforfrontend.dto.BoughtProductsDTO;
import com.undefined.backendforfrontend.dto.ProductDTO;

@FeignClient(name = "products-service", path = "${products-service.base-path}")
public interface ProductsAppClient {

    @GetMapping("/products")
    public List<ProductDTO> getAllProducts();

    @GetMapping("/list-products")
    public List<ProductDTO> findProductsByList(@RequestParam List<Long> listToProducts);

    @PutMapping("/bought-products")
    public String updateProducts(@RequestBody List<BoughtProductsDTO> boughtProducts);

    @GetMapping("/actuator/health")
    Map<String, String> checkHealth();

    @GetMapping("/products/{id}")
    public ProductDTO findByIdProduct(@PathVariable Long id);

}