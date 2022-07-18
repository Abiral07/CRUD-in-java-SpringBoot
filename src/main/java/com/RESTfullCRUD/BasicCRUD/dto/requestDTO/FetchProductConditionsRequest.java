package com.RESTfullCRUD.BasicCRUD.dto.requestDTO;

import lombok.Data;

@Data
public class FetchProductConditionsRequest {
    private String vendorName;
    private String city;
}
