package com.RESTfullCRUD.BasicCRUD.service.impl;

import com.RESTfullCRUD.BasicCRUD.entity.Product;
import com.RESTfullCRUD.BasicCRUD.repository.ProductRepository;
//import com.RESTfullCRUD.BasicCRUD.repository.VendorRepository;
import com.RESTfullCRUD.BasicCRUD.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Log4j2
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product addProduct(Product product) throws Exception {
        return productRepository.save(product);
    }

    @Override
    public List<Product> fetchProducts() throws Exception {
        try {
            List<Product> productList = new ArrayList<>();
            productList =  productRepository.findAll();
            if (!productList.isEmpty()) {
                return productList;
            } else {
                throw new Exception("Product list not found");
            }
        } catch (Exception e) {
            log.debug("fetchProducts: " + e);
            log.error("fetchProducts: " + e.getMessage());
            throw new Exception("", e);

        }
    }

    @Override
    public Product fetchProductById(Long productId) {
        return productRepository.findById(productId).get();
    }

    @Override
    public void removeProductById(Long productId) {
        productRepository.deleteById(productId);
//        return null;
    }

    @Override
    public List<Product> fetchProductByName(String prodName) {
        return productRepository.findByProdName(prodName);
    }

    @Override
    public Product updateProduct(Long productId, Product product) {
        Product oldProd = productRepository.findById(productId).get();

        if(Objects.nonNull(product.getProdName()) && !"".equalsIgnoreCase(product.getProdName())){
            oldProd.setProdName(product.getProdName());
        }
        if(Objects.nonNull(product.getProdType()) && !"".equalsIgnoreCase(product.getProdType())){
            oldProd.setProdType(product.getProdType());
        }
//        System.out.println(product.getPrice());
        if(Objects.nonNull(product.getPrice()) && product.getPrice() != 0.0){
            oldProd.setPrice(product.getPrice());
        }
        if(Objects.nonNull(product.getVendor()) && !ObjectUtils.isEmpty(product.getVendor())){
//            if(Objects.nonNull(product.getVendor().getVendorName()) && !"".equalsIgnoreCase(product.getVendor().getVendorName())){
//            }
                oldProd.setVendor(product.getVendor());
        }

        return productRepository.save(oldProd);
    }

    @Override
    public List<Object> fetchProductsWithConditions(String vendorName, String city) {
        return productRepository.findWithVendorAndCity(vendorName,city);
    }

//    @Override
//    public Object fetchProductsWithCondition(String vendorName, String city) {
//        return productRepository.findWithVendorAndCity(vendorName,city);
//    }


}
