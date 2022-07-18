package com.RESTfullCRUD.BasicCRUD.dto.requestDTO;

import lombok.Data;

@Data
public class ProductUpdateDto {
    private Long prodID;
    private String prodName;
    private String prodType;
    private Double price;
}
