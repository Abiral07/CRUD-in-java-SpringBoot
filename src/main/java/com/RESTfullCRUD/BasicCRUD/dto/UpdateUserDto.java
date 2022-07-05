package com.RESTfullCRUD.BasicCRUD.dto;

import com.RESTfullCRUD.BasicCRUD.entity.Address;
import lombok.Data;

@Data
public class UpdateUserDto{

    private String userName;
//    private String password;
    private String contact;
    private String email;
    private Address address;

}