package com.RESTfullCRUD.BasicCRUD.Controller;

import com.RESTfullCRUD.BasicCRUD.constant.PathConstant;
import com.RESTfullCRUD.BasicCRUD.dto.CustomUserDetails;
import com.RESTfullCRUD.BasicCRUD.dto.responseDTO.ProductDto;
import com.RESTfullCRUD.BasicCRUD.entity.Product;
import com.RESTfullCRUD.BasicCRUD.entityToDto.ProductConvertor;
import com.RESTfullCRUD.BasicCRUD.dto.responseDTO.ProductAndVendorDTO;
import com.RESTfullCRUD.BasicCRUD.exceptions.CustomException;
import com.RESTfullCRUD.BasicCRUD.service.ProductService;
import com.RESTfullCRUD.BasicCRUD.service.UserService;
import com.RESTfullCRUD.BasicCRUD.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private ProductConvertor productConvertor;
    private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

//    private User verifyVendor(String authorization) throws CustomException {
//        if (authorization != null && authorization.toLowerCase().startsWith("bearer")) {
//            // Authorization: Basic base64credentials
//            String base64Credentials = authorization.substring("Basic".length()).trim();
//            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
//            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
//            // credentials = username:password
//            final String[] values = credentials.split(":", 2);
//            User vendor = userService.fetchUserByName(values[0]);
//            if(Objects.nonNull(vendor))
//                return vendor;
//        }
//        throw new CustomException("Vendor not allowed or could not be found ");
//    }

    @PostMapping(PathConstant.ADD_PRODUCT)
    public ResponseEntity<String> addProduct(@Valid @RequestBody Product product,@RequestHeader("Authorization") String token) throws Exception {
        LOGGER.info("Inside addProduct of productController ........logging info..");
        Product p = productService.addProduct(product, token);
        String message = new String("Successfully added Product with Product_Id: "+p.getProdID()+" by vendor "+ p.getVendor().getUserName()+" of ID "+p.getVendor().getUserId());
        return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
    }

    @PostMapping(PathConstant.GET_PRODUCT)
    public ResponseEntity<List<ProductDto>> fetchProducts() throws Exception {
        return new ResponseEntity<>( productConvertor.entityToDto(productService.fetchProducts()),HttpStatus.ACCEPTED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProduct() throws Exception {
        return new ResponseEntity<>(productService.fetchProducts(),HttpStatus.ACCEPTED);
    }

    @GetMapping(PathConstant.GET_PRODUCT_BY_ID)
    public ResponseEntity<ProductDto> fetchProductById(@PathVariable("id") Long productId){
        return new ResponseEntity<>(productConvertor.entityToDto(productService.fetchProductById(productId)),HttpStatus.ACCEPTED);
    }

    @GetMapping(PathConstant.GET_PRODUCT_BY_NAME)
    public ResponseEntity<List<ProductDto>> fetchProductByName(@PathVariable("name") String prodName){
        return new ResponseEntity<>(productConvertor.entityToDto(productService.fetchProductByName(prodName)),HttpStatus.ACCEPTED);
    }

    @RolesAllowed({ "ROLE_VENDOR", "ROLE_ADMIN" })
    @DeleteMapping(PathConstant.REMOVE_PRODUCT)
    public ResponseEntity<String> removeProductById(@PathVariable("id") Long productId,@RequestHeader("Authorization") String token) throws CustomException {
        productService.removeProductById(productId, token);
        return new ResponseEntity<>("Product deleted Success!!!",HttpStatus.NO_CONTENT);
    }

    @PutMapping(PathConstant.UPDATE_PRODUCT)
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") Long productId,@RequestBody ProductDto productDto,@RequestHeader("Authorization") String token) throws CustomException {
        Product product = productConvertor.DtoToEntity(productDto);
        return new ResponseEntity<>(productConvertor.entityToDto(productService.updateProduct(productId, product, token)),HttpStatus.ACCEPTED);
    }

//    @PostMapping("/getproductOf")
//    public ResponseEntity<Object> fetchProductsWithCondition(@RequestParam("vendor") String vendorName, @RequestParam("city") String city) throws Exception {
//        return new ResponseEntity<>(productService.fetchProductsWithCondition(vendorName, city));
//    }
    @GetMapping("/getProductOf")
    public ResponseEntity<List<ProductAndVendorDTO>> fetchProductsWithCondition(@RequestParam("vendor") String vendorName, @RequestParam("city") String city) throws Exception {
        return new ResponseEntity<>(productService.fetchProductsWithConditions(vendorName, city),HttpStatus.ACCEPTED);
    }
}
