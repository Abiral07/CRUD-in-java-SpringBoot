package com.RESTfullCRUD.BasicCRUD.repository;

import com.RESTfullCRUD.BasicCRUD.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductReprository extends JpaRepository<Product, Long> {

    public List<Product> findByProdName(String prodName);
}
