package com.RESTfullCRUD.BasicCRUD.service;

import com.RESTfullCRUD.BasicCRUD.entity.Product;

import java.util.List;

public interface ProductService {


    /**
     * @param product
     * @return Product
     */
    public Product addProduct(Product product) throws Exception;

    public List<Product> fetchProducts() throws Exception;

    public Product fetchProductById(Long productId);

    public void removeProductById(Long productId);

    public List<Product> fetchProductByName(String prodName);

    public Product updateProduct(Long productId, Product product);
}
