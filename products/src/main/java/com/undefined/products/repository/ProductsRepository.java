package com.undefined.products.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.undefined.products.model.Products;

@Repository
public interface ProductsRepository extends MongoRepository<Products, Long> {

}
