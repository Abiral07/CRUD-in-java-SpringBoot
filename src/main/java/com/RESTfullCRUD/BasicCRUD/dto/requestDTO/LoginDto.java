package com.RESTfullCRUD.BasicCRUD.dto.requestDTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginDto implements Serializable {
    private static final long serialVersionUID = 5926468583005150707L;

    private String userName;
    private String password;
}
