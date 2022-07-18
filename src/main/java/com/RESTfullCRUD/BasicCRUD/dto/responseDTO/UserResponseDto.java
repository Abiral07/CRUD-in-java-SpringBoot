package com.RESTfullCRUD.BasicCRUD.dto.responseDTO;

import com.RESTfullCRUD.BasicCRUD.entity.Address;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class UserResponseDto {

    private String userName;
    @JsonIgnore
    private String password;
    private String contact;
    private String email;
    private Address address;

}