package com.RESTfullCRUD.BasicCRUD.service;

import com.RESTfullCRUD.BasicCRUD.entity.Product;
import com.RESTfullCRUD.BasicCRUD.repository.ProductReprository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductReprository productReprository;
    @Override
    public Product addProduct(Product product) {
        return productReprository.save(product);
    }

    @Override
    public List<Product> fetchProducts() {
        return productReprository.findAll();
    }

    @Override
    public Product fetchProductById(Long productId) {
        return productReprository.findById(productId).get();
    }

    @Override
    public Void removeProductById(Long productId) {
        productReprository.deleteById(productId);
        return null;
    }

    @Override
    public Product fetchProductByName(String prodName) {
        return productReprository.findOneByProdNameIgnoreCase(prodName);
    }

    @Override
    public Product updateProduct(Long productId, Product product) {
        Product oldProd = productReprository.findById(productId).get();

        if(Objects.nonNull(product.getProdName()) && !"".equalsIgnoreCase(product.getProdName())){
            oldProd.setProdName(product.getProdName());
        }
        if(Objects.nonNull(product.getProdType()) && !"".equalsIgnoreCase(product.getProdType())){
            oldProd.setProdType(product.getProdType());
        }
//        to-do: find how to compare double to 0.0
        System.out.println(product.getPrice());
        if(Objects.nonNull(product.getPrice()) && product.getPrice() != 0.0){
            oldProd.setPrice(product.getPrice());
        }

        return productReprository.save(oldProd);
    }

}
