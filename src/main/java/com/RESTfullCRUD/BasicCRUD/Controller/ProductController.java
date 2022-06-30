package com.RESTfullCRUD.BasicCRUD.Controller;

import com.RESTfullCRUD.BasicCRUD.constant.PathConstant;
import com.RESTfullCRUD.BasicCRUD.dto.ProductDto;
import com.RESTfullCRUD.BasicCRUD.entity.Product;
import com.RESTfullCRUD.BasicCRUD.entityToDto.ProductConvertor;
import com.RESTfullCRUD.BasicCRUD.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductConvertor productConvertor;
    private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @PostMapping(PathConstant.ADD_PRODUCT)
    public Product addProduct(@Valid @RequestBody Product product) throws Exception {
        LOGGER.info("Inside addproduct of productcontroller ........logging info..");
        return productService.addProduct(product);
    }

//    @PostMapping("/getproduct")
//    public ResponseEntity<Object> fetchProducts() throws Exception {
//        return new ResponseEntity<>(productService.fetchProducts(), HttpStatus.ACCEPTED);
//    }

    @PostMapping(PathConstant.GET_PRODUCT)
    public List<ProductDto> fetchProducts() throws Exception {
        return productConvertor.entityToDto(productService.fetchProducts());
    }

    @GetMapping("/all")
    public List<Product> getAllProduct() throws Exception {
        return productService.fetchProducts();
    }

    @GetMapping(PathConstant.GET_PRODUCT+"/{id}")
    public ProductDto fetchProductById(@PathVariable("id") Long productId){
        return productConvertor.entityToDto(productService.fetchProductById(productId));
    }

    @GetMapping(PathConstant.GET_PRODUCT+"/name/{name}")
    public List<Product> fetchProductByName(@PathVariable("name") String prodName){
        return  productService.fetchProductByName(prodName);
    }

    @DeleteMapping(PathConstant.REMOVE_PRODUCT+"/{id}")
    public String removeProductById(@PathVariable("id") Long productId){
        productService.removeProductById(productId);
        return "Product deleted Sucess!!!";
    }

    @PutMapping(PathConstant.UPDATE_PRODUCT+"/{id}")
    public Product updateProduct(@PathVariable("id") Long productId,@RequestBody Product product){
        return productService.updateProduct(productId, product);
    }

//    @PostMapping("/getproductOf")
//    public ResponseEntity<Object> fetchProductsWithCondition(@RequestParam("vendor") String vendorName, @RequestParam("city") String city) throws Exception {
//        return new ResponseEntity<>(productService.fetchProductsWithCondition(vendorName, city));
//    }
    @GetMapping("/getproductOf")
    public List<Object> fetchProductsWithCondition(@RequestParam("vendor") String vendorName, @RequestParam("city") String city) throws Exception {
        return productService.fetchProductsWithConditions(vendorName, city);
    }
}
