package com.RESTfullCRUD.BasicCRUD.dto;

import lombok.Data;

@Data
public class ProductDto {

    private long prodID;
    private String prodName;
    private double price;
    private Long vendorId;


}
