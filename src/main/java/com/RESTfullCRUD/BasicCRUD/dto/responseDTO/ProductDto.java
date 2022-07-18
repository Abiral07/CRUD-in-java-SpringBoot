package com.RESTfullCRUD.BasicCRUD.dto.responseDTO;

import lombok.Data;

@Data
public class ProductDto {

    private long prodID;
    private String prodName;
    private double price;
    private Long vendorId;


}
