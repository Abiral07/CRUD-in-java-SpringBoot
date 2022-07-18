package com.RESTfullCRUD.BasicCRUD.dto.requestDTO;

import com.RESTfullCRUD.BasicCRUD.entity.Address;
import com.RESTfullCRUD.BasicCRUD.entity.Product;
import com.RESTfullCRUD.BasicCRUD.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDto {
    private Long userId;
    private String userName;
    private String password;
    private String citizenNo;
    private String contact;
    private String email;
    private Address address;
}
