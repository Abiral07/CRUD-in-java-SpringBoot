package com.RESTfullCRUD.BasicCRUD.repository;

import com.RESTfullCRUD.BasicCRUD.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReprository extends JpaRepository<Product, Long> {

    public Product findOneByProdNameIgnoreCase(String prodName);

}
