package com.RESTfullCRUD.BasicCRUD.service.impl;

import com.RESTfullCRUD.BasicCRUD.entity.Product;
import com.RESTfullCRUD.BasicCRUD.entity.User;
import com.RESTfullCRUD.BasicCRUD.exceptions.CustomException;
import com.RESTfullCRUD.BasicCRUD.repository.ProductRepository;
//import com.RESTfullCRUD.BasicCRUD.repository.VendorRepository;
import com.RESTfullCRUD.BasicCRUD.dto.responseDTO.ProductAndVendorDTO;
import com.RESTfullCRUD.BasicCRUD.repository.UserRepository;
import com.RESTfullCRUD.BasicCRUD.service.ProductService;
import com.RESTfullCRUD.BasicCRUD.utils.JwtTokenUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Log4j2
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public Product addProduct(Product product, String token) throws Exception {
        String vendorName = jwtTokenUtil.getUsernameFromToken(token.substring(7));
        product.setVendor(userRepository.findMatchedUser(vendorName));
        return productRepository.save(product);
    }

    @Override
    public List<Product> fetchProducts() throws Exception {
        try {
            List<Product> productList;
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
    public void removeProductById(Long productId, String token) throws CustomException {
        String vendorName = jwtTokenUtil.getUsernameFromToken(token.substring(7));
        String vendorNameFromDB = productRepository.getVendorName(productId);
        if((vendorNameFromDB.equals(vendorName))){
            productRepository.deleteById(productId);
        }else {
            throw new CustomException("User not Authorized to remove product");
        }
    }

    @Override
    public List<Product> fetchProductByName(String prodName) {
        return productRepository.findByProdName(prodName);
    }

    @Override
    public Product updateProduct(Long productId, Product product, String token) throws CustomException {
        Product oldProd = productRepository.findById(productId).get();
        String vendorName = jwtTokenUtil.getUsernameFromToken(token.substring(7));
        if(oldProd.getVendor().getUserName().equals(vendorName)){
            if(Objects.nonNull(product.getProdName()) && !"".equalsIgnoreCase(product.getProdName())){
                oldProd.setProdName(product.getProdName());
            }
            if(Objects.nonNull(product.getProdType()) && !"".equalsIgnoreCase(product.getProdType())){
                oldProd.setProdType(product.getProdType());
            }
            if(Objects.nonNull(product.getPrice()) && product.getPrice() != 0.0){
                oldProd.setPrice(product.getPrice());
            }
            if(Objects.nonNull(product.getVendor()) && !ObjectUtils.isEmpty(product.getVendor())){
                throw new CustomException("Can't Change vendor Details. Try Again!!");
            }
            return productRepository.save(oldProd);
        }
        throw new CustomException("This Vendor is not Authorized to change/update this product");
    }

    @Override
    public List<ProductAndVendorDTO> fetchProductsWithConditions(String userName, String city) {
        System.out.println(productRepository.findWithVendorAndCity(userName,city));
        return productRepository.findWithVendorAndCity(userName,city);
    }

//    @Override
//    public List<Object> fetchProductsWithConditions(FetchProductConditionsRequest fetchProductConditionsRequest) {
//        return productRepository.findWithVendorAndCity(fetchProductConditionsRequest.getVendorName(),fetchProductConditionsRequest.getCity());
//    }
}
