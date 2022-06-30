package com.RESTfullCRUD.BasicCRUD.service;

import com.RESTfullCRUD.BasicCRUD.entity.Product;

import java.util.List;

public interface ProductService {


    /**
     * @param product Product object
     * @return Product
     */
    public Product addProduct(Product product) throws Exception;

    /**
     *
     * @return List of Product
     * @throws Exception e
     */
    List<Product> fetchProducts() throws Exception;

    /**
     *
     * @param productId id
     * @return Product
     */
    Product fetchProductById(Long productId);

    public void removeProductById(Long productId);

    public List<Product> fetchProductByName(String prodName);

    public Product updateProduct(Long productId, Product product);

//    Object fetchProductsWithCondition(String vendorName, String city);

    List<Object> fetchProductsWithConditions(String vendorName, String city);
}
