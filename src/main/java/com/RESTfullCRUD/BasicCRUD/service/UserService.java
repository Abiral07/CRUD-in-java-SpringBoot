package com.RESTfullCRUD.BasicCRUD.service;

import com.RESTfullCRUD.BasicCRUD.dto.requestDTO.RegistrationDto;
import com.RESTfullCRUD.BasicCRUD.entity.User;
import com.RESTfullCRUD.BasicCRUD.exceptions.CustomException;
import com.RESTfullCRUD.BasicCRUD.dto.requestDTO.LoginDto;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Map;


public interface UserService {

    String userLogin(HttpServletRequest request, LoginDto loginDto) throws Exception;

    List<User> fetchAllUser() throws CustomException;



    User addUser(RegistrationDto user) throws Exception, NoSuchAlgorithmException, InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, InvalidKeyException;

    User fetchUserById(Long id) throws Exception;

    User fetchUserByName(String name) throws CustomException;

    User updateUser(Long id, User user) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, IOException, InvalidKeySpecException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, CustomException;

    List<User> fetchUserOfCountry(String country);

    Map<String, Object> getSessionDetails(HttpServletRequest request) throws CustomException;

    Map<String, Object> getClaimsDetail(String bearerToken);
}

