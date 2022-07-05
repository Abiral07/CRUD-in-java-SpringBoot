package com.RESTfullCRUD.BasicCRUD.Controller;

import com.RESTfullCRUD.BasicCRUD.constant.PathConstant;
import com.RESTfullCRUD.BasicCRUD.dto.UpdateUserDto;
import com.RESTfullCRUD.BasicCRUD.entity.User;
import com.RESTfullCRUD.BasicCRUD.entityToDto.UserConvertor;
import com.RESTfullCRUD.BasicCRUD.exceptions.CustomException;
import com.RESTfullCRUD.BasicCRUD.requestDTO.LoginUserDto;
import com.RESTfullCRUD.BasicCRUD.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserConvertor userConvertor;

    @GetMapping(PathConstant.GET_USER)
    public List<User> fetchAllUser(@RequestHeader("Authorization") String token) throws CustomException {
        return userService.fetchAllUser(token);
    }

    @GetMapping(PathConstant.GET_USERDTO)
    public List<UpdateUserDto> fetchAllUserDto(@RequestHeader("Authorization") String token) throws CustomException {
        return userConvertor.entityToDto(userService.fetchAllUser(token));
    }

    @PostMapping(PathConstant.ADD_USER)
    public  User addUser(@RequestBody User user) throws Exception {
        return userService.addUser(user);
    }

    @GetMapping(PathConstant.GET_USER+"/{id}")
    public User fetchUserById(@PathVariable("id") Long id, @RequestHeader("Authorization") String token) throws Exception {
        return userService.fetchUserById(id, token);
    }

    @GetMapping(PathConstant.GET_USER+"/name/{name}")
    public List<UpdateUserDto> fetchUserByName(@PathVariable("name") String name, @RequestHeader("Authorization") String token) throws CustomException {
        return userConvertor.entityToDto(userService.fetchUserByName(name, token));
    }

    @PutMapping(PathConstant.UPDATE_USER+"/{id}")
    public UpdateUserDto updateUser(@PathVariable("id") Long id, @RequestBody UpdateUserDto updateUserDto) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, IOException, InvalidKeySpecException, BadPaddingException, InvalidKeyException, CustomException {
        User user = userConvertor.DtoToEntity(updateUserDto);
        return userConvertor.entityToDto(userService.updateUser(id,user));
    }

    @GetMapping("/getuserOfCountry/{country}")
    public List<UpdateUserDto> fetchUserOfCountry(@PathVariable("country") String country){
        return userConvertor.entityToDto(userService.fetchUserOfCountry(country));

    }

    @PostMapping("/login")
    public String userLogin(@RequestBody LoginUserDto loginDto) throws Exception {
        return userService.userLogin(loginDto);
    }

}
