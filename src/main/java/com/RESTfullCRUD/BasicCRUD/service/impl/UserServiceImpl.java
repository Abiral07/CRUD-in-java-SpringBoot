package com.RESTfullCRUD.BasicCRUD.service.impl;

import com.RESTfullCRUD.BasicCRUD.config.AESCustomCryptoWithIV;
import com.RESTfullCRUD.BasicCRUD.config.RSA;
import com.RESTfullCRUD.BasicCRUD.entity.User;
import com.RESTfullCRUD.BasicCRUD.exceptions.CustomException;
import com.RESTfullCRUD.BasicCRUD.repository.UserRepository;
import com.RESTfullCRUD.BasicCRUD.requestDTO.LoginUserDto;
import com.RESTfullCRUD.BasicCRUD.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AESCustomCryptoWithIV aesCustomCryptoWithIV;
    @Autowired
    PasswordEncoder passwordEncoder;

    RSA rsa = new RSA();

    public UserServiceImpl() throws IOException, NoSuchAlgorithmException {
    }


    @Override
    public String userLogin(LoginUserDto loginDto) throws Exception {
        try {
            User matchedUser = userRepository.findMatchedUser(loginDto.getUserName());
            if (passwordEncoder.matches(loginDto.getPassword(), matchedUser.getPassword())){
                return new String("123456789"+matchedUser.getUserId().toString());
            }
            throw new CustomException("Incorrect username or password");
        } catch (Exception e) {
            log.error("Login: " + e.getMessage());
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    public List<User> fetchAllUser(String token) throws CustomException {
       if(checkToken(token)){
           List<User> user = userRepository.findAll();
           return user.stream().map((x)-> {
               try {
                   return decryptColumns(x);
               } catch (Exception e) {
                   throw new RuntimeException(e);
               }
           }).collect(Collectors.toList());
       }
       throw new CustomException("Authentication Fail!!!!!");
    }

    @Override
    public User addUser(User user) throws CustomException {
//        TODO : check role
        return userRepository.save(encryptColumns(user));
    }

    @Override
    public User fetchUserById(Long id, String token) throws Exception {
       if(checkToken(token)){
           User user= userRepository.findById(id).get();
           return decryptColumns(user);
       }
       throw new CustomException("Authentication fail");
    }

    @Override
    public List<User> fetchUserByName(String name, String token) throws CustomException {
        if(checkToken(token))
            return userRepository.findByUserName(name);
        throw new CustomException("Authentication Fail");
    }

    @Override
    public User updateUser(Long id, User user) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, IOException, InvalidKeySpecException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, CustomException {
        User newUser = userRepository.findById(id).get();

        if (Objects.nonNull(user.getUserName()) && !"".equalsIgnoreCase(user.getUserName())) {
            if (!(userRepository.findByUserName(user.getUserName())).isEmpty()) {
                throw new CustomException("DUBLICATE USER NAME");
            }else
                newUser.setUserName(user.getUserName());
        }
        if (Objects.nonNull(user.getPassword()) && !"".equalsIgnoreCase(user.getPassword())) {
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if (Objects.nonNull(user.getEmail()) && !"".equalsIgnoreCase(user.getEmail())) {
            newUser.setEmail(user.getEmail());
        }
        if (Objects.nonNull(user.getContact()) && !"".equalsIgnoreCase(user.getContact())) {
            newUser.setContact(rsa.rsaEncrypt(user.getContact()));
        }
        if (Objects.nonNull(user.getCitizenNo()) && !"".equalsIgnoreCase(user.getCitizenNo())) {
            newUser.setCitizenNo(aesCustomCryptoWithIV.encrypt("AES/CBC/PKCS5Padding",user.getCitizenNo()));
        }
        if (Objects.nonNull(user.getRole()) && !ObjectUtils.isEmpty(user.getRole())) {
            newUser.setRole(user.getRole());
        }
        if (Objects.nonNull(user.getAddress()) && !ObjectUtils.isEmpty(user.getAddress())) {
            newUser.setAddress(user.getAddress());
        }

        return userRepository.save(newUser);
    }

    @Override
    public List<User> fetchUserOfCountry(String country) {
        return userRepository.getByCountry(country);
    }

    private Boolean checkToken(String token){
        String uid = token.substring(16);
        Optional<User> user = userRepository.findById(Long.parseLong(uid));
        return (!user.isEmpty() );
    }

    private User encryptColumns(User user) throws CustomException {
        try {
            if (!(userRepository.findByUserName(user.getUserName())).isEmpty()) {
                throw new CustomException("DUBLICATE USER NAME");
            } else {
                //------------------AES Encryption----------------------
                user.setCitizenNo(aesCustomCryptoWithIV.encrypt("AES/CBC/PKCS5Padding", user.getCitizenNo()));
                //------------------------------------------------------

                //-------------------Bcrypt Encryption-----------------
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                //-----------------------------------------------------
                //-------------------RSA Encryption-----------------

                user.setContact(rsa.rsaEncrypt(user.getContact()));
                //-----------------------------------------------------
                return (user);
            }
        } catch (Exception e) {
            log.error("Registration: " + e.getMessage());
            throw new CustomException(e.getMessage());
        }
    }

    private User decryptColumns(User user) throws Exception {
        user.setContact(rsa.rsaDecrypt(user.getContact()));
        user.setCitizenNo(aesCustomCryptoWithIV.decrypt("AES/CBC/PKCS5Padding", user.getCitizenNo()));
        return user;
    }
}