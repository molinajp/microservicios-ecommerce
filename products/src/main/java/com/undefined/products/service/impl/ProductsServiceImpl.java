package com.undefined.products.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.undefined.products.dto.BoughtProductsDTO;
import com.undefined.products.dto.ProductsDTO;
import com.undefined.products.exception.ProductExcepcion;
import com.undefined.products.model.Products;
import com.undefined.products.repository.ProductsRepository;
import com.undefined.products.service.ProductsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductsServiceImpl implements ProductsService {

    private final ProductsRepository productsRepository;

    @Override
    public List<ProductsDTO> findAllProducts() {
        List<Products> response = productsRepository.findAll();
        return response.stream().map(ProductsDTO::productsToDTO).collect(Collectors.toList());
    }

    @Override
    public String updateProducts(List<BoughtProductsDTO> boughtProducts) throws ProductExcepcion {
        boolean saveInDB = false;

        for (int i = 0; i < boughtProducts.size(); i++) {
            Optional<Products> response = productsRepository.findById(boughtProducts.get(i).getId());
            if (response.isPresent()) {
                if (response.get().getStock() < boughtProducts.get(i).getStock()) {
                    log.error("MS Products - Fail to send incorrect stock value");
                    throw new ProductExcepcion(
                            "MS Products - Fail update product stock: {} " + response.get().getDescription());
                } else {
                    saveInDB = true;
                }
            } else {
                log.error("MS Products - Fail to send incorrect id");
                throw new ProductExcepcion(
                        "MS Products - Fail find product id: {} " + boughtProducts.get(i).getId());
            }
        }
        if (saveInDB) {
            for (int i = 0; i < boughtProducts.size(); i++) {
                Products productToUpdate = productsRepository.findById(boughtProducts.get(i).getId()).get();
                productToUpdate.setStock(productToUpdate.getStock() - boughtProducts.get(i).getStock());
                productsRepository.save(productToUpdate);
            }

        }
        return "MS Products - OK - Products update";
    }

    @Override
    public ProductsDTO findByIdProduct(Long id) {
        Optional<Products> response = productsRepository.findById(id);
        if (response.isPresent()) {
            return ProductsDTO.productsToDTO(response.get());
        } else {
            return null;
        }
    }

    @Override
    public List<ProductsDTO> findProductsByList(List<Long> listToProducts) {
        List<ProductsDTO> newProductsList = new ArrayList<>();
        for (int i = 0; i < listToProducts.size(); i++) {
            Optional<Products> product = productsRepository.findById(listToProducts.get(i));
            if (product.isPresent()) {
                newProductsList.add(ProductsDTO.productsToDTO(product.get()));
            }

        }
        return newProductsList;

    }

}
