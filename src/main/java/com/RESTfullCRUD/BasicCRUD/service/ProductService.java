package com.RESTfullCRUD.BasicCRUD.service;

import com.RESTfullCRUD.BasicCRUD.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    public Product addProduct(Product product);

    public List<Product> fetchProducts();

    public Product fetchProductById(Long productId);

    public Void removeProductById(Long productId);

    public Product fetchProductByName(String prodName);

    public Product updateProduct(Long productId, Product product);
}
