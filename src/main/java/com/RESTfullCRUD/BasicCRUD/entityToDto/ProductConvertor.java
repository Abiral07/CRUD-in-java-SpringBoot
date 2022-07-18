package com.RESTfullCRUD.BasicCRUD.entityToDto;

import com.RESTfullCRUD.BasicCRUD.dto.responseDTO.ProductDto;
import com.RESTfullCRUD.BasicCRUD.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductConvertor {
    public ProductDto entityToDto(Product product){
        ProductDto dto = new ProductDto();
        dto.setProdID(product.getProdID());
        dto.setProdName(product.getProdName());
        dto.setPrice(product.getPrice());
        dto.setVendorId(product.getVendor().getUserId());
        return dto;
    }

    public List<ProductDto> entityToDto(List<Product> product){
        return product.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public Product DtoToEntity(ProductDto dto){
        Product product = new Product();
        product.setProdID(dto.getProdID());
        product.setProdName(dto.getProdName());
        product.setPrice(dto.getPrice());
        return product;
    }

    public List<Product> DtoToEntity(List<ProductDto> dto){
        return dto.stream().map(this::DtoToEntity).collect(Collectors.toList());
    }
}
