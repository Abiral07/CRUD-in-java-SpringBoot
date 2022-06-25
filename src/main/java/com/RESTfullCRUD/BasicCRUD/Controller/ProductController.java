package com.RESTfullCRUD.BasicCRUD.Controller;

import com.RESTfullCRUD.BasicCRUD.entity.Product;
import com.RESTfullCRUD.BasicCRUD.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);


    @PostMapping("/addproduct")
    public Product addProduct(@Valid @RequestBody Product product){
        LOGGER.info("Inside addproduct of productcontroller ........logging info..");
        return productService.addProduct(product);
    }

    @GetMapping("/getproduct")
    public List<Product> fetchProducts(){
        return  productService.fetchProducts();
    }

    @GetMapping("/getproduct/{id}")
    public Product fetchProductById(@PathVariable("id") Long productId){
        return  productService.fetchProductById(productId);
    }

    @GetMapping("/getproduct/name/{name}")
    public Product fetchProductByName(@PathVariable("name") String prodName){
        return  productService.fetchProductByName(prodName);
    }

    @DeleteMapping("/removeproduct/{id}")
    public String removeProductById(@PathVariable("id") Long productId){
        productService.removeProductById(productId);
        return "Product deleted Sucess!!!";
    }

    @PutMapping("/updateproduct/{id}")
    public Product updateProduct(@PathVariable("id") Long productId,@RequestBody Product product){
        return productService.updateProduct(productId, product);
    }
}
