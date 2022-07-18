package com.RESTfullCRUD.BasicCRUD.dto.responseDTO;

import com.RESTfullCRUD.BasicCRUD.entity.Address;
import com.RESTfullCRUD.BasicCRUD.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductAndVendorDTO {
    private Long prodID;
    private String prodName;
    private String prodType;
    private Double price;
    private String vendorName;
    private String vendorContact;
//    private Address address;
    private String country;
    private String city;
    private String province;
}
