package com.example.nxttrendz1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;

import com.example.nxttrendz1.model.Product;
import com.example.nxttrendz1.repository.ProductJpaRepository;
import com.example.nxttrendz1.repository.ProductRepository;

@Service
public class ProductJpaService implements ProductRepository {

    @Autowired
    private ProductJpaRepository db;

    @Override
    public ArrayList<Product> getProducts() {
        return (ArrayList<Product>) db.findAll();
    }

    @Override
    public Product getProductById(int productId) {
        try {
            Product product = db.findById(productId).get();
            return product;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Product addProduct(Product product) {
        try {
            db.save(product);

            return product;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Product updateProduct(int productId, Product product) {
        try {
            Product newOne = db.findById(productId).get();
            if (product.getProductName() != null) {
                newOne.setProductName(product.getProductName());
            }
            if (product.getPrice() != 0) {
                newOne.setPrice(product.getPrice());
            }

            db.save(newOne);

            return newOne;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteProduct(int productId) {
        try {
            db.deleteById(productId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }
}