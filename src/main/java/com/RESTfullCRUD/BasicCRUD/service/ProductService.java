package com.RESTfullCRUD.BasicCRUD.service;

import com.RESTfullCRUD.BasicCRUD.entity.Product;
import com.RESTfullCRUD.BasicCRUD.dto.responseDTO.ProductAndVendorDTO;
import com.RESTfullCRUD.BasicCRUD.exceptions.CustomException;

import java.util.List;

public interface ProductService {


    /**
     * @param product Product object
     * @return Product
     */
    Product addProduct(Product product, String token) throws Exception;
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

    void removeProductById(Long productId, String token) throws CustomException;

    List<Product> fetchProductByName(String prodName);

    Product updateProduct(Long productId, Product product, String token) throws CustomException;
    List<ProductAndVendorDTO> fetchProductsWithConditions(String vendorName, String city);

//    List<Object> fetchProductsWithConditions(FetchProductConditionsRequest fetchProductConditionsRequest);
}
