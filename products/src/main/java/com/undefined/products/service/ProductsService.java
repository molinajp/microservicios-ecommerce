package com.undefined.products.service;

import java.util.List;

import com.undefined.products.dto.BoughtProductsDTO;
import com.undefined.products.dto.ProductsDTO;
import com.undefined.products.exception.ProductExcepcion;

public interface ProductsService {
    public List<ProductsDTO> findAllProducts();

    public String updateProducts(List<BoughtProductsDTO> boughtProducts) throws ProductExcepcion;

    public ProductsDTO findByIdProduct(Long id);

    public List<ProductsDTO> findProductsByList(List<Long> listToProducts);

}
